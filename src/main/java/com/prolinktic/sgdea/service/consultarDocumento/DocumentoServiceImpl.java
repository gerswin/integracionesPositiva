package com.prolinktic.sgdea.service.consultarDocumento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.client.AlfrescoRestClient;
import com.prolinktic.sgdea.dtos.AlfrescoNode;
import com.prolinktic.sgdea.dtos.AlfrescoNodeList;
import com.prolinktic.sgdea.dtos.buscarDocumento.Documento;
import com.prolinktic.sgdea.dtos.buscarDocumento.ResponseBuscarDocumento;
import com.prolinktic.sgdea.dtos.consultarArchivo.ResponseProperties;
import com.prolinktic.sgdea.exception.exceptions.ControlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Value("${alfresco.url}")
    private String alfrescoUrl;

    @Value("${alfresco.credentials.gcp}")
    private String credencialesGCP;

    private final AlfrescoRestClient alfrescoRestClient;

    private final RestTemplate restTemplate = new RestTemplate();

    public DocumentoServiceImpl(AlfrescoRestClient alfrescoRestClient) {
        this.alfrescoRestClient = alfrescoRestClient;
    }

    @Override
    public ResponseBuscarDocumento buscarDocumento(String idDocumento) throws ControlException {
        AlfrescoNodeList nodeChildren = alfrescoRestClient.getNodeChildren(idDocumento);
        List<AlfrescoNode> entries = nodeChildren.getList().getEntries();

        List<Documento> documentos = new ArrayList<>();
        for (AlfrescoNode node : entries) {
            String id = node.getEntry().getId();
            AlfrescoNode archivo = getNode(id);

            Map<String, Object> properties = archivo.getEntry().getProperties();

            Documento documento = new Documento();
            documento.setId(archivo.getEntry().getId());
            documento.setNombre(archivo.getEntry().getName());
            if (documento.getNombre().startsWith("pp-")) {
                documento.setDescripcion("Documento principal");
            } else {
                documento.setDescripcion("Documento secundario");
            }

            ObjectMapper modelMapper = new ObjectMapper();
            ResponseProperties map = modelMapper.convertValue(properties, ResponseProperties.class);
            documento.setPropiedades(map);

            documentos.add(documento);
        }

        ResponseBuscarDocumento response = new ResponseBuscarDocumento();
        response.setIdRadicado(idDocumento);
        response.setEstado(200);
        response.setDocumentos(documentos);
        response.setMensaje("OK");

        return response;
    }

    @Override
    public ResponseBuscarDocumento buscarDocumento2(String idDocumento, String idDoucmentoFinal, Map<String, Object> newMetadata) throws ControlException {
        AlfrescoNodeList nodeChildren = alfrescoRestClient.getNodeChildren2(idDocumento, idDoucmentoFinal);
        List<AlfrescoNode> entries = nodeChildren.getList().getEntries();

        List<Documento> documentos = new ArrayList<>();
        for (AlfrescoNode node : entries) {
            String id = node.getEntry().getId();
            AlfrescoNode archivo = getNode(id);

            Map<String, Object> properties = archivo.getEntry().getProperties();

            Documento documento = new Documento();
            documento.setId(archivo.getEntry().getId());
            documento.setNombre(archivo.getEntry().getName());
            if (documento.getNombre().startsWith("pp-")) {
                documento.setDescripcion("Documento principal");
            } else {
                documento.setDescripcion("Documento secundario");
            }

            ObjectMapper modelMapper = new ObjectMapper();
            ResponseProperties map = modelMapper.convertValue(properties, ResponseProperties.class);
            documento.setPropiedades(map);

            documentos.add(documento);
            //inicio de setear propiedades de archivos
            updateMetadata(archivo.getEntry().getId(),newMetadata);
            //final de setear propiedades de archivos
        }

        ResponseBuscarDocumento response = new ResponseBuscarDocumento();
        response.setIdRadicado(idDocumento);
        response.setEstado(200);
        response.setDocumentos(documentos);
        response.setMensaje("OK");

        return response;
    }

    @Override
    public void updateMetadata(String nodeId, Map<String, Object> newMetadata) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //Adicionamos el header para realizar la authenticacion
        headers.set("authorization", "Basic "+credencialesGCP);

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

    public AlfrescoNode getNode(String idDocumento) {
        String url = alfrescoUrl + "/nodes/" + idDocumento;
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Basic "+credencialesGCP);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<AlfrescoNode> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, AlfrescoNode.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al cargar el documento en Alfresco. Código de respuesta: " + response.getBody());
        }
    }


    @Override
    public AlfrescoNodeList obtenerHijosPorNodeId(String nodeId) throws ControlException {
        return alfrescoRestClient.obtenerHijosPorNodeId(nodeId);
    }
}
