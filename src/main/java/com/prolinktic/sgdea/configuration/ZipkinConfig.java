package com.prolinktic.sgdea.configuration;

import brave.baggage.BaggageField;
import brave.sampler.Sampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZipkinConfig {

    @Bean
    public Sampler defaultSampler() {
        return new Sampler() {
            @Override
            public boolean isSampled(long traceId) {
                return true;
            }
        };
    }

    @Bean
    public BaggageField serviceNameField() {
        return BaggageField.create("serviceName");
    }
}
