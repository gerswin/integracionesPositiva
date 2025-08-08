package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseClasifDocDto {

    private String mensaje;
    private String status;
    private Object documentoExitosos;
    private Object documentoFallidos;

}
