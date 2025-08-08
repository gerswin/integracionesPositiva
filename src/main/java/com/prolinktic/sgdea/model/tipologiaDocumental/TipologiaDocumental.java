package com.prolinktic.sgdea.model.tipologiaDocumental;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tipologiadocumental")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TipologiaDocumental implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipologiadocumental") // Nombre de la columna en la base de datos
    private Long idTipologiaDocumental;

    @Column(name = "estado")
    private Boolean estado;

    private String nombre;
    private String descripcion;

    @Column(name = "codigotipologiadocumental")
    private String codigoTipologiaDocumental;

    @Column(name = "nombrecorto")
    private String nombreCorto;

    @Column(name = "siglaindice")
    private String siglaIndice;

    @Column(name = "descripcionindice")
    private String descripcionIndice;

    @Column(name = "esundocumentoindexable")
    private Boolean esUnDocumentoIndexable;

    @Column(name = "esundocumentogestion")
    private Boolean esUnDocumentoGestion;

    @Column(name = "esundocumentoplantilla")
    private Boolean esUnDocumentoPlantilla;

    @Column(name = "labelindice")
    private Boolean labelIndice;

    @Column(name = "bloqueoindice")
    private Boolean bloqueoIndice;

    @Column(name = "exigidoindice")
    private Boolean exigidoIndice;

    @Column(name = "unicoindice")
    private Boolean unicoIndice;

    @Column(name = "visibleindice")
    private Boolean visibleIndice;

    @Column(name = "indexableindice")
    private Boolean indexableIndice;

    @Column(name = "editableindice")
    private Boolean editableIndice;

    @Column(name = "esundocumentoplantillatipoformulario")
    private Boolean esUnDocumentoPlantillaTipoFormulario;
}
