package com.prolinktic.sgdea.service.dependencias;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prolinktic.sgdea.dao.Fondo.FondoJpaSpring;
import com.prolinktic.sgdea.dao.SeccionSubSeccion.SeccionSubSeccionJpaSpring;
import com.prolinktic.sgdea.dtos.consultarArchivo.ResponseConsultarArchivo;
import com.prolinktic.sgdea.dtos.dependencias.DataDependencias;
import com.prolinktic.sgdea.dtos.dependencias.ResponseDependencias;
import com.prolinktic.sgdea.infrastructura.gateway.AlfrescoClient;
import com.prolinktic.sgdea.model.Fondo.Fondo;
import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DependenciasServiceImpl implements DependenciasService{


    private final SeccionSubSeccionJpaSpring seccionSubSeccion;
    private final FondoJpaSpring fondoJpaSpring;

//    @Autowired
    private final RestTemplate restTemplate;

    @Value("${sgdea.url}")
    private String sgdeaUrl;

    public DependenciasServiceImpl(SeccionSubSeccionJpaSpring seccionSubSeccion, FondoJpaSpring fondoJpaSpring, @Qualifier("appRestClient") RestTemplate restTemplate) {
        this.seccionSubSeccion = seccionSubSeccion;
        this.fondoJpaSpring = fondoJpaSpring;
        this.restTemplate = restTemplate;
    }

//    @Override
//    public ResponseDependencias consultarDocumento() {
//        UUID correlationId = UUID.randomUUID();
//
//        try {
//            ResponseEntity<List<SeccionSubSeccion>> response = restTemplate.exchange(
//                    sgdeaUrl + "seccionSubSeccion",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<>() {
//                    }
//            );
//
//
//
//            ResponseEntity<List<Fondo>> responseFondo = restTemplate.exchange(
//                    sgdeaUrl + "fondo",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<>() {
//                    }
//            );
//
//            List<Fondo> fondos = responseFondo.getBody();
//
//            List<DataDependencias> data = new ArrayList<>();
//
//         List<SeccionSubSeccion> seccionSubSecciones = response.getBody()
//                    .stream()
//                    .filter(SeccionSubSeccion::getEstado)
//                    .collect(Collectors.toList());
//
//    //        List<SeccionSubSeccion> seccionSubSecciones = null;
//
//            for (SeccionSubSeccion element : seccionSubSecciones) {
//
//                Fondo fondoConId = null;
//
//                for (Fondo fondo : fondos) {
//                    if (fondo.getIdFondo() == element.getIdversiontrd().getId_fondo()) {
//                        fondoConId = fondo;
//                        break; // Termina el bucle cuando se encuentra el primer fondo con id 1.
//                    }
//                }
//
//                DataDependencias dataDependencias = new DataDependencias();
//                dataDependencias.setCodigoFondo(fondoConId.getIdFondo().toString());
//                dataDependencias.setFondo(fondoConId.getNombre());
//                dataDependencias.setCodigoOficinaProductora(element.getCodigo());
//                dataDependencias.setNombreOficinaProductora(element.getNombre());
//                dataDependencias.setNodeId(element.getIdAlfresco());//pendiente probar
//                data.add(dataDependencias);
//            }
//
//            HttpHeaders headers = response.getHeaders();
//            String dateHeaderValue = headers.getFirst("Date");
//
//            return new ResponseDependencias(
//                    "Exito", dateHeaderValue,
//                    correlationId.toString(), 200, data);
//
//        } catch (Error ex) {
//            return new ResponseDependencias(
//                    "Error", null,
//                    correlationId.toString(), 500, null);
//        }
//    }


    @Override
    public ResponseDependencias consultarDocumento() {
        UUID correlationId = UUID.randomUUID();

        try {

            List<SeccionSubSeccion> response = seccionSubSeccion.findAll();
            List<Fondo> responseFondo = fondoJpaSpring.findAll();

            List<DataDependencias> data = new ArrayList<>();

            List<SeccionSubSeccion> seccionSubSecciones = response
                    .stream()
                    .filter(SeccionSubSeccion::getEstado)
                    .collect(Collectors.toList());

            for (SeccionSubSeccion element : seccionSubSecciones) {
                Fondo fondoConId = responseFondo.stream()
                        .filter(fondo -> fondo.getIdFondo() == element.getIdversiontrd().getId_fondo())
                        .findFirst()
                        .orElse(null);

                if (fondoConId != null) {
                    DataDependencias dataDependencias = new DataDependencias();
                    dataDependencias.setCodigoFondo(fondoConId.getIdFondo().toString());
                    dataDependencias.setFondo(fondoConId.getNombre());
                    dataDependencias.setCodigoOficinaProductora(element.getCodigo());
                    dataDependencias.setNombreOficinaProductora(element.getNombre());
                    dataDependencias.setNodeId(element.getIdNodeAlfresco());
                    data.add(dataDependencias);
                }
            }

            SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            httpDateFormat.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
            String currentDateTime = httpDateFormat.format(new Date());

            return new ResponseDependencias(
                    "Exito", currentDateTime,
                    correlationId.toString(), 200, data);

        } catch (Exception ex) {
            return new ResponseDependencias(
                    "Error", null,
                    correlationId.toString(), 500, null);
        }
    }

}
