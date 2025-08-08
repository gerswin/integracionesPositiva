package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "TipoDocumento")
@Data
public class TipoDocumentoAsignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_doc")
    private Integer idTipoDoc;

    @Column(name = "termino_del_tramite")
    private Integer terminoDelTramite;

    @Column(name = "tipo_de_radicado")
    private Integer tipoDeRadicado;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;



    // Constructor, getters, and setters
}