package com.prolinktic.sgdea.service.radicado;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.prolinktic.sgdea.client.AlfrescoRestClient;
import com.prolinktic.sgdea.client.SGDEARestClient;
import com.prolinktic.sgdea.dao.SeccionSubSeccion.SeccionSubSeccionDao;
import com.prolinktic.sgdea.dao.tipologiaDocumental.TipologiaDocumentalDao;
import com.prolinktic.sgdea.dtos.AlfrescoNode;
import com.prolinktic.sgdea.dtos.AlfrescoNodeList;
import com.prolinktic.sgdea.dtos.Entry;
import com.prolinktic.sgdea.dtos.buscarDocumento.ResponseBuscarDocumento;
import com.prolinktic.sgdea.dtos.consultarArchivo.ResponseProperties;
import com.prolinktic.sgdea.dtos.dependencias.DataDependencias;
import com.prolinktic.sgdea.dtos.dependencias.ResponseDependencias;
import com.prolinktic.sgdea.dtos.radicado.Properties;
import com.prolinktic.sgdea.dtos.radicado.*;
import com.prolinktic.sgdea.exception.AnexosInvalidosException;
import com.prolinktic.sgdea.exception.CarpetaNoEncontradaException;
import com.prolinktic.sgdea.exception.exceptions.ControlException;
import com.prolinktic.sgdea.infrastructura.gateway.AlfrescoClient;
import com.prolinktic.sgdea.model.Alfesco.CreateNode;
import com.prolinktic.sgdea.model.tipologiaDocumental.TipologiaDocumental;
import com.prolinktic.sgdea.service.consultarDocumento.DocumentoService;
import com.prolinktic.sgdea.service.dependencias.DependenciasService;
import com.prolinktic.sgdea.service.instrumento.InstrumentoService;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

import static com.prolinktic.sgdea.util.PQRDUtils.convertDTOToMap;
import static com.prolinktic.sgdea.util.PQRDUtils.convertDTOToMultiValueMap;

@Service
public class RadicadoServiceImpl implements RadicadoService {

//    public static final String URL = "jdbc:postgresql://34.75.252.120:5432/gcpprolinktic";
//    private static final String USUARIO = "postgres";
//    private static final String CONTRASEÑA = "rfcw33nN73ae4DE";

    @Autowired
    private DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(RadicadoServiceImpl.class);

    @Value("${alfresco.url}")
    private String alfrescoUrl;

    @Value("${alfresco.almacenradicado}")
    private String almacenradicados;

    @Value("${alfresco.credentials.gcp}")
    private String credencialesGCP;

    private final AlfrescoClient alfrescoClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private final DocumentoService documentoService;
    private final AlfrescoRestClient alfrescoRestClient;
    private final SGDEARestClient sgdeaRestClient;
    private final InstrumentoService instrumentoService;
    private final SeccionSubSeccionDao seccionSubSeccionDao;
    private final DependenciasService dependenciasService;
    private final TipologiaDocumentalDao tipologiaDocumentalDao;

    @Autowired
    public RadicadoServiceImpl(AlfrescoClient alfrescoClient, DocumentoService documentoService, AlfrescoRestClient alfrescoRestClient, SGDEARestClient sgdeaRestClient, InstrumentoService instrumentoService, SeccionSubSeccionDao seccionSubSeccionDao, DependenciasService dependenciasService, TipologiaDocumentalDao tipologiaDocumentalDao) {
        this.alfrescoClient = alfrescoClient;
        this.documentoService = documentoService;
        this.alfrescoRestClient = alfrescoRestClient;
        this.sgdeaRestClient = sgdeaRestClient;
        this.instrumentoService = instrumentoService;
        this.seccionSubSeccionDao = seccionSubSeccionDao;
        this.dependenciasService = dependenciasService;
        this.tipologiaDocumentalDao = tipologiaDocumentalDao;
    }

