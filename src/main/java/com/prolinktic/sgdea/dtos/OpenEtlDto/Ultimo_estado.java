package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ultimo_estado {
    private String estado;
    private String resultado;
    private String mensaje_resultado;
    private String archivo;
    private String xml;
    private String fecha;
}
