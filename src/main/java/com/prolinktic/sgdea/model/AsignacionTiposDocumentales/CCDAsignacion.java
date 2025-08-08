package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "CCD")
@Data
public class CCDAsignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ccd")
    private Integer idCcd;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc")
    private TipoDocumentoAsignacion tipoDocumentoAsignacion;

    @Column(name = "numero_ccd")
    private Integer numeroCcd;
    @Column(name = "tipo_de_soporte")
    private Integer tipo_soporte;
    @Column(name = "nombre_ccd")
    private String nombreCcd;

    @ManyToOne
    @JoinColumn(name = "id_serieSubserie")
    private SerieAsignacion serieAsignacionSubserie;

    @ManyToOne
    @JoinColumn(name = "id_dependencia")
    private SeccionSubSeccionAsignacion dependencia;
}