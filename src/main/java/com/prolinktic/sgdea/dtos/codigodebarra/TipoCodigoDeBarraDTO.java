package com.prolinktic.sgdea.dtos.codigodebarra;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoCodigoDeBarraDTO {

    private Integer id;
    private String tipoCodigoBarra;
    private String descripcion;
    private boolean estado;

}
