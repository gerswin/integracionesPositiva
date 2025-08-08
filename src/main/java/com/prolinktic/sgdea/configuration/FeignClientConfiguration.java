package com.prolinktic.sgdea.configuration;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Value("${alfresco.auth.usuario}")
    private String usuario;
    @Value("${alfresco.auth.contrasena}")
    private String contresena;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(){
        return new BasicAuthRequestInterceptor(usuario, contresena);
    }

}
