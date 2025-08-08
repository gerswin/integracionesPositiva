package com.prolinktic.sgdea.dtos.radicado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaRadicadoEntrada {
    String nombreDocumento;
    String idDocumento;
    String observacion;
    int tipoDocumental;
}
