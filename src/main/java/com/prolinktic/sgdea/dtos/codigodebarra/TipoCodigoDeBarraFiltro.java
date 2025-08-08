package com.prolinktic.sgdea.dtos.codigodebarra;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipoCodigoDeBarraFiltro {

    private String codigodeBarra;
    private Boolean estado;
    private int pagina = 0;
    private int cantidad = 30;

}
