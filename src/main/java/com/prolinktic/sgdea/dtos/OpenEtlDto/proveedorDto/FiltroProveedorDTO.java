package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

@Data
public class FiltroProveedorDTO {
    private int start;
    private int length;
    private String buscar;
    private String columnaOrden;
    private String ordenDireccion;
}
