package com.prolinktic.sgdea.service.facturacionDescargaOpen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.client.AlfrescoRestClient;
import com.prolinktic.sgdea.dao.FacturacionDescarga.FacturacionDescargaDao;
import com.prolinktic.sgdea.dao.FacturacionDescarga.FacturacionDescargaJpaSpring;
import com.prolinktic.sgdea.dao.clasificacionDocumento.ClasificacionDocumentoDao;
import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.mapper.FacturacionDescargaMapper;
import com.prolinktic.sgdea.dtos.OpenEtlDto.mapper.FacturacionDescargaMapperManual;
import com.prolinktic.sgdea.exception.FacturacionException;
import com.prolinktic.sgdea.infrastructura.gateway.OpenEtlRestTemplateImpl;
import com.prolinktic.sgdea.model.FacturacionDescarga.FacturacionDescarga;
import com.prolinktic.sgdea.model.clasificacionDocumento.DocumentoClasificado;
import com.prolinktic.sgdea.util.CustomMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FacturacionDescargaOpenImpl implements FacturacionDescargaOpen {
    private static final Logger log = LoggerFactory.getLogger(FacturacionDescargaOpenImpl.class);
    @Autowired
    private OpenEtlRestTemplateImpl open;
    @Autowired
    private FacturacionDescargaJpaSpring jpaFacDes;
    @Autowired
    private FacturacionDescargaDao facDao;
    @Autowired
    private AlfrescoRestClient alfrescoRestClient;
    @Autowired
    private ClasificacionDocumentoDao documentoDao;

    @Autowired
    private FacturacionDescargaMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @Value("${nodeIdCarpetaCrm}")
    private String carpetaCRM;

    @Value("${nodeIdCarpetaCORE}")
    private String carpetaCORE;

    @Value("${nodeIdCarpetaSGDEA}")
    private String carpetaSGDEA;

    @Value("${nodeIdCarpetaCuida2}")
    private String carpetaCuida2;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public ResponseFacDes descargarFacturacion(String cufe, String fecha) {
        log.info("Iniciando descargarFacturacion metodo servicio - {}", LocalDateTime.now().format(FORMATTER));
        log.info("Consultando en openEtl - CUFE: {}, Fecha: {}", cufe, fecha);
        ResponseFacDes responseFacDes = new ResponseFacDes();
        try {
            log.info("Se consulta en openEtl - {}", LocalDateTime.now().format(FORMATTER));
            Optional<ConsultaDocOpen> resp = open.descargarFacturacion(cufe, fecha);
            ConsultaDocOpen consul = resp.get();
            log.info("Se obvtienen los datos en openEtl - {}", LocalDateTime.now().format(FORMATTER));
            if (consul.getData() != null && !consul.getData().isEmpty()) {
                log.info("hay datos de consulta - {}", LocalDateTime.now().format(FORMATTER));
                List<Ultimo_estado> historicoEstados = consul.getData().get(0).getHistorico_estados();
                if (historicoEstados != null && !historicoEstados.isEmpty()) {
                    for (int i = 0; i < historicoEstados.size(); i++) {
                        log.info("Se revisa historico de estados - {}", LocalDateTime.now().format(FORMATTER));

                        if (historicoEstados.get(i).getEstado() != null &&
                            historicoEstados.get(i).getEstado().equals("RDI")) {
                            responseFacDes.setPdf(historicoEstados.get(i).getArchivo());
                            responseFacDes.setXml(historicoEstados.get(i).getXml());
                            responseFacDes.setMensaje("exitoso");
                            responseFacDes.setStatus(200);
                            break;
                        }
                    }
                    if (responseFacDes.getStatus() != 200) {
                        log.info("No se encontró estado RDI - {}", LocalDateTime.now().format(FORMATTER));
                        responseFacDes.setMensaje("No se encontró estado RDI");
                        responseFacDes.setStatus(204);
                    }
                } else {
                    log.info("Histórico de estados vacío - {}", LocalDateTime.now().format(FORMATTER));
                    responseFacDes.setMensaje("Histórico de estados vacío");
                    responseFacDes.setStatus(204);
                }
            } else {
                log.info("No se encontraron datos de consulta - {}", LocalDateTime.now().format(FORMATTER));
                responseFacDes.setMensaje("Data no encontrada");
                responseFacDes.setStatus(204);
            }
        } catch (HttpClientErrorException e) {
            log.info("Se capturo un error en descargarFacturacion - {}", LocalDateTime.now().format(FORMATTER));
            responseFacDes.setMensaje("Error en la peticion");
            responseFacDes.setStatus(404);
        } catch (ResourceAccessException e) {
            log.error("Error de comunicación con el servidor: {}", e.getMessage(), e);
            responseFacDes.setMensaje("Error de comunicación con el servidor, por favor intente más tarde.");
            responseFacDes.setStatus(503);
        } catch (Exception e) {
            log.error("Error inesperado en descargarFacturacion: {}", e.getMessage(), e);
            responseFacDes.setMensaje("Error interno del sistema. Por favor contacte al soporte.");
            responseFacDes.setStatus(500);
        }
        log.info("Se retorno la respuesta responseFacDes - {}", LocalDateTime.now().format(FORMATTER));
        return responseFacDes;
    }

    @Override
    public ResponseListFacDes traerOpen(String fechaInicial, String fechaFinal) {
        log.info("Empezando Proceso Listar facturas en openEtl en Service");
        try {
            ResponseListFacDes responseFacDes = new ResponseListFacDes();
            ResponseDataList rep = open.listarFacturacion(fechaInicial, fechaFinal);
            List<Dato> listDatos = rep.getData();


            int tamañoLote = 400;
            List<FacturacionDescarga> facturasTotal = new ArrayList<>();
            List<FacturacionDescarga> facturasNuevas = new ArrayList<>();

            for (int i = 0; i < listDatos.size(); i += tamañoLote) {
                int fin = Math.min(i + tamañoLote, listDatos.size());
                List<Dato> lote = listDatos.subList(i, fin);

                List<FacturacionDescarga> facturasLote = lote.stream()
                        .map(dato -> {

                            if (!jpaFacDes.existsByCufe(dato.getCufe())) {
                                FacturacionDescarga nuevaFactura = new FacturacionDescarga();
                                nuevaFactura.setIdFacDes(0);
                                nuevaFactura.setOfe(dato.getOfe());
                                nuevaFactura.setProveedor(dato.getProveedor());
                                nuevaFactura.setTipo(dato.getTipo());
                                nuevaFactura.setPrefijo(dato.getPrefijo());
                                nuevaFactura.setConsecutivo(dato.getConsecutivo());
                                nuevaFactura.setCufe(dato.getCufe());
                                nuevaFactura.setFecha(dato.getFecha());
                                nuevaFactura.setHora(dato.getHora());
                                nuevaFactura.setValor(Double.parseDouble(dato.getValor().replace(" ", "")));
                                nuevaFactura.setMarca(false);
                                return nuevaFactura;
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());


                if (!facturasLote.isEmpty()) {
                    log.info("Guardando lote de {} facturas nuevas en base de datos", facturasLote.size());
                    jpaFacDes.saveAll(facturasLote);
                    facturasNuevas.addAll(facturasLote);
                }


                List<FacturacionDescarga> facturasLoteCompleto = lote.stream()
                        .map(dato -> {
                            FacturacionDescarga facturaExistente = new FacturacionDescarga();
                            facturaExistente.setIdFacDes(0);
                            facturaExistente.setOfe(dato.getOfe());
                            facturaExistente.setProveedor(dato.getProveedor());
                            facturaExistente.setTipo(dato.getTipo());
                            facturaExistente.setPrefijo(dato.getPrefijo());
                            facturaExistente.setConsecutivo(dato.getConsecutivo());
                            facturaExistente.setCufe(dato.getCufe());
                            facturaExistente.setFecha(dato.getFecha());
                            facturaExistente.setHora(dato.getHora());
                            facturaExistente.setValor(Double.parseDouble(dato.getValor().replace(" ", "")));
                            facturaExistente.setMarca(false);
                            return facturaExistente;
                        })
                        .collect(Collectors.toList());

                facturasTotal.addAll(facturasLoteCompleto);
            }

            log.info("Procesando FacturaDescarga DAO");
            List<FacturacionDescargaDto> dtoList = mapper.toDtoList(facturasTotal);
            responseFacDes.setData(dtoList);

            log.info("Procesando FacturaDescarga DAO");
            responseFacDes.setTotal(dtoList.size());
            responseFacDes.setStatus("200");
            responseFacDes.setMessage("Exitoso");

            log.info("Total de facturas procesadas: {}", facturasTotal.size());
            log.info("Total de facturas nuevas guardadas: {}", facturasNuevas.size());

            log.info("Terminando Proceso Listar facturas en openEtl en Service");
            return responseFacDes;
        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.warn("Error en Service Proceso Listar facturas en openEtl " + e.getMessage());
            return new ResponseListFacDes("400", "Verificar las fechas ingresadas", 0, null);
        } catch (Exception e) {
            log.error("Error inesperado en procesamiento de facturas", e);
            return new ResponseListFacDes("500", "Error interno del servidor", 0, null);
        }
    }

    public ResponseListFacDes traerOpenConParametros(String fechaInicial, String fechaFinal,
                                                     String prefijo,
                                                     String consecutivo,
                                                     String proveedor) {
        log.info("Empezando Proceso Listar facturas en openEtl en Service");
        try {
            ResponseDataList rep = open.listarFacturacion(fechaInicial, fechaFinal);
            List<Dato> listDatos = rep.getData();
            log.info("Total de facturas desde Open: {}", listDatos.size());

            if (listDatos == null || listDatos.isEmpty()) {
                ResponseListFacDes responseFacDes = new ResponseListFacDes();
                responseFacDes.setData(List.of());
                responseFacDes.setTotal(0);
                responseFacDes.setStatus("200");
                responseFacDes.setMessage("Exitoso");
                return responseFacDes;
            }

            ObjectMapper mapper2 = new ObjectMapper();
            String jsonInput = mapper2.writeValueAsString(listDatos);

            List<Map<String, Object>> facturasNoGuardadas = filtrarFacturasNoGuardadas(jsonInput, fechaInicial, fechaFinal);
            log.info("Facturas no guardadas: {}", facturasNoGuardadas.size());

            List<FacturacionDescarga> facturasProcesadas = facturasNoGuardadas.stream()
                    .filter(dato ->
                            (prefijo == null || prefijo.isEmpty() || prefijo.equals(dato.get("prefijo"))) &&
                            (consecutivo == null || consecutivo.isEmpty() || consecutivo.equals(dato.get("consecutivo"))) &&
                            (proveedor == null || proveedor.isEmpty() || proveedor.equals(dato.get("proveedor")))
                    )
                    .map(dato -> {
                        FacturacionDescarga factura = new FacturacionDescarga();
                        factura.setIdFacDes(0);
                        factura.setOfe((String) dato.get("ofe"));
                        factura.setProveedor((String) dato.get("proveedor"));
                        factura.setTipo((String) dato.get("tipo"));
                        factura.setPrefijo((String) dato.get("prefijo"));
                        factura.setConsecutivo((String) dato.get("consecutivo"));
                        factura.setCufe((String) dato.get("cufe"));
                        factura.setFecha((String) dato.get("fecha"));
                        factura.setHora((String) dato.get("hora"));

                        String valorStr = String.valueOf(dato.get("valor")).replace(" ", "");
                        factura.setValor(Double.parseDouble(valorStr));
                        factura.setMarca((Boolean) dato.get("marca"));
                        return factura;
                    })
                    .collect(Collectors.toList());

            log.info("Procesando FacturaDescarga DAO");
            List<FacturacionDescargaDto> dtoList = mapper.toDtoList(facturasProcesadas);

            ResponseListFacDes responseFacDes = new ResponseListFacDes();
            responseFacDes.setData(dtoList);
            responseFacDes.setTotal(dtoList.size());
            responseFacDes.setStatus("200");
            responseFacDes.setMessage("Exitoso");

            log.info("Total de facturas procesadas: {}", facturasProcesadas.size());
            return responseFacDes;

        } catch (HttpServerErrorException | HttpClientErrorException e) {
            log.warn("Error en Service Proceso Listar facturas en openEtl " + e.getMessage());
            return new ResponseListFacDes("400", "Verificar las fechas ingresadas", 0, null);
        } catch (Exception e) {
            log.error("Error inesperado en procesamiento de facturas", e);
            return new ResponseListFacDes("500", "Error interno del servidor", 0, null);
        }
    }

//    @Retryable(
//            value = {Exception.class},
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 10000, multiplier = 2)
//    )
//    @Scheduled(cron = "0 0 7,14,18 * * ?") // Se ejecuta todos los días a las 7:00 AM, 2:00 PM y 6:00 PM
//    public void ejecutarTraerOpen() {
//        log.info("Iniciando ejecución de traerOpen");
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
//
//        String fechaInicial = yesterday.format(DATE_FORMAT);
//        String fechaFinal = today.format(DATE_FORMAT);
//
//
//        log.info("Iniciando ejecución de traerOpen con fechas: {} - {}", fechaInicial, fechaFinal);
//        traerOpen(fechaInicial, fechaFinal);
//        log.info("Ejecución de traerOpen finalizada con éxito.");
//    }

    @Recover
    public void recuperarTraerOpen(Exception e) {
        log.error("Todos los intentos de ejecutar traerOpen fallaron. Error final: {}", e.getMessage());
    }

    @Override
    public CompletableFuture<ResponseListFacDes> traerOpenAsync(String fechaInicial, String fechaFinal, String prefijo,
                                                                String consecutivo,
                                                                String proveedor) {
        return CompletableFuture.supplyAsync(() -> traerOpenConParametros(fechaInicial, fechaFinal, prefijo, consecutivo, proveedor));
    }

    @Override
    public List<FacturacionDescargaDto> listarFacturasProvedor(String proveedor) {
        List<FacturacionDescarga> facturas = facDao.findByProveedor(proveedor);
        List<FacturacionDescargaDto> dtoList = FacturacionDescargaMapperManual.toDtoList(facturas);

        return dtoList;
    }

    @Override
    public ResposneMarca marcarFacturacion(String cufe) {
        List<FacturacionDescarga> respfac = facDao.findByCufe(cufe);
        if (!respfac.isEmpty() && !respfac.get(0).isMarca()) {
            FacturacionDescarga rempl = respfac.get(0);
            rempl.setMarca(true);
            jpaFacDes.save(rempl);
            return new ResposneMarca("Marcada exitosa", "200");
        } else {
            return new ResposneMarca("El cufe buscado no existe o ya se encuentra descargada", "404");
        }
    }

//    @Override
//    public List<FacturacionDescarga> listarFacturacion(String fecha, String prefijo, String consecutivo, String proveedor) {
//        return jpaFacDes.findAll(filtrarByDto(fecha,prefijo,consecutivo,proveedor)).stream().filter(facturacionDescarga -> !facturacionDescarga.isMarca()).collect(Collectors.toList());
//    }


    public List<Map<String, Object>> filtrarFacturasNoGuardadas(String jsonInput, String fechaInicial, String fechaFinal) {
        log.info("JSON Input enviado a la función: {}", jsonInput);

        try {
            // Crear la consulta para el procedimiento almacenado
            StoredProcedureQuery query = entityManager.createStoredProcedureQuery("filtrar_facturas_no_guardadas");

            // Registrar parámetros del procedimiento
            query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
            query.setParameter(1, jsonInput);

            query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
            query.setParameter(2, fechaInicial);

            query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
            query.setParameter(3, fechaFinal);

            // Ejecutar y obtener el resultado en JSON
            String jsonResult = (String) query.getSingleResult();
            log.info("Resultado JSON recibido de la función: {}", jsonResult);

            // Parsear el JSON a una lista de mapas
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> result = mapper.readValue(
                    jsonResult,
                    new TypeReference<List<Map<String, Object>>>() {
                    }
            );

            log.info("Número de registros después de parsear: {}", result.size());
            return result;

        } catch (Exception e) {
            log.error("Error al ejecutar filtrarFacturasNoGuardadas - {}", e);
            throw new RuntimeException("Error al filtrar facturas no guardadas", e);
        }
    }


    @Override
    public List<FacturacionDescarga> listarFacturacion(String fecha, String prefijo, String consecutivo, String proveedor) {
        log.info("Iniciando listarFacturacion metodo - {}", LocalDateTime.now().format(FORMATTER));


        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ListarFacturacion", FacturacionDescarga.class);
        log.info("Se crea store procedure de listar facturacion - {}", LocalDateTime.now().format(FORMATTER));

        query.registerStoredProcedureParameter("p_fecha", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_prefijo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_consecutivo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_proveedor", String.class, ParameterMode.IN);

        query.setParameter("p_fecha", fecha);
        query.setParameter("p_prefijo", prefijo);
        query.setParameter("p_consecutivo", consecutivo);
        query.setParameter("p_proveedor", proveedor);

        log.info("Se ejecuta store procedure de listar facturacion - {}", LocalDateTime.now().format(FORMATTER));
        var result = query.getResultList();

        log.info("Se termino de ejecutar store procedure de contar facturacion - {}", LocalDateTime.now().format(FORMATTER));

        return result;
    }

    private Specification<FacturacionDescarga> filtrarByDto(String fecha, String prefijo, String consecutivo, String proveedor) {
        Specification<FacturacionDescarga> espec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predic = new ArrayList<>();
            if (fecha != null && !fecha.isEmpty()) {
                Expression<String> fechaLowerCase = criteriaBuilder.lower(root.get("fecha"));
                predic.add(criteriaBuilder.like(fechaLowerCase, "%" + fecha + "%"));
            } else {
                // Obtener la fecha actual y calcular la fecha límite (10 días atrás)
                LocalDate currentDate = LocalDate.now();
                LocalDate tenDaysAgoDate = currentDate.minus(10, ChronoUnit.DAYS);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String tenDaysAgo = tenDaysAgoDate.format(formatter);

                Expression<String> fechaExp = root.get("fecha");
                predic.add(criteriaBuilder.greaterThanOrEqualTo(fechaExp, tenDaysAgo));
            }
            if (prefijo != null && !prefijo.isEmpty()) {
                Expression<String> prefijoLowerCase = criteriaBuilder.lower(root.get("prefijo"));
                predic.add(criteriaBuilder.like(prefijoLowerCase, "%" + prefijo + "%"));
            }
            if (consecutivo != null && !consecutivo.isEmpty()) {
                Expression<String> consecutivoLowerCase = criteriaBuilder.lower(root.get("consecutivo"));
                predic.add(criteriaBuilder.like(consecutivoLowerCase, "%" + consecutivo + "%"));
            }
            if (proveedor != null && !proveedor.isEmpty()) {
                Expression<String> proveedorLowerCase = criteriaBuilder.lower(root.get("proveedor"));
                predic.add(criteriaBuilder.like(proveedorLowerCase, "%" + proveedor + "%"));
            }

            return criteriaBuilder.and(predic.toArray(new Predicate[predic.size()]));
        });
        return espec;
    }

    @Override
    public ResponseConsultaDocumentoDto consultaDocumento(Integer ofeIdentificacion, String prefijo, Integer consecutivo,
                                                          String tipo, String proveedor, String cufe, String fecha) throws FacturacionException {

        CompletableFuture<List<ConsultaDocDto>> documentoRecepcion = CompletableFuture.supplyAsync(() -> open.consultarDocumentoRecepcion(ofeIdentificacion, prefijo, consecutivo,
                tipo, proveedor, cufe, fecha));

        try {
            List<ConsultaDocDto> consultaDoc = documentoRecepcion.get();

            if (!consultaDoc.isEmpty()) {
                ConsultaDocDto documento = consultaDoc.get(0);

                FacturacionDescarga factura = facDao.findByCufeAndProveedorAndPrefijoAndConsecutivo(documento.getCufe(), documento.getProveedorIdentificacion()
                        , documento.getPrefijo(), documento.getConsecutivo());


                DocumentoClasificado clasificacionDoc = documentoDao.findByRfaPrefijoAndCdoConsecutivo(documento.getPrefijo(), documento.getConsecutivo());

                var estado = documento.getHistoricoEstados().stream()
                        .filter(estadoDto1 -> "RDI".equals(estadoDto1.getEstado()))
                        .findFirst().orElse(null);


                return ResponseConsultaDocumentoDto.builder()
                        .mensaje("Documento encontrado")
                        .cufe(documento.getCufe())
                        .ofeIdentificacion(documento.getOfeIdentificacion())
                        .proIdentificacion(documento.getProveedorIdentificacion())
                        .cdoClasificacion(documento.getCdoClasificacion())
                        .resolucion(documento.getResolucion())
                        .prefijo(documento.getPrefijo())
                        .consecutivo(documento.getConsecutivo())
                        .fechaDocumento(documento.getFechaDocumento())
                        .horaDocumento(documento.getHoraDocumento())
                        .estado(documento.getEstado())
                        .marca(factura != null ? factura.isMarca() : null)
                        .clasificador(clasificacionDoc != null ? clasificacionDoc.getClasificacion().name() : "")
                        .qr(documento.getQr())
                        .signaturevalue(documento.getSignatureValue())
                        .xml(estado != null ? estado.getXml() : null)
                        .pdf(estado != null ? estado.getArchivo() : null)
                        .ultimoEstado(documento.getUltimoEstado())
                        .historicoEstados(documento.getHistoricoEstados())
                        .build();
            }

            return ResponseConsultaDocumentoDto.builder()
                    .mensaje("Documento no encontrado")
                    .build();
        } catch (InterruptedException | ExecutionException e) {
            throw new FacturacionException(FacturacionException.FALLO_CONSULTAR_DOC, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseClasifDocDto clasificacionDocumento(ClasificacionDocumentoRequestDto request) throws FacturacionException {
        String tdeCodigo;

        Boolean existe = documentoDao.existePorPrefijoYClasificado(
                request.getRfaPrefijo(),
                request.getCdoConsecutivo()
        );

        if (existe) {
            String formattedMessage = String.format(FacturacionException.DOCUMENTO_EXISTENTE_TEMPLATE, request.getRfaPrefijo(), request.getCdoConsecutivo());
            return ResponseClasifDocDto.builder()
                    .mensaje(formattedMessage)
                    .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .build();
        }

        switch (request.getTipoDocumentoFAC()) {
            case FC:
                tdeCodigo = "01";
                break;
            case ND:
                tdeCodigo = "91";
                break;
            case NC:
                tdeCodigo = "92";
                break;

            default:
                return ResponseClasifDocDto.builder()
                        .mensaje(FacturacionException.VALIDACION_TIPO_DOCUMENTO)
                        .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build();
        }

        String observacion = "Clasificacion de documentos";
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        List<MultipartFile> files = Arrays.asList(request.getXmlfile(), request.getPdfile())
                .stream()
                .filter(file -> file != null && !file.isEmpty())
                .map(file -> {

                    try {
                        String fileName = file.getOriginalFilename();
                        int index = fileName.lastIndexOf(".");
                        String nameExtension = index != -1 ? fileName.substring(0, index) : "";
                        String ext = index != -1 ? fileName.substring(index) : "";

                        String newFileName = nameExtension + "_" + timestamp + ext;

                        byte[] fileContent = file.getBytes();
                        InputStream inputStream = new ByteArrayInputStream(fileContent);

                        MultipartFile newMultipartFile = new CustomMultipartFile(fileName, newFileName, file.getContentType(), fileContent);
                        return newMultipartFile;

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        MultipartFile[] filesArray = files.toArray(new MultipartFile[0]);

        // Validación mejorada para determinar el valor de nodeId
        String nodeId;
        switch (request.getQuienClasifica()) {
            case CRM:
                nodeId = carpetaCRM;
                break;
            case CORE:
                nodeId = carpetaCORE;
                break;
            case SGDEA:
                nodeId = carpetaSGDEA;
                break;
            case CUIDA2:
                nodeId = carpetaCuida2;
                break;
            default:
                nodeId = null;
                break;
        }

        var res = alfrescoRestClient.uploadFiles(nodeId, filesArray);

        ResponseClasifDocDto responseClasifDocDto = ResponseClasifDocDto.builder()
                .mensaje("Radicación Exitosa")
                .status(String.valueOf(HttpStatus.OK.value()))
                .documentoExitosos(new ArrayList<>())
                .documentoFallidos(new ArrayList<>())
                .build();

        List<String> fallidos = new ArrayList<>();

        res.stream().filter(uploadFile -> "CREATED".equals(uploadFile.getStatus()))
                .forEach(uploadFile -> {
                    documentoDao.create(DocumentoClasificado.builder()
                            .evento("ACEPTACION")
                            .ofeIdentificacion(request.getOfeIdentificacion())
                            .proIdentificacion(request.getProIdentificacion())
                            .tdeCodigo(tdeCodigo)
                            .rfaPrefijo(request.getRfaPrefijo())
                            .cdoConsecutivo(request.getCdoConsecutivo())
                            .cdoFecha(request.getCdoFecha())
                            .cdoObservacion(observacion)
                            .creCodigo(tdeCodigo)
                            .clasificacion(request.getQuienClasifica())
                            .clasificacionOtros(request.getClasificacion())
                            .tipoDocumentoFAC(request.getTipoDocumentoFAC())
                            .nodeId(uploadFile.getId())
                            .formatoArchivo(uploadFile.getName().substring(uploadFile.getName().indexOf(".") + 1))
                            .fechaCargue(uploadFile.getCreatedAt())
                            .tipoArchivo("ENTRADA")
                            .nombreArchivo(uploadFile.getName())
                            .build());
                });


        res.stream().filter(uploadFile -> !"CREATED".equals(uploadFile.getStatus()))
                .forEach(uploadFile -> {
                    String errorMessage = String.format("Error al subir el archivo: %s. Status: %s",
                            uploadFile.getName(), uploadFile.getStatus());

                    fallidos.add(errorMessage);
                });

        if (!fallidos.isEmpty()) {
            responseClasifDocDto.setDocumentoFallidos(fallidos);
            responseClasifDocDto.setMensaje("Proceso completado con errores.");
        }

        return responseClasifDocDto;
    }

}
