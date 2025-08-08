package com.prolinktic.sgdea.client;

import com.google.gson.Gson;
import com.prolinktic.sgdea.dtos.AlfrescoNode;
import com.prolinktic.sgdea.dtos.AlfrescoNodeList;
import com.prolinktic.sgdea.dtos.Entry;
import com.prolinktic.sgdea.dtos.UploadFile;
import com.prolinktic.sgdea.dtos.radicado.AlfrescoResponseDTO;
import com.prolinktic.sgdea.exception.exceptions.ControlException;
import com.prolinktic.sgdea.infrastructura.gateway.AlfrescoClient;
import com.prolinktic.sgdea.model.Alfesco.CreateNode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.prolinktic.sgdea.util.PQRDUtils.convertDTOToMultiValueMap;

@Component
public class AlfrescoRestClient {

    @Value("${alfresco.url}")
    private String alfrescoUrl;

    @Value("${alfresco.almacenradicado}")
    private String almacenradicados;

    @Value("${alfresco.credentials.gcp}")
    private String credencialesGCP;

    private final AlfrescoClient alfrescoClient;

    private final RestTemplate restTemplate;

    public AlfrescoRestClient(AlfrescoClient alfrescoClient, @Qualifier("appRestClient") RestTemplate restTemplate) {
        this.alfrescoClient = alfrescoClient;
        this.restTemplate = restTemplate;
    }

