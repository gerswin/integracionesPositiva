package com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResponseCreateDocOpenDTO {
    private String message;
    private String lote;
    private List<DocumentoProcesadoOpen> documentosProcesados;
    private List<DocumentoFallidoOpen> documentosFallidos;

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentoProcesadoOpen {
        private long cdoId;
        private String rfaPrefijo;
        private String cdoConsecutivo;

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate fechaProcesamiento;

        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime horaProcesamiento;
    }

    @Getter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentoFallidoOpen {
        private String documento;
        private String consecutivo;
        private String prefijo;
        private JsonNode errors; // puede ser arreglo u objeto

        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate fechaProcesamiento;

        @JsonFormat(pattern = "HH:mm:ss")
        private LocalTime horaProcesamiento;

        private static final ObjectMapper objectMapper = new ObjectMapper();

        public List<String> getErrorsAsList() {
            if (errors.isArray())
                return objectMapper.convertValue(errors, new TypeReference<>(){});

            if (!errors.isObject())
                return Collections.emptyList();

            Map<String, Object> errores = objectMapper.convertValue(errors, new TypeReference<>(){});

            if (!errores.containsKey("message"))
                return Collections.emptyList();

            if (errores.get("message") instanceof String) {
                String mensaje = (String) errores.get("message");
                return List.of(mensaje);
            }


            return Collections.emptyList();
        }
    }

}
