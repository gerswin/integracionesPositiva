package com.prolinktic.sgdea.model.versionestrd;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "versiontrd")
public class VersionesTRD {

    @Id
    @Column(name="idversiontrd")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombreversion",  length = 50)
    private String nombreVersion;

    @Column(name = "fechaversion")
    private Date fechaVersion;
 //comentado temporalmente   private LocalDate fechaVersion;

    @Column(name = "fechainicio")
    private Date fechaInicio;
    //comentado temporalmente    private LocalDate fechaInicio;

    @Column(name = "fechafin")
    private Date fechaFin;
    //comentado temporalmente  private LocalDate fechaFin;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "estado", columnDefinition = "boolean default true")
    private Integer estado;

    @Column(name = "fondoid")
    private Integer id_fondo;
}
