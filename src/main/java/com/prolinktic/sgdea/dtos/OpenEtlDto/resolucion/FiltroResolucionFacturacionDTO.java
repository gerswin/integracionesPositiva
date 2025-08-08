package com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion;

import lombok.Data;

@Data
public class FiltroResolucionFacturacionDTO {
    private Integer start = 0;
    private Integer length = 10;
    private String buscar = "";
    private String columnaOrden = "codigo";
    private String ordenDireccion = "asc";

    public FiltroResolucionFacturacionDTO() {}

    public FiltroResolucionFacturacionDTO(Integer start, Integer length, String buscar,
                                          String columnaOrden, String ordenDireccion) {
        this.start = start;
        this.length = length;
        this.buscar = buscar;
        this.columnaOrden = columnaOrden;
        this.ordenDireccion = ordenDireccion;
    }
}