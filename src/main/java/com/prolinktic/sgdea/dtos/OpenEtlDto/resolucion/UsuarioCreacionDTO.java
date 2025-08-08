package com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsuarioCreacionDTO {
    @JsonProperty("usu_id")
    private Integer usuId;

    @JsonProperty("usu_nombre")
    private String usuNombre;

    @JsonProperty("usu_identificacion")
    private String usuIdentificacion;
}