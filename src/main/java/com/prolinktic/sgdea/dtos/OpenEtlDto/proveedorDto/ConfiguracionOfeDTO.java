package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

@Data
public class ConfiguracionOfeDTO {
    private int ofe_id;
    private int sft_id;
    private int tdo_id;
    private int toj_id;
    private String ofe_identificacion;
    private String ofe_razon_social;
    private String ofe_nombre_comercial;
    private String ofe_primer_apellido;
    private String ofe_segundo_apellido;
    private String ofe_primer_nombre;
    private String ofe_otros_nombres;
}
