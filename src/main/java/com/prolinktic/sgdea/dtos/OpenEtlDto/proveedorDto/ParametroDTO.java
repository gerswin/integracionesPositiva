package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

@Data
public class ParametroDTO {
    private int tdo_id; // o dep_id, mun_id, etc., dependiendo del caso
    private String tdo_codigo; // o dep_codigo, etc.
    private String tdo_descripcion; // o dep_descripcion, etc.
}
