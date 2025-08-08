package com.prolinktic.sgdea.infrastructura.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.dtos.OpenEtlDto.*;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.CrearProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.FiltroProveedorDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto.ProveedorResponseDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocEmisionOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.CreateDocSoporteOpen;
import com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento.ResponseCreateDocOpenDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.FiltroResolucionFacturacionDTO;
import com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion.ResolucionResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.*;

@Slf4j
@Service
public class OpenEtlRestTemplate implements OpenEtlRestTemplateImpl {
    @Value("${open.url}")
    private String baseOpen;

    @Value("${open.email}")
    private String email;

    @Value("${open.pass}")
    private String pass;

    @Value("${open.ofe}")
    private String ofe;


    private final RestTemplate restTemplate;

    public OpenEtlRestTemplate(@Qualifier("appRestClient") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //generar token en openETL
    public String createToken() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<OpenResponse> response = restTemplate.exchange(
                    baseOpen + "81/api/login?email=" + email + "&password=" + pass,
                    HttpMethod.POST,
                    requestEntity,
                    OpenResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Token creado exitosamente.");
                return Objects.requireNonNull(response.getBody()).getToken();
            } else {
                log.warn("Respuesta con estado inesperado: {}", response.getStatusCode());
                return "Error al crear token";
            }

        } catch (Exception e) {
            log.error("Error inesperado al crear token: {}", e.getMessage(), e);
            return "Error al crear token";
        }
    }


    @Override
    public Optional<ConsultaDocOpen> descargarFacturacion(String cufe, String fecha) {

        if ((cufe == null || cufe.isEmpty()) && (fecha == null || fecha.isEmpty())) {
            throw new IllegalArgumentException("Debes proporcionar fecha y cufe, o cufe, o fecha.");
        }


        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        if (fecha != null && !fecha.isEmpty()) {
            map.add("fecha", fecha);
        }
        if (cufe != null && !cufe.isEmpty()) {
            map.add("cufe", cufe);
        }
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ConsultaDocOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/consulta-documentos",
                HttpMethod.POST,
                requestEntity,
                ConsultaDocOpen.class
        );
        ConsultaDocOpen resp = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.ofNullable(resp);
        } else {
            ConsultaDocOpen respbad = new ConsultaDocOpen();
            respbad.setMessage(resp.getMessage());
            respbad.setErrors(resp.getErrors());
            return Optional.of(respbad);
        }
    }

    @Override
    public ResponseDataList listarFacturacion(String fechaInicio, String fechaFin) {
        log.info("Entrando a Llamado API Open Lista Facturacion -- /api/recepcion/documentos/listar-documentos");
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("fecha_desde", fechaInicio);
        map.add("fecha_hasta", fechaFin);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
//            RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseDataList> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/listar-documentos",
                HttpMethod.POST,
                requestEntity,
                ResponseDataList.class
        );
        log.info("Finalizando Llamado API Open  Lista Facturacion -- /api/recepcion/documentos/listar-documentos");
        return response.getBody();

    }

    @Override
    public void marcarFacturacion() {

    }

    @Override
    public XmlUblDianDto obtieneXmlUblDIAN(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ofe_identificacion", String.valueOf(ofeIdentificacion));
        map.add("tipo_documento", tipoDocumento);
        map.add("prefijo", prefijo);
        map.add("consecutivo", String.valueOf(consecutivo));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenEtlDataResponse<XmlUblDianDto>> response = restTemplate.exchange(
                baseOpen + "81/api/emision/documentos/obtener-xml-ubl",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        }
        return null;
    }

    @Override
    public PdfNotificacionDto obtenerPdf(String tipoDocumento, String prefijo, Integer consecutivo, Integer ofeIdentificacion) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("ofe_identificacion", String.valueOf(ofeIdentificacion));
        map.add("tipo_documento", tipoDocumento);
        map.add("prefijo", prefijo);
        map.add("consecutivo", String.valueOf(consecutivo));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenEtlDataResponse<PdfNotificacionDto>> response = restTemplate.exchange(
                baseOpen + "81/api/emision/documentos/obtener-pdf",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        }
        return null;
    }

    @Override
    public DatosGeneralesDocDto consultarDatosGenerales(Integer ofeIdentificacion, String prefijo, Integer consecutivo) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OpenEtlDataResponse<DatosGeneralesDocDto>> response = restTemplate.exchange(
                baseOpen + "81/api/emision/documentos/consultar/ofe/" + ofe + "/prefijo/" + prefijo + "/consecutivo/" + consecutivo,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        }
        return null;
    }

    public ResponseRegistroEventoOpen clasificacionDocumento(ClasificacionDocumentoRequestDto requestDto, String tdeCodigo, String observacion, String creCodigo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String token = createToken();
        headers.set("Authorization", "Bearer " + token);

        DocumentoRequestDto documento = new DocumentoRequestDto(
                requestDto.getOfeIdentificacion(),
                requestDto.getProIdentificacion(),
                tdeCodigo,
                requestDto.getRfaPrefijo(),
                requestDto.getCdoConsecutivo(),
                requestDto.getCdoFecha(),
                observacion,
                creCodigo
        );

        EventoDocumentoRequestDto eventoDocumentoRequest = new EventoDocumentoRequestDto();
        eventoDocumentoRequest.setEvento("ACEPTACION");
        eventoDocumentoRequest.setDocumentos(Collections.singletonList(documento));

        HttpEntity<EventoDocumentoRequestDto> requestEntity = new HttpEntity<>(eventoDocumentoRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ResponseRegistroEventoOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/registrar-evento",
                HttpMethod.POST,
                requestEntity,
                ResponseRegistroEventoOpen.class
        );

        ResponseRegistroEventoOpen resp = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            return resp;
        } else {
            return new ResponseRegistroEventoOpen(response.getStatusCode() + " " + resp.getMessage(), null, resp.getFallidos());
        }
    }

    @Override
    public List<ConsultaDocDto> consultarDocumentoRecepcion(Integer ofeIdentificacion, String prefijo, Integer consecutivo,
                                                            String tipo, String proveedor, String cufe, String fecha) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        String url = baseOpen + "81/api/recepcion/documentos/consulta-documentos?";

        if (cufe != null && !cufe.isEmpty() && fecha != null && !fecha.isEmpty()) {
            url += "fecha=" + fecha + "&cufe=" + cufe;
        } else if (ofeIdentificacion != null && proveedor != null && !proveedor.isEmpty() &&
                   tipo != null && !tipo.isEmpty() && prefijo != null && !prefijo.isEmpty() && consecutivo != null) {
            url += "ofe=" + ofeIdentificacion + "&proveedor=" + proveedor + "&tipo=" + tipo +
                   "&prefijo=" + prefijo + "&consecutivo=" + consecutivo;
        } else {
            throw new IllegalArgumentException("Debes proporcionar fecha y cufe, o ofe, proveedor, tipo, prefijo y consecutivo.");
        }

        ResponseEntity<OpenEtlListDataResponse<ConsultaDocDto>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getData();
        }

        return null;
    }

    @Override
    public ResponseRegistroOpen registrarDocumentoFileRecepcion(Integer ofeIdentificacion, File pdf, File xml, String cufe) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("ofe_identificacion", ofeIdentificacion);
        bodyMap.add("documentos", cufe);
        FileSystemResource pdf1 = new FileSystemResource(pdf);
        FileSystemResource xml1 = new FileSystemResource(xml);
        bodyMap.add(cufe + "_pdf", pdf1);
        bodyMap.add(cufe + "_xml", xml1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<ResponseRegistroOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/registrar-documentos-file",
                HttpMethod.POST,
                requestEntity,
                ResponseRegistroOpen.class
        );
        ResponseRegistroOpen resp = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            return resp;
        } else {
            ResponseRegistroOpen respbad = new ResponseRegistroOpen(response.getStatusCode() + " " + response.getBody().getMessage(), response.getBody().getErrors(), null);
            return respbad;
        }
    }

    @Override
    public ResponseRegistroOpen registrarDocumentoJsonRecepcion(Integer ofeIdentificacion, List<DocumentoDTO> documentos) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Requested-With", "XMLHttpRequest");
        headers.set("Accept", "application/json");

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("ofe_identificacion", ofeIdentificacion);
        requestMap.put("documentos", documentos);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestMap, headers);

        ResponseEntity<ResponseRegistroOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/registrar-documentos-json",
                HttpMethod.POST,
                requestEntity,
                ResponseRegistroOpen.class
        );

        ResponseRegistroOpen resp = response.getBody();

        return resp;
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return resp;
//        } else {
//            ResponseRegistroOpen respbad = new ResponseRegistroOpen(
//                    response.getStatusCode() + " " + response.getBody().getMessage(),
//                    response.getBody().getErrors(),
//                    null
//            );
//            return respbad;
//        }
    }

    @Override
    public ResponseRegistroEventoOpen registroEventoRecepcion(String request) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.set("Content-Type", "application/json");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseRegistroEventoOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/registrar-evento",
                HttpMethod.POST,
                requestEntity,
                ResponseRegistroEventoOpen.class
        );
        ResponseRegistroEventoOpen resp = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            return resp;
        } else {
            ResponseRegistroEventoOpen respbad = new ResponseRegistroEventoOpen(response.getStatusCode() + " " + resp.getMessage(), null, resp.getFallidos());
            return respbad;
        }
    }

    @Override
    public ResponseRegistroOpen registroDocumento(Integer ofeIdentificacion, File pdf, File xml, String cufe) {
        HttpHeaders headers = new HttpHeaders();
        String token = createToken();
        headers.set("authorization", "Bearer " + token);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("ofe_identificacion", ofeIdentificacion);
        bodyMap.add("documentos", cufe);
        FileSystemResource pdf1 = new FileSystemResource(pdf);
        FileSystemResource xml1 = new FileSystemResource(xml);
        bodyMap.add(cufe + "_pdf", pdf1);
        bodyMap.add(cufe + "_xml", xml1);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);
        ResponseEntity<ResponseRegistroOpen> response = restTemplate.exchange(
                baseOpen + "81/api/recepcion/documentos/registrar-documentos-file",
                HttpMethod.POST,
                requestEntity,
                ResponseRegistroOpen.class
        );
        ResponseRegistroOpen resp = response.getBody();
        if (response.getStatusCode().is2xxSuccessful()) {
            return resp;
        } else {
            ResponseRegistroOpen respbad = new ResponseRegistroOpen(response.getStatusCode() + " " + response.getBody().getMessage(), response.getBody().getErrors(), null);
            return respbad;
        }
    }

    @Override
    public String obtenerAttachedDocumentDocumentoEnEmision(String tipoDoc, String prefijo, String consecutivo, Integer ofeIdentificacion) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "ofe_identificacion", ofe,
                "tipo_documento", tipoDoc,
                "prefijo", prefijo,
                "consecutivo", consecutivo
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate client = new RestTemplate();

        String url = baseOpen + "81/api/emision/documentos/obtener-attached-document";

        ResponseEntity<Map<String, Map<String, String>>> response = client
                .exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {
                });

        Map<String, Map<String, String>> responseBody = response.getBody();

        if (!response.getStatusCode().is2xxSuccessful() || responseBody == null) {
            return "";
        }

        Map<String, String> data = responseBody.get("data");

        if (data == null)
            return "";

        String base64AttachedDocument = data.get("attached_document");
