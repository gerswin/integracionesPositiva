package com.prolinktic.sgdea.model.tipoDocumental;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoDocumentalVM {
    private int id_tipo_documental;
    private int termino_tramite;
    private int tipo_de_radicado;
    private String descripcion;
}