    @Override
    public Object createEntrada(RadicadoEntradaDTO radicadoEntradaDTO, MultipartFile[] anexos, MultipartFile file) throws Exception {
        try {
            String almacenradicadosFinal = "";
            List<DescripcionAnexoDTO> descripcionAnexoDTOList2 = new ArrayList<>();

            //inicio de primeras validaciones
            //1- validar que exista el id de la dependencia, si existe obtener el nodeId del registro asociado
            String destinatarioDependencia = radicadoEntradaDTO.getDestinatarioDependencia(); //llega un id
            //campo destinatarioDependencia corresponde a campo codigo en tabla seccionSubseccion
            Object nodeIdDependenciaValidado = validarDependencia(destinatarioDependencia);
            if (nodeIdDependenciaValidado == null) {
                //finalizar el proceso
                return "destinatarioDependencia no existe";
            }


            //2- validar en alfresco que exista la serieRadicacion
            String serieR = radicadoEntradaDTO.getSerieRadicacion();
            // Boolean validarSerieRadicacion = validarLaSerieRadicacion(serieR);
            if (serieR.equals("")) {
                //finalizar el proceso
                return "Serie Radicacion no puede ser nulo";
            }

            if (file.isEmpty() || file == null) {
                //finalizar el proceso
                return "El campo File no puede ser nulo";
            }



            //3- validar en alfresco que exista la SubSerieRadicacion
            String subSerieR = radicadoEntradaDTO.getSubserieRadicacion();
            //  Boolean validarSuSerieRadicacion = validarLaSubSerieRadicacion(subSerieR);
            if (subSerieR.equals("")) {
                //radicar en el nodeId que se ingresa en la serie, else: radicar en este node id
                almacenradicadosFinal = serieR;
                // return "Sub Serie Radicacion no existe";
            } else {
                almacenradicadosFinal = subSerieR;
            }
            //fin de primeras validaciones

            ResponseDependencias response = dependenciasService.consultarDocumento();
            DataDependencias maestroDependencia = response.getData().stream()
                    .filter(sr -> sr.getCodigoOficinaProductora().replace(".0", "")
                            .equals(String.valueOf(radicadoEntradaDTO.getOficinaProductora()).replace(".0", "")))
                    .findFirst().orElse(null);

            if (maestroDependencia == null) {
                return String.format("No se encontró la dependencia con el dato ingresado en Oficina Productora: %s.",
                        radicadoEntradaDTO.getDestinatarioDependencia());
            }

            Entry serie = obtenerEntryPorId(maestroDependencia.getNodeId(), radicadoEntradaDTO.getSerieRadicacion());
            if (serie == null) {
                return String.format("No se encontró la Serie: %s para la dependencia: %s.",
                        radicadoEntradaDTO.getSerieRadicacion(), radicadoEntradaDTO.getDestinatarioDependencia());
            }

            Entry subSerie = obtenerEntryPorId(serie.getId(), radicadoEntradaDTO.getSubserieRadicacion());
//            if (subSerie == null) {
//                return String.format("No se encontró la subSerie: %s para la Serie: %s y la dependencia: %s.",
//                        radicadoEntradaDTO.getSubserieRadicacion(), radicadoEntradaDTO.getSerieRadicacion(), radicadoEntradaDTO.getDestinatarioDependencia());
//            }


            String nombreIdType = "";
            Map<String, Object> instrumentos = instrumentoService.consultarInstrumentos(String.valueOf(radicadoEntradaDTO.getOficinaProductora()), null);
            if (instrumentos.containsKey("body")) {
                Object bodyObj = instrumentos.get("body");

                if (bodyObj instanceof Collection) {
                    Collection<Map<String, Object>> bodyCollection = (Collection<Map<String, Object>>) bodyObj;

                    Optional<Map<String, Object>> firstItem = bodyCollection.stream().findFirst();
                    if (firstItem.isPresent()) {
                        Map<String, Object> item = firstItem.get();
                        if (item.containsKey("tipo")) {
                            Object tipoObj = item.get("tipo");
                            if (tipoObj instanceof Collection) {
                                Collection<Map<String, Object>> tipos = (Collection<Map<String, Object>>) tipoObj;
                                nombreIdType = tipos.stream()
                                        .filter(tipo -> tipo.get("idTipoDoc").equals(radicadoEntradaDTO.getTipoDocumentalRadicacion()))
                                        .map(tipo -> (String) tipo.get("nombre"))
                                        .findFirst()
                                        .orElse("");
                            }
                        }
                    }
                }
            }

            System.out.println(nombreIdType);


            ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            AlfrescoResponseDTO metadataRadicados = alfrescoClient.getAllPlantilla(almacenradicadosFinal);
            String nombreCarpeta = sgdeaRestClient.generarSecuencia(radicadoEntradaDTO.getTipoRadicado());

            Properties properties = getProperties(radicadoEntradaDTO, nombreCarpeta);

            //inicio de propiedades del cmfolder
            Map<String, Object> propertiesFolder = new HashMap<>();
            propertiesFolder.put("ltexp:docNumber", radicadoEntradaDTO.getNumeroDocumentoRemitente());
            propertiesFolder.put("ltexp:docType", radicadoEntradaDTO.getTipoDocumentoRemitente());
            propertiesFolder.put("MetRad:Fecha_de_Recepcion", LocalDate.now().toString());
            propertiesFolder.put("MetRad:Remitente", radicadoEntradaDTO.getNombreRemitente());
            propertiesFolder.put("MetRad:Usuario_radicador", "POSITIVA");
            propertiesFolder.put("MetRad:asunto", radicadoEntradaDTO.getObservacionPpal());
            propertiesFolder.put("MetRad:id_numero", radicadoEntradaDTO.getNumeroDocumentoRemitente());
            propertiesFolder.put("MetRad:id_type", TipoDocumentoIdentidad.getNombreById(radicadoEntradaDTO.getTipoDocumentoRemitente()));


            propertiesFolder.put("AF:Creat_Date", LocalDateTime.now().toString());
            propertiesFolder.put("AF:Id_Nombre", radicadoEntradaDTO.getNombreRemitente());
            propertiesFolder.put("AF:Id_Numero_identificacion", radicadoEntradaDTO.getNumeroDocumentoRemitente());
            propertiesFolder.put("AF:Serie", serie.getName());
            propertiesFolder.put("AF:Subserie", Optional.ofNullable(subSerie).map(Entry::getName).orElse(""));
            propertiesFolder.put("AF:id_type", TipoDocumentoIdentidad.getNombreById(radicadoEntradaDTO.getTipoDocumentoRemitente()));
            //fin de propiedades del cmfolder

            CreateNode createNode = CreateNode.builder()
                    .name(nombreCarpeta)
//                .nodeType("ltexp:Expediente")
                    .nodeType("MetRad:Metadatos_Radicado")
                    .properties(propertiesFolder)
                    .build();

        /*
             CreateNode createNode = CreateNode.builder()
                .name(nombreCarpeta)
                .nodeType("cm:folder")
                .properties(propertiesFolder)
                .build();

         */


            AlfrescoNode respFolder = alfrescoRestClient.createNode(almacenradicadosFinal, convertDTOToMap(createNode));

            String idParaAnexarPrincipalYAnexos = respFolder.getEntry().getId();

            EntradaAlfresco entradaAlfresco = new EntradaAlfresco();
            entradaAlfresco.setName("pp-" + file.getOriginalFilename());
            entradaAlfresco.setNodeType("proltk:comunicacion_oficial");

            entradaAlfresco.setProperties(properties);

            String nombre = generateFileName(file, radicadoEntradaDTO, null);

            MultiValueMap<String, Object> dataNodeType = new LinkedMultiValueMap<>();
            dataNodeType.add("filedata", new HttpEntity<>(file.getResource()));
            dataNodeType.add("nodeType", "proltk:comunicacion_oficial");
//            dataNodeType.add("name", "pp-" + file.getOriginalFilename());
            dataNodeType.add("name", nombre);
            dataNodeType.add("proltk:nombreOriginalArchivo", file.getOriginalFilename());
            dataNodeType.add("proltk:numero_radicado", nombreCarpeta);

            //crear la carpeta principal en alfresco con su documento principal
            AlfrescoNode node = alfrescoRestClient.createNode(idParaAnexarPrincipalYAnexos, dataNodeType);

            String idradicadoAux = String.valueOf(new StringBuilder(node.toString()).reverse()).substring(58, 94);
            String idRadicado = String.valueOf(new StringBuilder(idradicadoAux).reverse());

            if (anexos != null) {
                String jsonArrayString = radicadoEntradaDTO.getDescripcionAnexo();
                List<DescripcionAnexoDTO> descripcionAnexoDTOList = objectMapper.readValue(jsonArrayString, new TypeReference<>() {
                });

                descripcionAnexoDTOList2 = descripcionAnexoDTOList;

                if (anexos.length != descripcionAnexoDTOList.size()) {
                    throw new ControlException(HttpStatus.BAD_REQUEST, "No coinciden la cantidad de anexos y su descripción");
                }

                if (!Objects.equals(idRadicado, "") && !Objects.equals(anexos[0].getOriginalFilename(), "")) {

                    int i = 0;
                    for (MultipartFile anexo : anexos) {
                        DescripcionAnexoDTO descripcionAnexo = descripcionAnexoDTOList.get(i);
                        Integer tipoDocumental = null;

                        if (descripcionAnexo.getTipoDocumental() > 0) {
                            try {
                                tipoDocumental = descripcionAnexo.getTipoDocumental();
                            } catch (NumberFormatException e) {
                                log.warn("Tipo documental inválido: " + descripcionAnexo.getTipoDocumental());
                            }
                        }

                        String nombreArchivo = generateFileName(anexo, null, tipoDocumental);

                        entradaAlfresco.getProperties().setNombreOrginalArchivo(anexo.getOriginalFilename());
                        entradaAlfresco.setName(nombreArchivo);
//                        MultiValueMap<String, Object> dataNodeType2 = new LinkedMultiValueMap<>();
//                        dataNodeType2.add("filedata", new HttpEntity<>(anexo.getResource()));
//                        dataNodeType2.addAll(convertDTOToMultiValueMap(entradaAlfresco));

                        MultiValueMap<String, Object> dataNodeType2 = new LinkedMultiValueMap<>();
                        dataNodeType2.add("filedata", new HttpEntity<>(anexo.getResource()));
                        dataNodeType2.add("nodeType", "proltk:comunicacion_oficial");
                        dataNodeType2.add("name", nombreArchivo);
                        dataNodeType2.add("proltk:nombreOriginalArchivo", anexo.getOriginalFilename());
                        dataNodeType2.add("proltk:numero_radicado", nombreCarpeta);

                        //enviar el anexo a alfresco que se esta iterando en el for
                        AlfrescoNode node2 = alfrescoRestClient.createNode(idParaAnexarPrincipalYAnexos, dataNodeType2);

//                        Map<String, Object> propertiesFolder2 = new HashMap<>();
//                        propertiesFolder2.put("proltk:nombreOriginalArchivo", anexo.getOriginalFilename());
//                        documentoService.updateMetadata(node2.getEntry().getId(), propertiesFolder2);
                        i++;
                    }
                }
            } else {//entra aca si los anexos son nulos
                //   updatePropertiesFile(nombreCarpeta, entradaAlfresco, properties);
            }


            //inicio de setar propiedades
            Map<String, Object> propertiesFile = new HashMap<>();
            propertiesFile.put("proltk:numero_radicado", nombreCarpeta);
            propertiesFile.put("proltk:remitenteDireccion", radicadoEntradaDTO.getRemitenteDireccion());
            propertiesFile.put("proltk:numeroDocumentoRemitente", radicadoEntradaDTO.getNumeroDocumentoRemitente());
            propertiesFile.put("proltk:oficinaProductora", radicadoEntradaDTO.getOficinaProductora());
            propertiesFile.put("proltk:serieRadicacion", radicadoEntradaDTO.getSerieRadicacion());
            propertiesFile.put("proltk:remitenteMunicipio", radicadoEntradaDTO.getRemitenteMunicipio());
            propertiesFile.put("proltk:remitenteTelefono", radicadoEntradaDTO.getRemitenteTelefono());
            //    propertiesFile.put("proltk:tipoDocumentoRemitente", radicadoEntradaDTO.getTipoDocumentoRemitente());
            propertiesFile.put("proltk:asuntoRadicacion", radicadoEntradaDTO.getAsuntoRadicacion());
            propertiesFile.put("proltk:subserieRadicacion", radicadoEntradaDTO.getSubserieRadicacion());
            propertiesFile.put("proltk:idTramite", radicadoEntradaDTO.getIdTramite());
            propertiesFile.put("proltk:tipoDocumentalRadicacion", radicadoEntradaDTO.getTipoDocumentalRadicacion());
            propertiesFile.put("proltk:destinatarioNombresApellidos", radicadoEntradaDTO.getDestinatarioNombresApellidos());
            propertiesFile.put("proltk:remitenteTipoPersona", radicadoEntradaDTO.getRemitenteTipoPersona());
            propertiesFile.put("proltk:nombreRemitente", radicadoEntradaDTO.getNombreRemitente());
            propertiesFile.put("proltk:fondoRadicacion", radicadoEntradaDTO.getFondoRadicacion());
            propertiesFile.put("proltk:observacionPpal", radicadoEntradaDTO.getObservacionPpal());
            propertiesFile.put("proltk:medioRecepcion", radicadoEntradaDTO.getMedioRecepcion());
            propertiesFile.put("proltk:identificadorSistema", radicadoEntradaDTO.getIdentificadorSistema());
            propertiesFile.put("proltk:remitenteDepartamento", radicadoEntradaDTO.getRemitenteDepartamento());
            propertiesFile.put("proltk:tipoRadicado", radicadoEntradaDTO.getTipoRadicado());
            propertiesFile.put("proltk:remitentePais", radicadoEntradaDTO.getRemitentePais());
            propertiesFile.put("proltk:destinatarioUbicacionSede", radicadoEntradaDTO.getDestinatarioUbicacionSede());
            propertiesFile.put("proltk:radicadoOrigen", radicadoEntradaDTO.getRadicadoOrigen());
            propertiesFile.put("proltk:fechaDocumento", radicadoEntradaDTO.getFechaDocumento());
            propertiesFile.put("proltk:destinatarioDependencia", radicadoEntradaDTO.getDestinatarioDependencia());
            propertiesFile.put("proltk:remitenteCorreoElectronico", radicadoEntradaDTO.getRemitenteCorreoElectronico()); //27 propiedades hasta aca

            propertiesFile.put("AF:Creat_Date", LocalDateTime.now().toString());
            propertiesFile.put("AF:Id_Nombre", radicadoEntradaDTO.getNombreRemitente());
            propertiesFile.put("AF:Id_Numero_identificacion", radicadoEntradaDTO.getNumeroDocumentoRemitente());
            propertiesFile.put("AF:Serie", serie.getName());
            propertiesFile.put("AF:Subserie",Optional.ofNullable(subSerie).map(Entry::getName).orElse(""));
            propertiesFile.put("AF:id_type", nombreIdType);
            //final de setear propiedades de archivos


            //inicio de obtener el parentId de la carpe ta de radicado que se acabo de crear
            String peticion = "{\n" +
                    "  \"query\": {\n" +
                    "    \"query\": \"SELECT * FROM cmis:folder WHERE cmis:name='" + nombreCarpeta + "'\",\n" +
                    "    \"language\": \"cmis\"\n" +
                    "  }\n" +
                    "}";
            Gson g = new Gson();
            Object peticionJson = g.fromJson(peticion, Object.class);
            AlfrescoResponseDTO metadataRadicados1 = alfrescoClient.buscarNodeIdPorNombreCarpeta(peticionJson);
            String padreDeCarpeta = metadataRadicados1.getList().getEntries().get(0).getEntry().getParentId();
            //fin  de obtener el parentId de la carpeta de radicado que se acabo de crear
            documentoService.updateMetadata(padreDeCarpeta, propertiesFolder);


            //     ResponseBuscarDocumento respuesta =  documentoService.buscarDocumento2(nombreCarpeta, almacenradicadosFinal, propertiesFile);
            ResponseBuscarDocumento respuesta = documentoService.buscarDocumento2(nombreCarpeta, padreDeCarpeta, propertiesFile);
            respuesta.setDescripcionAnexoDTOList(descripcionAnexoDTOList2);

            for (int i = 0; i < respuesta.getDocumentos().size(); i++) {
                //   respuesta.getDocumentos().get(i).getPropiedades().setRemitentePais(properties.getRemitentePais());
                ResponseProperties responseProperties = new ResponseProperties();
                responseProperties.setRemitentePais(radicadoEntradaDTO.getRemitentePais());
                responseProperties.setMedioRecepcion(radicadoEntradaDTO.getMedioRecepcion());
                responseProperties.setIdentificadorSistema(radicadoEntradaDTO.getIdentificadorSistema());
                responseProperties.setOficinaProductora(String.valueOf(radicadoEntradaDTO.getOficinaProductora()));
                responseProperties.setRemitenteMunicipio(radicadoEntradaDTO.getRemitenteMunicipio());
                responseProperties.setTipoDocumental(radicadoEntradaDTO.getTipoDocumentalRadicacion());
                responseProperties.setTipoDocumento(radicadoEntradaDTO.getTipoDocumentoRemitente());
                responseProperties.setRemitenteTipoPersona(radicadoEntradaDTO.getRemitenteTipoPersona());
                responseProperties.setRemitenteDepartamento(radicadoEntradaDTO.getRemitenteDepartamento());
                responseProperties.setFondo(radicadoEntradaDTO.getFondoRadicacion());
                respuesta.getDocumentos().get(i).setPropiedades(responseProperties);
            }

            //fin de ajuste temporal
            return respuesta;

        } catch (Exception e) {
            log.error("Error en la solicitud: " + e.getMessage());
            throw new RuntimeException("Error al Radicar el Documento");
        }
    }

