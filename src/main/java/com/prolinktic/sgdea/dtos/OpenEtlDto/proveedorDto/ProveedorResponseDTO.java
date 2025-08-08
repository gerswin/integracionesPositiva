package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

import java.util.List;

@Data
public class ProveedorResponseDTO {
    private int total;
    private int filtrados;
    private List<ProveedorDTO> data;
    private String cantidad_usuarios_portal_proveedores;
}
