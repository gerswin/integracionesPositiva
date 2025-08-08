package com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfiguracionOFEDTO {
    @JsonProperty("ofe_id")
    private Integer ofeId;

    @JsonProperty("ofe_identificacion")
    private String ofeIdentificacion;

    @JsonProperty("ofe_razon_social")
    private String ofeRazonSocial;

    @JsonProperty("ofe_nombre_comercial")
    private String ofeNombreComercial;

    @JsonProperty("ofe_primer_apellido")
    private String ofePrimerApellido;

    @JsonProperty("ofe_segundo_apellido")
    private String ofeSegundoApellido;

    @JsonProperty("ofe_primer_nombre")
    private String ofePrimerNombre;

    @JsonProperty("ofe_otros_nombres")
    private String ofeOtrosNombres;
}