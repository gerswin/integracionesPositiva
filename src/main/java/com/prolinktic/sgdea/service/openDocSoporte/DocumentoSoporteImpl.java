package com.prolinktic.sgdea.service.openDocSoporte;

import com.prolinktic.sgdea.dao.resolucionConsecutivo.ResolucionConsecutivoDao;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocSoporteOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.FiltroResolucionFacturacionDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.ResolucionDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.ResolucionResponseDTO;
import com.prolinktic.sgdea.infrastructura.gateway.OpenEtlRestTemplateImpl;
import com.prolinktic.sgdea.model.resolucion.ResolucionConsecutivo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class DocumentoSoporteImpl implements DocumentoSoporteService {

    @Value("${open.ofe}")
    private String ofe;

    @Autowired
    private OpenEtlRestTemplateImpl open;

    @Autowired
    private ResolucionConsecutivoDao resolucionConsecutivoDao;

    @Override
    @Transactional
    public ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocSoporteOpen documento) throws Exception {
        log.debug("Iniciando creación de documento soporte");

        try {
            ResolucionDTO resolucionDTO = obtenerResolucionSoporteVigente("SEDS");
            if (resolucionDTO == null) {
                throw new IllegalStateException("No se encontró resolución con prefijo 'SEDS'");
            }

            CreateDocSoporteOpen.DocSoporteOpen docs = documento.getDocumentos();
            if (docs == null) {
                throw new IllegalArgumentException("El objeto CreateDocSoporteOpen no contiene 'documentos'");
            }


            ResolucionConsecutivo resolucionConsecutivo = obtenerOCrearResolucionConsecutivo(resolucionDTO);


            Map<String, String> consecutivosAsignados = asignarConsecutivosADocumentos(docs, resolucionDTO, resolucionConsecutivo);

            ResponseCreateDocOpenDTO response = open.crearDocumentoSoporte(documento);


            procesarRespuestaYActualizarConsecutivos(response, consecutivosAsignados, resolucionConsecutivo);

            return response;

        } catch (Exception e) {
            log.error("Error al registrar documento soporte en Open", e);
            throw new Exception("Error al registrar documento soporte en Open", e);
        }
    }

    private ResolucionDTO obtenerResolucionSoporteVigente(String prefijo) {
        ResolucionResponseDTO response = open.listarResolucionesFacturacion(new FiltroResolucionFacturacionDTO());

        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return response.getData().stream()
                .filter(r -> "DOCUMENTO_SOPORTE".equalsIgnoreCase(r.getRfaTipo()))
                .filter(r -> {
                    try {
                        LocalDate desde = LocalDate.parse(r.getRfaFechaDesde(), formatter);
                        LocalDate hasta = LocalDate.parse(r.getRfaFechaHasta(), formatter);
                        return (hoy.isEqual(desde) || hoy.isEqual(hasta) || (hoy.isAfter(desde) && hoy.isBefore(hasta)));
                    } catch (Exception e) {
                        return false;
                    }
                })
                .filter(r -> prefijo.equalsIgnoreCase(r.getRfaPrefijo()))
                .findFirst()
                .orElse(null);
    }


    private ResolucionConsecutivo obtenerOCrearResolucionConsecutivo(ResolucionDTO resolucionDTO) {
        Optional<ResolucionConsecutivo> consecutivoOpt = resolucionConsecutivoDao.obtenerPorPrefijo(resolucionDTO.getRfaPrefijo());

        if (consecutivoOpt.isPresent()) {
            return consecutivoOpt.get();
        }

        ResolucionConsecutivo nuevoConsecutivo = new ResolucionConsecutivo();
        nuevoConsecutivo.setConsecutivo(Long.parseLong(resolucionDTO.getRfaConsecutivoInicial()));
        nuevoConsecutivo.setConsecutivoIni(Long.parseLong(resolucionDTO.getRfaConsecutivoInicial()));
        nuevoConsecutivo.setConsecutivoFin(Long.parseLong(resolucionDTO.getRfaConsecutivoFinal()));
        nuevoConsecutivo.setRfaPrefijo(resolucionDTO.getRfaPrefijo());
        nuevoConsecutivo.setRfaResolucion(resolucionDTO.getRfaResolucion());

        resolucionConsecutivoDao.updateResolu(nuevoConsecutivo);
        return nuevoConsecutivo;
    }

    private Map<String, String> asignarConsecutivosADocumentos(CreateDocSoporteOpen.DocSoporteOpen docs,
                                                               ResolucionDTO resolucionDTO,
                                                               ResolucionConsecutivo resolucionConsecutivo) {
        Map<String, String> consecutivosAsignados = new HashMap<>();
        long consecutivoActual = resolucionConsecutivo.getConsecutivo();

        if (docs.getDS() != null) {
            for (CreateDocSoporteOpen.DSDocumentoRequestDTO ds : docs.getDS()) {
                String consecutivo = String.valueOf(consecutivoActual);
                setCamposResolucionDS(ds, resolucionDTO, consecutivo);

                String claveDocumento = "DS_" + consecutivo;
                consecutivosAsignados.put(claveDocumento, consecutivo);
                consecutivoActual++;
            }
        }

        if (docs.getDS_NC() != null) {
            for (CreateDocSoporteOpen.DSNCDocumentoRequestDTO dsNc : docs.getDS_NC()) {
                String consecutivo = String.valueOf(consecutivoActual);
                setCamposResolucionDsNc(dsNc, resolucionDTO, consecutivo);

                String claveDocumento = "DS_NC_" + consecutivo;
                consecutivosAsignados.put(claveDocumento, consecutivo);
                consecutivoActual++;
            }
        }

        return consecutivosAsignados;
    }

    private void procesarRespuestaYActualizarConsecutivos(ResponseCreateDocOpenDTO response,
                                                          Map<String, String> consecutivosAsignados,
                                                          ResolucionConsecutivo resolucionConsecutivo) {
        Set<String> consecutivosExitosos = new HashSet<>();


        if (response.getDocumentosProcesados() != null) {
            for (ResponseCreateDocOpenDTO.DocumentoProcesadoOpen docProcesado : response.getDocumentosProcesados()) {
                consecutivosExitosos.add(docProcesado.getCdoConsecutivo());
                log.info("Documento procesado exitosamente - Prefijo: {}, Consecutivo: {}",
                        docProcesado.getRfaPrefijo(), docProcesado.getCdoConsecutivo());
            }
        }


        if (response.getDocumentosFallidos() != null) {
            for (ResponseCreateDocOpenDTO.DocumentoFallidoOpen docFallido : response.getDocumentosFallidos()) {
                log.warn("Documento fallido - Prefijo: {}, Consecutivo: {}, Errores: {}",
                        docFallido.getPrefijo(), docFallido.getConsecutivo(), docFallido.getErrorsAsList());
            }
        }


        if (!consecutivosExitosos.isEmpty()) {
            long maxConsecutivoExitoso = consecutivosExitosos.stream()
                    .mapToLong(Long::parseLong)
                    .max()
                    .orElse(resolucionConsecutivo.getConsecutivo() - 1);


            if (maxConsecutivoExitoso >= resolucionConsecutivo.getConsecutivo()) {
                resolucionConsecutivo.setConsecutivo(maxConsecutivoExitoso + 1);
                resolucionConsecutivoDao.updateResolu(resolucionConsecutivo);
                log.info("Consecutivo actualizado en BD: {}", resolucionConsecutivo.getConsecutivo());
            }
        } else {
            log.warn("No se procesó ningún documento exitosamente, consecutivo en BD permanece sin cambios: {}",
                    resolucionConsecutivo.getConsecutivo());
        }
    }

    private void setCamposResolucionDS(CreateDocSoporteOpen.DSDocumentoRequestDTO documento,
                                       ResolucionDTO resolucionDTO,
                                       String consecutivo) {
        documento.setOfe_identificacion(ofe);
        documento.setRfa_resolucion(resolucionDTO.getRfaResolucion());
        documento.setRfa_prefijo(resolucionDTO.getRfaPrefijo());
        documento.setCdo_consecutivo(consecutivo);
    }

    private void setCamposResolucionDsNc(CreateDocSoporteOpen.DSNCDocumentoRequestDTO documento,
                                         ResolucionDTO resolucionDTO,
                                         String consecutivo) {
        documento.setOfe_identificacion(ofe);
        documento.setRfa_prefijo(resolucionDTO.getRfaPrefijo());
        documento.setCdo_consecutivo(consecutivo);
    }
}