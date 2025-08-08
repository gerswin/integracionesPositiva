package com.prolinktic.sgdea.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(webInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/v2/api-docs/**", "/swagger-resources/**");

    }

}
