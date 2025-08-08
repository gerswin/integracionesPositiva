package com.prolinktic.sgdea.client;

import com.prolinktic.sgdea.dao.Secuancia.SecuenciaJpaSpring;
import com.prolinktic.sgdea.dao.valorSecuencia.ValorSecuenciaJpaRepository;
import com.prolinktic.sgdea.model.secuencia.Secuencia;
import com.prolinktic.sgdea.model.secuencia.ValorSecuencia;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@Component
public class SGDEARestClient {


    @Value("${sgdea.url}")
    private String sgdeaUrl;

    @Autowired
    private ValorSecuenciaJpaRepository valorSecuenciaJpaRepository;

    @Autowired
    private SecuenciaJpaSpring secuenciaJpaSpring;

    private final RestTemplate restTemplate = new RestTemplate();

    public Boolean verificarDependenciasTRD(Map<String, Object> data) {
        String url = sgdeaUrl + "trd/verificar";
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Boolean.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Error al invocar servicio SGDEA Alfresco. Código de respuesta: " + response.getBody());
        }
    }


//    public String generarSecuencia(int tipo){
//        String prefijo ="ENT";
//        switch (tipo) {
//            case 2: prefijo = "SAL";
//            break;
//            case 3: prefijo = "INT";
//            break;
//        };
//        String url = sgdeaUrl + "generar/secuencia/"+prefijo;
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept", "*/*");
//        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
//        });
//        if (response.getStatusCode().is2xxSuccessful()) {
//            return response.getBody();
//        } else {
//            throw new RuntimeException("Error al invocar servicio SGDEA Alfresco. Código de respuesta: " + response.getBody());
//        }
//    }

    public String generarSecuencia(int tipo) {

        String prefijo = "ENT";
        switch (tipo) {
            case 2:
                prefijo = "SAL";
                break;
            case 3:
                prefijo = "INT";
                break;
        }
        ;

        Secuencia secuenciaConf = secuenciaJpaSpring.findByNombreCorto(prefijo);

        if (secuenciaConf == null)
            throw new NullPointerException("No existe configuracion para este tipo de secuencia");

        final String nombre = new StringBuilder()
                .append(secuenciaConf.getPrefijo())
                .append(LocalDate.now().getYear())
                .toString();

        ValorSecuencia valorSecuencia = valorSecuenciaJpaRepository.save(ValorSecuencia.builder()
                .tipoSecuencia(secuenciaConf.getTipoSecuencia())
                .valor(nombre)
                .build());
        ;
        return new StringBuilder()
                .append(nombre)
                .append(StringUtils.leftPad("" + valorSecuencia.getId(), secuenciaConf.getDigitosSecuencia().intValue(), "0"))
                .toString();
    }
}
