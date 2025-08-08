package com.prolinktic.sgdea.dtos.radicado;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RadicadoRequest {
    private Integer idRadicado;
    private Integer tipoDocumento;
    private Integer numeroDocumento;
    private String fechaRadicado;
    private Integer tipoRadicado;
}
