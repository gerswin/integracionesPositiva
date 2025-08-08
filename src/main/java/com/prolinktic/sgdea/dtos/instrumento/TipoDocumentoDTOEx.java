package com.prolinktic.sgdea.dtos.instrumento;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TipoDocumentoDTOEx {
    private Integer idTipoDoc;
    private String nombre;

    public TipoDocumentoDTOEx(Integer idTipoDoc, String nombre) {
        this.idTipoDoc = idTipoDoc;
        this.nombre = nombre;
    }
}