    public AlfrescoNode createNode(String id, MultiValueMap<String, Object> data) {
        String url = alfrescoUrl + "/nodes/" + id + "/children";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<AlfrescoNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AlfrescoNode.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al cargar el documento en Alfresco. Código de respuesta: " + response.getBody());
        }
    }

    public AlfrescoNode updateNode(String id, String data) {
        String url = alfrescoUrl + "/nodes/" + id;
        HttpHeaders headers = new HttpHeaders();
      //  headers.set("authorization", "Basic YWRtaW46YWRtaW4=");
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<String> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new AlfrescoNode();
        } else {
            throw new RuntimeException("Error al cargar el documento en Alfresco. Código de respuesta: " + response.getBody());
        }
    }

    public AlfrescoNode createNode(String id, Map<String, Object> data) {
        String url = alfrescoUrl + "/nodes/" + id + "/children";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);
        ResponseEntity<AlfrescoNode> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, AlfrescoNode.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al cargar el documento en Alfresco. Código de respuesta: " + response.getBody());
        }
    }

    public AlfrescoNodeList getNodeChildren(String idDocumento) throws ControlException {

        //incicio de obtener nodeId padre del nombre de la carpeta que llega en el parametro idDocumento
        //incicio de obtener nodeId padre del nombre de la carpeta que llega en el parametro idDocumento

        //inicio de validar si existe el nombre de la carpeta en alfresco
        String peticion = "{\n" +
                "  \"query\": {\n" +
                "    \"query\": \"SELECT * FROM cmis:folder WHERE cmis:name='" + idDocumento + "'\",\n" +
                "    \"language\": \"cmis\"\n" +
                "  }\n" +
                "}";
        Gson g = new Gson();
        Object peticionJson = g.fromJson(peticion, Object.class);
        AlfrescoResponseDTO metadataRadicados1 = alfrescoClient.buscarNodeIdPorNombreCarpeta(peticionJson);
        String padreDeCarpeta = metadataRadicados1.getList().getEntries().get(0).getEntry().getParentId();

        //fin de obtener nodeId padre del nombre de la carpeta que llega en el parametro idDocumento
        //fin de obtener nodeId padre del nombre de la carpeta que llega en el parametro idDocumento


        String url = alfrescoUrl + "/nodes/"+padreDeCarpeta+"/children?relativePath=/" + idDocumento;
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<AlfrescoNodeList> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, AlfrescoNodeList.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ControlException(response.getStatusCode(), "Error al cargar el documento en Alfresco. " + response.getBody());
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getMessage().contains("was not found")) {
                throw new ControlException(HttpStatus.BAD_REQUEST, "El id del radicado no es válido o no existe");
            } else {
                throw new ControlException(HttpStatus.INTERNAL_SERVER_ERROR, "Se ha generado un error en el consumo de alfresco. Error: " + ex.getMessage());
            }
        }
    }


    public AlfrescoNodeList getNodeChildren2(String idDocumento, String almacenradicadosFinal) throws ControlException {
        //    String url = alfrescoUrl + "/nodes/" + almacenradicados + "/children?relativePath=/" + idDocumento;
        String url = alfrescoUrl + "/nodes/" + almacenradicadosFinal + "/children?relativePath=/" + idDocumento;
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        try {

            ResponseEntity<AlfrescoNodeList> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, AlfrescoNodeList.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ControlException(response.getStatusCode(), "Error al cargar el documento en Alfresco. " + response.getBody());
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getMessage().contains("was not found")) {
                throw new ControlException(HttpStatus.BAD_REQUEST, "El id del radicado no es válido o no existe");
            } else {
                throw new ControlException(HttpStatus.INTERNAL_SERVER_ERROR, "Se ha generado un error en el consumo de alfresco. Error: " + ex.getMessage());
            }
        }
    }


    public AlfrescoNodeList getLastNodeChildren(int skip, int max, String order) throws ControlException {
        String url = alfrescoUrl + "/nodes/" + almacenradicados + "/children?skipCount=" + skip + "&maxItems=" + max + "&orderBy=" + order;
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        try {

            ResponseEntity<AlfrescoNodeList> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, AlfrescoNodeList.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ControlException(response.getStatusCode(), "Error al cargar el documento en Alfresco. " + response.getBody());
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getMessage().contains("was not found")) {
                throw new ControlException(HttpStatus.BAD_REQUEST, "El id del radicado no es válido o no existe");
            } else {
                throw new ControlException(HttpStatus.INTERNAL_SERVER_ERROR, "Se ha generado un error en el consumo de alfresco. Error: " + ex.getMessage());
            }
        }
    }




    public AlfrescoNodeList obtenerHijosPorNodeId(String nodeId) throws ControlException {
        // String url = "http://ec2-34-193-209-210.compute-1.amazonaws.com:8080/alfresco/api/-default-/public/alfresco/versions/1/nodes/" + nodeId + "/children?skipCount=0&maxItems=10000";

        String url = alfrescoUrl + "/nodes/" + nodeId + "/children?skipCount=0&maxItems=60000";
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        try {

            ResponseEntity<AlfrescoNodeList> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, AlfrescoNodeList.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new ControlException(response.getStatusCode(), "Error en obtenerHijosPorNodeId. " + response.getBody());
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getMessage().contains("was not found")) {
                throw new ControlException(HttpStatus.BAD_REQUEST, "El nodeId no es válido o no existe");
            } else {
                throw new ControlException(HttpStatus.INTERNAL_SERVER_ERROR, "Se ha generado un error en el consumo de alfresco. Error: " + ex.getMessage());
            }
        }
    }

    public List<UploadFile> uploadFiles(String nodeId, MultipartFile[] files) {
        List<UploadFile> ans = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                CreateNode createNode = CreateNode.builder()
                        .name(file.getOriginalFilename())
                        .nodeType("cm:content")
                        .build();

                MultiValueMap<String, Object> dataMultiMap = new LinkedMultiValueMap<>();
                dataMultiMap.add("filedata", new HttpEntity<>(file.getResource()));
                dataMultiMap.addAll(convertDTOToMultiValueMap(createNode));
                Entry node = createNode(nodeId, dataMultiMap).getEntry();
                ans.add(UploadFile.builder()
                        .id(node.getId())
                        .createdAt(node.getCreatedAt())
                        .parentId(node.getParentId())
                        .name(node.getName())
                        .status("CREATED").build());
            }catch (RuntimeException e){
                ans.add(UploadFile.builder()
                        .status("FAIL")
                        .name(file.getOriginalFilename())
                        .message(e.getMessage()).build());
            }

        }
        return ans;
    }
    
}
