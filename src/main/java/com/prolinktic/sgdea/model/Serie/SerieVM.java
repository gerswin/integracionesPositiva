package com.prolinktic.sgdea.model.Serie;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SerieVM {
    private int id_serieSubserie;
    private Integer id_padre;
    private String codigo_serie;
    private String descripcion;
    private Date fecha_vigencia_inicial;
    private Date fecha_vigencia_final;
    private String procedimiento;
    private String observacion;

    // Getters and Setters
}