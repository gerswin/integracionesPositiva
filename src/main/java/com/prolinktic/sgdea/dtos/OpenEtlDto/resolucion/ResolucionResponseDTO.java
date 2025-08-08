package com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion;

import lombok.Data;

import java.util.List;

@Data
public class ResolucionResponseDTO {
    private Integer total;
    private Integer filtrados;
    private List<ResolucionDTO> data;
}