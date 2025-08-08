package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "AsociacionTipoDocumental")
@Data
public class AsociacionTipoDocumental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_AsociacionTipoDocumental")
    private Integer idAsociacionTipoDocumental;

    @ManyToOne
    @JoinColumn(name = "id_ccd")
    private CCDAsignacion ccd;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc")
    private TipoDocumentoAsignacion tipoDocumentoAsignacion;

    // Constructor, getters, and setters
}