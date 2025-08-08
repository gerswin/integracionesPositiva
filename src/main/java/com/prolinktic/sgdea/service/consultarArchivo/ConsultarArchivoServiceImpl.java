package com.prolinktic.sgdea.service.consultarArchivo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.dtos.consultarArchivo.ResponseConsultarArchivo;
import com.prolinktic.sgdea.infrastructura.gateway.AlfrescoClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Base64;

@Service
public class ConsultarArchivoServiceImpl implements ConsultarArchivoService {

    @Autowired
    AlfrescoClient alfrescoClient;

    @Override
    public Object consultarDocumento(String id_documento) {
        UUID correlationId = UUID.randomUUID();

        try {
            var responseArchivo = alfrescoClient.obtenerContenidoDeNodo(id_documento);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonObject = objectMapper.readTree(responseArchivo);
            String createdAt = jsonObject.at("/entry/createdAt").asText();
            String documentoBase64 = consultaArchivoContentById(id_documento);

            ResponseConsultarArchivo response = new ResponseConsultarArchivo(
                    id_documento, "Exito", createdAt,
                    correlationId.toString(), 200, documentoBase64);

            return response;
        } catch (FeignException.NotFound ex) {
            // FeignException.NotFound es una subclase específica de FeignException para errores 404
            return new ResponseConsultarArchivo(
                    id_documento, "Documento no Existe", null,
                    correlationId.toString(), 404, null);
        } catch (FeignException e) {
            // Manejar otras excepciones Feign aquí
            return new ResponseConsultarArchivo(
                    id_documento, "Error en la solicitud a Alfresco", null,
                    correlationId.toString(), e.status(), null);
        } catch (Exception e) {
            // Manejar otras excepciones generales aquí
            return new ResponseConsultarArchivo(
                    id_documento, "Error interno del servidor", null,
                    correlationId.toString(), 500, null);
        }
    }

    @Override
    public String consultaArchivoContentById(String id_documento) {
        var responseDocumento = alfrescoClient.consultarDocumentoByte(id_documento);

        if (responseDocumento != null) {
            return new String(Base64.getEncoder().encodeToString(responseDocumento));
        } else {
            return null;
        }
    }
}
