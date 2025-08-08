package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Serie")
@Data
public class SerieAsignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_serieSubserie")
    private Integer idSerieSubserie;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estados")
    private Boolean estados;

    @Column(name = "tipo_de_soporte")
    private Integer tipoDeSoporte;

    @ManyToOne
    @JoinColumn(name = "idpadre")
    private SerieAsignacion padre;


    @Column(name = "fecha_vigencia_inicial")
    private Date fechaVigenciaInicial;

    @Column(name = "fecha_vigencia_final")
    private Date fechaVigenciaFinal;

    @Column(name = "observacion")
    private String observacion;
    @Column(name ="codigo")
    private String codigo;
    // Constructor, getters, and setters
}