    private Entry obtenerEntryPorId(String nodeId, String id) throws ControlException {
        AlfrescoNodeList nodeList = documentoService.obtenerHijosPorNodeId(nodeId);
        return nodeList.getList().getEntries().stream()
                .filter(entry -> entry.getEntry().getId().equals(id))
                .findFirst()
                .map(AlfrescoNode::getEntry)
                .orElse(null);
    }

    String generateFileName(MultipartFile file, RadicadoEntradaDTO radicadoEntradaDTO, Integer tipoDocumentalPersonalizado) {

        String originalFileName = file.getOriginalFilename();
        String formatoArchivo = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);


        ZoneId zonaBogota = ZoneId.of("America/Bogota");
        long timestamp = ZonedDateTime.now(zonaBogota).toInstant().toEpochMilli();


        int tipoDocumental = (tipoDocumentalPersonalizado != null && tipoDocumentalPersonalizado > 0)
                ? tipoDocumentalPersonalizado
                : radicadoEntradaDTO.getTipoDocumentalRadicacion();

        if (tipoDocumental <= 0) {

            String nameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            return String.format("%s_%d.%s", nameWithoutExtension, timestamp, formatoArchivo);
        }


        try {
            Optional<TipologiaDocumental> tipologiaDocumental =
                    tipologiaDocumentalDao.findById((long) tipoDocumental);

            if (tipologiaDocumental.isPresent()) {
                String nombreTipologia = tipologiaDocumental.get().getNombre()
                        .replace(" ", "_")
                        .replaceAll("[\\\\/:*?\"<>|]", "_");

                return String.format("%s_%d.%s",
                        nombreTipologia,
                        timestamp,
                        formatoArchivo);
            } else {
                String nameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf("."));
                return String.format("%s_%d.%s", nameWithoutExtension, timestamp, formatoArchivo);
            }
        } catch (NumberFormatException e) {
            log.error("Error en la solicitud generateFileName : " + e.getMessage());
            throw new RuntimeException("Error al generar nombre del archivo");
        }
    }

    public void updateMetadata(String nodeId, Map<String, Object> newMetadata) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Adicionamos el header para realizar la authenticacion
        headers.set("Authorization", "Basic YWRtaW46YWRtaW4=");

        Map<String, Object> properties = new HashMap<>();
        properties.put("properties", newMetadata);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(properties, headers);

        // se actualiza la metadata del node en aldresco para radicados
        restTemplate.exchange(
                alfrescoUrl + "/nodes/" + nodeId,
                HttpMethod.PUT,
                request,
                String.class
        );
    }



    //desde aca
    //desde aca
    //desde aca
    //desde aca
    //desde aca
    Object validarDependencia(String cod) throws JSONException {
        //aca consultar la base de datos
        Object respuesta = obtenerNodeAlfrescoDeSeccionSubSeccion(cod);
        if(respuesta != null){//si existe el node id de la dependencia en la BD
            return respuesta;
        }else {
            return null;
        }
    }


    Boolean validarLaSerieRadicacion(String serieRa){
        if(true){//si existe la serie Radicacion en alfresco
            return true;
        }else {
            return false;
        }
    }


    Boolean validarLaSubSerieRadicacion(String subSerieRa){
        if(true){//si existe la sub serie Radicacion en alfresco
            return true;
        }else {
            return false;
        }
    }

    public Object obtenerNodeAlfrescoDeSeccionSubSeccion(String codigo) {

        String sqlBase = "SELECT idnodealfresco FROM public.seccionsubseccion WHERE codigo IN (?, ?)";
        JSONObject json = null;

        String codigoLimpio = codigo != null ? codigo.trim() : "";
        String codigoAlterno = codigoLimpio.endsWith(".0")
                ? codigoLimpio.replace(".0", "")
                : codigoLimpio + ".0";

        try (
                Connection conexion = dataSource.getConnection();
                PreparedStatement preparedStatement = conexion.prepareStatement(sqlBase)
        ) {
            preparedStatement.setString(1, codigoLimpio);
            preparedStatement.setString(2, codigoAlterno);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    json = new JSONObject();
                    json.put("idnodealfresco", resultSet.getObject("idnodealfresco"));
                } else {
                    System.out.println("No se encontró ningún registro con el código: " + codigo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return (json != null) ? json.opt("idnodealfresco") : null;
    }

//    public Object obtenerNodeAlfrescoDeSeccionSubSeccion(String codigo) throws JSONException {
//
//        Connection conexion = null;
//        PreparedStatement preparedStatement = null;
//
//        ResultSet resultSet = null;
//
//        Object objeto2 = null;
//        JSONObject json = null;
//        try {
//            // Paso 1: Cargar el controlador JDBC
//            Class.forName("org.postgresql.Driver");
//
//            // Paso 2: Establecer la conexión
//            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
//
//
//            // Paso 3: Preparar la consulta SQL
//            String sql = "Select idnodealfresco from seccionsubseccion where codigo=?";
//           // String sql = "select * from seccionsubseccion";
//            preparedStatement = conexion.prepareStatement(sql);
//
//            // Paso 4: Establecer los valores de los parámetros
//           // preparedStatement.setInt(1, codigo);
//            //   preparedStatement.setInt(1, "10001");
//         preparedStatement.setString(1, codigo);
//
//            resultSet = preparedStatement.executeQuery();
//
//            // Paso 6: Obtener el nombre y el valor de las columnas y construir el objeto JSON
//            if (resultSet.next()) {
//                ResultSetMetaData metaData = resultSet.getMetaData();
//                int numColumnas = metaData.getColumnCount();
//
//                json = new JSONObject();
//
//                for (int i = 1; i <= numColumnas; i++) {
//                    String nombreColumna = metaData.getColumnName(i);
//                    Object valorColumna = resultSet.getObject(i);
//                    json.put(nombreColumna, valorColumna);
//                }
//
//            } else {
//                System.out.println("No se encontró ningún registro con el ID proporcionado.");
//            }
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            // Cerrar recursos
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (preparedStatement != null) {
//                    preparedStatement.close();
//                }
//                if (conexion != null) {
//                    conexion.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(json != null){
//            return json.get("idnodealfresco");
//        }else {
//            return json;
//        }
//    }


    //hasta aca
    //hasta aca
    //hasta aca
    //hasta aca


  /*  private void updatePropertiesFile(String nombreCarpeta, EntradaAlfresco entradaAlfresco, Properties properties) throws Exception {
        ResponseBuscarDocumento responseBuscarDocumento = documentoService.buscarDocumento(nombreCarpeta);
        ObjectMapper gson = new ObjectMapper();
        gson.registerModule(new JavaTimeModule());
        entradaAlfresco.setProperties(properties);

        for (Documento documento : responseBuscarDocumento.getDocumentos()) {
            entradaAlfresco.setName(documento.getNombre());
            alfrescoRestClient.updateNode(documento.getId(), gson.writeValueAsString(entradaAlfresco));
        }
    }*/

    @NotNull
    private static Properties getProperties(RadicadoEntradaDTO radicadoEntradaDTO, String nombreCarpeta) {
        Properties properties = new Properties();
     //   properties.setRemitentePais(radicadoEntradaDTO.getRemitentePais());
        properties.setRemitenteDireccion(radicadoEntradaDTO.getRemitenteDireccion());
        properties.setNumeroDocumento(radicadoEntradaDTO.getNumeroDocumentoRemitente());
        properties.setOficinaProductora((int) Double.parseDouble(String.valueOf(radicadoEntradaDTO.getOficinaProductora())));
        properties.setSerie(radicadoEntradaDTO.getSerieRadicacion());
        properties.setRemitenteMunicipio(radicadoEntradaDTO.getRemitenteMunicipio());
        properties.setRemitenteTelefono(radicadoEntradaDTO.getRemitenteTelefono());
        properties.setFechaDocumento(new Date());
        properties.setTipoDocumento(radicadoEntradaDTO.getTipoDocumentoRemitente());
        properties.setAsuntoRadicacion(radicadoEntradaDTO.getAsuntoRadicacion());
       // properties.setTipoComunicacion(radicadoEntradaDTO.getTipoComunicacion());
        properties.setSubserie(radicadoEntradaDTO.getSubserieRadicacion());
        //properties.setHoraDocumento(new Date());
        properties.setIdTramite(radicadoEntradaDTO.getIdTramite());
        properties.setTipoDocumental(radicadoEntradaDTO.getTipoDocumentalRadicacion());
        properties.setDestinatarioNombresApellidos(radicadoEntradaDTO.getDestinatarioNombresApellidos());
        properties.setFechaOriginal(new Date());
        properties.setRemitenteTipoPersona(radicadoEntradaDTO.getRemitenteTipoPersona());
        properties.setNumeroRadicado(nombreCarpeta);
        properties.setNombreRemitente(radicadoEntradaDTO.getNombreRemitente());
        properties.setFondo(radicadoEntradaDTO.getFondoRadicacion());
        properties.setObservacionPpal(radicadoEntradaDTO.getObservacionPpal());
        properties.setMedioRecepcion(radicadoEntradaDTO.getMedioRecepcion());
        properties.setIdentificadorSistema(radicadoEntradaDTO.getIdentificadorSistema());
        properties.setRemitenteDepartamento(radicadoEntradaDTO.getRemitenteDepartamento());
        properties.setRemitenteCorreoElectronico(radicadoEntradaDTO.getRemitenteCorreoElectronico());
        return properties;
    }

    @Override
    public AnexoResponse agregarAnexos(AgregarAnexos agregarAnexos, MultipartFile[] anexos) throws JsonProcessingException {
        int cantidadAnexos = 0;
        int cantidadAnexosAgregados = 0;
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        String jsonArrayString = agregarAnexos.getDescripcionAnexo();
        List<DescripcionAnexoDTO> descripcionAnexoDTOList = objectMapper.readValue(jsonArrayString, new TypeReference<>() {
        });
        if (anexos.length != descripcionAnexoDTOList.size()) {
            throw new AnexosInvalidosException("No coinciden los anexos y su descipción");
        }

        //inicio de validar si existe el nombre de la carpeta en alfresco
        String peticion = "{\n" +
                "  \"query\": {\n" +
                "    \"query\": \"SELECT * FROM cmis:folder WHERE cmis:name='" + agregarAnexos.getIdRadicado() + "'\",\n" +
                "    \"language\": \"cmis\"\n" +
                "  }\n" +
                "}";
        Gson g = new Gson();
        Object peticionJson = g.fromJson(peticion, Object.class);
        AlfrescoResponseDTO metadataRadicados1 = alfrescoClient.buscarNodeIdPorNombreCarpeta(peticionJson);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String fechaCreacion="";
        if (metadataRadicados1.getList().getEntries() != null && !metadataRadicados1.getList().getEntries().isEmpty()) {
            fechaCreacion = metadataRadicados1.getList().getEntries().get(0).getEntry().getModifiedAt();
        }else{
            OffsetDateTime fechaActual = OffsetDateTime.now();
            fechaCreacion = fechaActual.format(formatter);
        }

        LocalDateTime fechaCreacionDateTime = LocalDateTime.parse(fechaCreacion, formatter);

        // Establece la hora a las 23:59
        LocalDateTime fechaLimite = fechaCreacionDateTime.withHour(23).withMinute(59).withSecond(59);

        // Si deseas considerar la zona horaria, puedes hacerlo de la siguiente manera
        fechaLimite = fechaLimite.atOffset(ZoneOffset.UTC).toLocalDateTime();

        // Obtiene la fecha y hora actual
        LocalDateTime fechaActual = LocalDateTime.now();

        // Compara la fecha y hora actual con la fecha límite
   /*     if (fechaActual.isBefore(fechaLimite)) {
            System.out.println("Aún puedes subir anexos. El tiempo no ha expirado.");
        } else {
            System.out.println("El tiempo para subir anexos ha expirado.");
            throw new AnexosInvalidosException("El tiempo para subir anexos ha expirado.");
        }*/

        int cantidadEntrys = metadataRadicados1.getList().getPagination().getCount();
        if (cantidadEntrys == 0) {
            // En lugar de devolver un String, puedes lanzar una excepción o devolver null
            throw new CarpetaNoEncontradaException("No se encontró el id de radicado enviado");
        }

        String idCarpetaAlfresco = metadataRadicados1.getList().getEntries().get(0).getEntry().getId();
        // Validar los anexos enviados
        if (anexos != null) {
            cantidadAnexos = anexos.length;
        } else {
            throw new AnexosInvalidosException("Revisar los anexos enviados, por favor");
        }
        if (anexos == null || anexos.length == 0) {
            // En lugar de devolver un String, puedes lanzar una excepción o devolver null
            throw new AnexosInvalidosException("Revisar los anexos enviados por favor");
        }

        EntradaAlfresco entradaAlfresco = new EntradaAlfresco();

        List<String> archivosCargados = new ArrayList<>();

        for (int i = 0; i < anexos.length; i++) {
            MultipartFile archivoActual = anexos[i];
            DescripcionAnexoDTO descripcionAnexo = descripcionAnexoDTOList.get(i);
            try {

                Integer tipoDocumental = null;

                if (descripcionAnexo.getTipoDocumental() > 0) {
                    try {
                        tipoDocumental = descripcionAnexo.getTipoDocumental();
                    } catch (NumberFormatException e) {
                        log.warn("Tipo documental inválido: " + descripcionAnexo.getTipoDocumental());
                    }
                }

                String nombreArchivoDocumental = generateFileName(archivoActual, null, tipoDocumental);

                entradaAlfresco.setName(nombreArchivoDocumental);
                entradaAlfresco.setNodeType("proltk:comunicacion_oficial");

                Properties properties = new Properties();
                properties.setNumeroRadicado(agregarAnexos.getIdRadicado());
                properties.setNombreOrginalArchivo(archivoActual.getOriginalFilename());

                entradaAlfresco.setProperties(properties);
                MultiValueMap<String, Object> dataNodeType2 = new LinkedMultiValueMap<>();
                dataNodeType2.add("filedata", new HttpEntity<>(archivoActual.getResource()));
                dataNodeType2.add("nodeType", "proltk:comunicacion_oficial");
                dataNodeType2.add("name", nombreArchivoDocumental);
                dataNodeType2.add("proltk:nombreOriginalArchivo", archivoActual.getOriginalFilename());
                dataNodeType2.add("proltk:numero_radicado", agregarAnexos.getIdRadicado());

                AlfrescoNode nombreArchivo = alfrescoRestClient.createNode(idCarpetaAlfresco, dataNodeType2);
//                AlfrescoResponse nombreArchivo = alfrescoRestClient.radicadoEntrada(entradaAlfresco, archivoActual, idCarpetaAlfresco);
                archivosCargados.add(nombreArchivo.getEntry().getName() + ": Creado");
            } catch (Exception e) {
                if (e.getMessage().contains("already exists.")) {
                    AlfrescoResponseDTO nodoExistente = alfrescoClient.obtenerNodoPorNombre(idCarpetaAlfresco, archivoActual.getOriginalFilename());
                    if (nodoExistente != null && nodoExistente.getList().getPagination().getCount() > 0) {
                        deleteFile(nodoExistente.getList().getEntries().get(i).getEntry().getId());
                        AlfrescoResponse nombreArchivo = alfrescoClient.radicadoEntrada(entradaAlfresco, archivoActual, idCarpetaAlfresco);
                        archivosCargados.add(archivoActual.getOriginalFilename() + ": Actualizado");
                    }

                }
            }
            cantidadAnexosAgregados++;
        }

        if (cantidadAnexos == cantidadAnexosAgregados) {
            // Retornar la instancia de AnexoResponse con la información deseada
            return new AnexoResponse(agregarAnexos.getIdRadicado(), idCarpetaAlfresco, archivosCargados);
        } else {
            // En caso de error
            return null; // O podrías lanzar una excepción, dependiendo de tu diseño
        }
    }

    private void deleteFile(String id) {
        String deleteUrl = alfrescoUrl + "/nodes/" + id;
        HttpHeaders deleteHeaders = new HttpHeaders();
        deleteHeaders.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<Void> deleteRequestEntity = new HttpEntity<>(deleteHeaders);

        restTemplate.exchange(deleteUrl, HttpMethod.DELETE, deleteRequestEntity, Void.class);
    }
}