//
//        return base64AttachedDocument != null
//                ? Base64.getDecoder().decode(base64AttachedDocument)
//                : new byte[0];

        return base64AttachedDocument;
    }

    @Override
    public ResponseCreateDocOpenDTO crearDocumentoEnEmision(CreateDocEmisionOpen documento) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateDocEmisionOpen> request = new HttpEntity<>(documento, headers);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);

        RestTemplate client = new RestTemplate(factory);

        String url = baseOpen + "82/api/registrar-documentos";

        ResponseEntity<ResponseCreateDocOpenDTO> response = client
                .exchange(url, HttpMethod.POST, request, ResponseCreateDocOpenDTO.class);

//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
//            return response.getBody();

        return response.getBody();
    }

    @Override
    public ResponseCreateDocOpenDTO crearDocumentoSoporte(CreateDocSoporteOpen documento) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateDocSoporteOpen> request = new HttpEntity<>(documento, headers);

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30000);

        RestTemplate client = new RestTemplate(factory);

        String url = baseOpen + "82/api/registrar-documentos";

        ResponseEntity<ResponseCreateDocOpenDTO> response = client
                .exchange(url, HttpMethod.POST, request, ResponseCreateDocOpenDTO.class);

//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null)
//            return response.getBody();

        return response.getBody();
    }

    @Override
    public ProveedorResponseDTO listarProveedores(FiltroProveedorDTO filtro) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String endpoint = baseOpen + "81/api/configuracion/lista-proveedores";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(endpoint)
                .queryParam("start", filtro.getStart())
                .queryParam("length", filtro.getLength());

        if (filtro.getBuscar() != null && !filtro.getBuscar().isEmpty()) {
            builder.queryParam("buscar", filtro.getBuscar());
        }
        if (filtro.getColumnaOrden() != null && !filtro.getColumnaOrden().isEmpty()) {
            builder.queryParam("columnaOrden", filtro.getColumnaOrden());
        }
        if (filtro.getOrdenDireccion() != null && !filtro.getOrdenDireccion().isEmpty()) {
            builder.queryParam("ordenDireccion", filtro.getOrdenDireccion());
        }

        HttpEntity<Void> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProveedorResponseDTO> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                request,
                ProveedorResponseDTO.class
        );

        return response.getBody();
    }

    @Override
    public CrearProveedorResponseDTO crearProveedor(CrearProveedorDTO request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CrearProveedorDTO> entity = new HttpEntity<>(request, headers);

        String endpoint = baseOpen + "81/api/configuracion/proveedores";

        try {
            RestTemplate client = new RestTemplate();
            ResponseEntity<CrearProveedorResponseDTO> response = client.exchange(
                    endpoint,
                    HttpMethod.POST,
                    entity,
                    CrearProveedorResponseDTO.class
            );

            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(ex.getResponseBodyAsString(), CrearProveedorResponseDTO.class);
            } catch (Exception parseError) {
                CrearProveedorResponseDTO error = new CrearProveedorResponseDTO();
                error.setMessage("Error al procesar la respuesta del servidor.");
                return error;
            }
        } catch (Exception ex) {
            CrearProveedorResponseDTO error = new CrearProveedorResponseDTO();
            error.setMessage("Error al intentar crear el proveedor: " + ex.getMessage());
            return error;
        }
    }

    @Override
    public ResolucionResponseDTO listarResolucionesFacturacion(FiltroResolucionFacturacionDTO filtro) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + createToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String endpoint = baseOpen + "81/api/configuracion/lista-resoluciones-facturacion";

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(endpoint)
                .queryParam("start", filtro.getStart())
                .queryParam("length", filtro.getLength());

        if (filtro.getBuscar() != null && !filtro.getBuscar().isEmpty()) {
            builder.queryParam("buscar", filtro.getBuscar());
        }
        if (filtro.getColumnaOrden() != null && !filtro.getColumnaOrden().isEmpty()) {
            builder.queryParam("columnaOrden", filtro.getColumnaOrden());
        }
        if (filtro.getOrdenDireccion() != null && !filtro.getOrdenDireccion().isEmpty()) {
            builder.queryParam("ordenDireccion", filtro.getOrdenDireccion());
        }

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ResolucionResponseDTO> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    request,
                    ResolucionResponseDTO.class
            );

            log.info("Respuesta exitosa del servicio lista-resoluciones-facturacion");
            return response.getBody();

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("Error al consultar resoluciones de facturación: {}", ex.getMessage());
            ResolucionResponseDTO errorResponse = new ResolucionResponseDTO();
            errorResponse.setTotal(0);
            errorResponse.setFiltrados(0);
            errorResponse.setData(List.of());
            return errorResponse;
        } catch (Exception ex) {
            log.error("Error inesperado al consultar resoluciones de facturación: {}", ex.getMessage(), ex);
            ResolucionResponseDTO errorResponse = new ResolucionResponseDTO();
            errorResponse.setTotal(0);
            errorResponse.setFiltrados(0);
            errorResponse.setData(List.of());
            return errorResponse;
        }
    }


}
