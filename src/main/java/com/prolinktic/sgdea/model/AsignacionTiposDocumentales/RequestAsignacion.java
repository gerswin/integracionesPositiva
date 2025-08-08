package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestAsignacion {
    private Integer dependenciaId;
    private Integer TipoDocumentalId;
}
