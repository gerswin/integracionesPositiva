package com.prolinktic.sgdea.dtos.serie;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SerieFlt {
    private Integer id_seriesubserie;
    private String descripcion;
    private Boolean estados;
    private Integer tipo_de_soporte;
    private Integer padre;
    private LocalDate fecha_vigencia_inicial;
    private LocalDate fecha_vigencia_final;
    private String observacion;
    private String codigo;
    private Integer dependencia;
    private Integer idccd;

}
