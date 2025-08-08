package com.prolinktic.sgdea.service.openRecepcion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.FiltroProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.ProveedorResponseDTO;
import com.prolinktic.sgdea.infrastructura.gateway.OpenEtlRestTemplateImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenRecepcionServiceImpl implements OpenRecepcionService {

    @Autowired
    private OpenEtlRestTemplateImpl open;


    @Override
    public ResponseRegistroOpen registrarDocumentoFileRecepcion(Integer ofeIdentificacion, MultipartFile pdf, MultipartFile xml, String cufe) throws Exception {
        log.debug("Empezando proceso registrarDocumentoFileRecepcion");

        if (ofeIdentificacion == null || pdf == null || xml == null || cufe == null || cufe.isEmpty()) {
            log.error("Parámetros de entrada inválidos");
            throw new IllegalArgumentException("Parámetros inválidos: ofeIdentificacion, archivos PDF, XML y CUFE son obligatorios.");
        }

        File pdfFile = null;
        File xmlFile = null;
        try {

            pdfFile = convertMultipartFileToFile(pdf);
            xmlFile = convertMultipartFileToFile(xml);

            ResponseRegistroOpen respOpen = open.registrarDocumentoFileRecepcion(ofeIdentificacion, pdfFile, xmlFile, cufe);

            return respOpen;

        } catch (IOException e) {
            log.error("Error al convertir los archivos MultipartFile a File", e);
            throw new Exception("Error al convertir los archivos", e);
        } catch (Exception e) {
            log.error("Error al registrar documento en Open", e);
            throw new Exception("Error al realizar el registro en Open", e);
        } finally {
            if (pdfFile != null && pdfFile.exists()) {
                boolean deletedPdf = pdfFile.delete();
                log.debug("Archivo PDF temporal eliminado: " + deletedPdf);
            }
            if (xmlFile != null && xmlFile.exists()) {
                boolean deletedXml = xmlFile.delete();
                log.debug("Archivo XML temporal eliminado: " + deletedXml);
            }
        }
    }

    @Override
    public ResponseRegistroOpen registrarDocumentoJsonRecepcion(Integer ofeIdentificacion, List<DocumentoDTO> documentos) throws Exception {
        log.debug("Empezando proceso registrarDocumentoJsonRecepcion");

        if (ofeIdentificacion == null || documentos == null || documentos.isEmpty()) {
            log.error("Parámetros de entrada inválidos");
            throw new IllegalArgumentException("Parámetros inválidos: ofeIdentificacion y documentos son obligatorios.");
        }

        try {
            ResponseRegistroOpen respOpen = open.registrarDocumentoJsonRecepcion(ofeIdentificacion, documentos);

            return respOpen;

        } catch (Exception e) {
            log.error("Error al registrar documentos en Open", e);
            throw new Exception("Error al realizar el registro en Open", e);
        }
    }

    @Override
    public ResponseRegistroEventoOpen registrarEvento(String evento, List<DocumentoEventoDTO> documentos) {
        log.debug("Iniciando validación para registro de evento: {}", evento);
        try {
            if (evento == null || evento.isEmpty()) {
                log.error("Error de validación: Tipo de evento vacío");
                throw new IllegalArgumentException("El tipo de evento no puede estar vacío");
            }

            if (documentos == null || documentos.isEmpty()) {
                log.error("Error de validación: Lista de documentos vacía");
                throw new IllegalArgumentException("Debe proporcionar al menos un documento");
            }

            log.debug("Validando {} documento(s)", documentos.size());
            for (int i = 0; i < documentos.size(); i++) {
                try {
                    validarDocumento(documentos.get(i));
                } catch (IllegalArgumentException e) {
                    log.error("Error de validación en documento {}: {}", i, e.getMessage());
                    throw new IllegalArgumentException("Error en documento " + i + ": " + e.getMessage());
                }
            }

            return registroEventoRecepcion(evento, documentos);
        } catch (Exception e) {
            log.error("Error al registrar el evento: {}", e.getMessage(), e);
            throw new RuntimeException("Error al registrar el evento: " + e.getMessage(), e);
        }
    }

    @Override
    public ProveedorResponseDTO listarProveedores(FiltroProveedorDTO request) throws Exception {
        log.debug("Empezando proceso listarProveedores");
        try {
            ProveedorResponseDTO respOpen = open.listarProveedores(request);

            return respOpen;

        } catch (Exception e) {
            log.error("Error al listar proveedores Open", e);
            throw new Exception("Error al listar proveedores Open", e);
        }
    }

    @Override
    public CrearProveedorResponseDTO crearProveedor(CrearProveedorDTO request) throws Exception {
        log.debug("Empezando proceso creacion proveedor");
        try {
            CrearProveedorResponseDTO respOpen = open.crearProveedor(request);

            return respOpen;

        } catch (Exception e) {
            log.error("Error al crear proveedor en Open", e);
            throw new Exception("Error al crear proveedor Open", e);
        }
    }

    private void validarDocumento(DocumentoEventoDTO documento) {
        // Si el documento usa CUFE
        if (documento.getCdo_cufe() != null && !documento.getCdo_cufe().isEmpty()) {
            log.debug("Validando documento con CUFE: {}", documento.getCdo_cufe());
            if (documento.getCdo_fecha() == null || documento.getCdo_fecha().isEmpty() ||
                    documento.getCre_codigo() == null || documento.getCre_codigo().isEmpty()) {
                throw new IllegalArgumentException("Para documentos con CUFE, se requieren los campos: cdo_fecha y cre_codigo");
            }
        }
        // Si el documento usa identificación de negocio
        else {
            log.debug("Validando documento con identificación de negocio: {}/{}/{}/{}",
                    documento.getOfe_identificacion(), documento.getTde_codigo(),
                    documento.getRfa_prefijo(), documento.getCdo_consecutivo());
            if (documento.getOfe_identificacion() == null || documento.getOfe_identificacion().isEmpty() ||
                    documento.getPro_identificacion() == null || documento.getPro_identificacion().isEmpty() ||
                    documento.getTde_codigo() == null || documento.getTde_codigo().isEmpty() ||
                    documento.getRfa_prefijo() == null || documento.getRfa_prefijo().isEmpty() ||
                    documento.getCdo_consecutivo() == null || documento.getCdo_consecutivo().isEmpty() ||
                    documento.getCdo_fecha() == null || documento.getCdo_fecha().isEmpty() ||
                    documento.getCre_codigo() == null || documento.getCre_codigo().isEmpty()) {
                throw new IllegalArgumentException("Para documentos sin CUFE, se requieren los campos: ofe_identificacion, pro_identificacion, tde_codigo, rfa_prefijo, cdo_consecutivo, cdo_fecha y cre_codigo");
            }
        }
    }


    public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        Path tempFilePath = Files.createTempFile("uploaded-file-", null);
        File tempFile = tempFilePath.toFile();
        multipartFile.transferTo(tempFile);
        return tempFile;
    }

    public ResponseRegistroEventoOpen registroEventoRecepcion(String evento, List<DocumentoEventoDTO> documentos) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("evento", evento);
            requestMap.put("documentos", documentos);
            String jsonRequest = objectMapper.writeValueAsString(requestMap);
            return open.registroEventoRecepcion(jsonRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir objetos a JSON", e);
        }
    }

}
