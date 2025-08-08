package com.prolinktic.sgdea.dtos.OpenEtlDto.proveedorDto;

import lombok.Data;

import java.util.List;

@Data
public class CrearProveedorResponseDTO {
    private Boolean success;
    private Integer pro_id;
    private String message;
    private List<String> errors;
}
