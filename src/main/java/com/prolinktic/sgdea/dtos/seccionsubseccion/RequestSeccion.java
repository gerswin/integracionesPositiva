package com.prolinktic.sgdea.dtos.seccionsubseccion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestSeccion {
    private String codigoDependencia;
    private String nombreDependencia;
    private Boolean estado;
}
