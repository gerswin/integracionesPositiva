package com.prolinktic.sgdea.dtos.seccionsubseccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class seccionSubseccionDTO {
    private String codigo;
    private Boolean estado;
    private Integer fondo;
    private Integer idSeccionSubSeccion;
    private Integer padre;
    private String nombre;
    private String observacion;
    private String nodeId;

}
