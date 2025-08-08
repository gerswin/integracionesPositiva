package com.prolinktic.sgdea.model.Serie.DTOS;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Request {
    private String codigoSerie;
    private String codigoSubserie;
    private LocalDate fechaInicial;
    private LocalDate fechaFinal;
    private String descripcion;
    private Integer tiempoArchivoGestion;
    private Integer tiempoArchivoCentral;
    private Integer soporte;
    private String disposicionFinal;
    private Boolean estado;
    private String procedimiento;
    private Integer codigoDependencia;
    private String dependencia;

}
