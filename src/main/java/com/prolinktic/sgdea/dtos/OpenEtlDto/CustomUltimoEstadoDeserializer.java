package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

class CustomUltimoEstadoDeserializer extends JsonDeserializer<Ultimo_estado> {
    @Override
    public Ultimo_estado deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        if (value == null || value.isEmpty()) {
            return null;
        }
        return p.getCodec().readValue(p, Ultimo_estado.class);
    }
}