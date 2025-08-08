package com.prolinktic.sgdea.dtos.OpenEtlDto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoDocumentoRequestDto {
    @JsonProperty("evento")
    private String evento;

    @JsonProperty("documentos")
    private List<DocumentoRequestDto> documentos;
}


