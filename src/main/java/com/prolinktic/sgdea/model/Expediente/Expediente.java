package com.prolinktic.sgdea.model.Expediente;


import com.prolinktic.sgdea.model.Serie.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "expediente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idexpediente")
    private Integer idexpediente;

    @Column(name = "idseriesubserie")
    private Long idseriesubserie;

    @Column(name = "idradicado")
    private Long idradicado;

    @Column(name = "codigoexpediente")
    private String codigoexpediente;

    @Column(name = "nombreexpediente")
    private String nombreexpediente;

    @Column(name = "fechavigenciainicial")
    private LocalDate fechavigenciainicial;

    @Column(name = "fechavigenciafinal")
    private LocalDate fechavigenciafinal;

    @Column(name = "tipoexpediente")
    private Long tipoexpediente;

    @Column(name = "idccd")
    private Long idccd;

    @Column(name = "area")
    private String area;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "nivelseguridad")
    private Long nivelseguridad;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "nombredocumento")
    private String nombredocumento;

    @Column(name = "fechadocumento")
    private LocalDate fechadocumento;

    @Column(name = "numeroradicado")
    private String numeroradicado;

    @Column(name = "tieneanexos")
    private Boolean tieneanexos;

    @Column(name = "tipodocumental")
    private Long tipodocumental;

    @Column(name = "nodeid")
    private String nodeid;

    @Column(name = "estado")
    private int estado;

    /*@ManyToOne
    @JoinColumn(name = "idseriesubserie", referencedColumnName = "id_seriesubserie", insertable = false, updatable = false)
    private Serie serieSubSerie;*/

}
