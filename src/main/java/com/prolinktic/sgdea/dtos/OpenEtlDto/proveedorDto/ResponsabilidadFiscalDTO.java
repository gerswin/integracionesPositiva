package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

@Data
public class ResponsabilidadFiscalDTO {
    private int ref_id;
    private String ref_codigo;
    private String ref_descripcion;
    private String fecha_vigencia_desde;
    private String fecha_vigencia_hasta;
    private String ref_codigo_descripion;
    private String estado;
}
