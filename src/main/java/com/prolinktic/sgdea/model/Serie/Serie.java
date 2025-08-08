package com.prolinktic.sgdea.model.Serie;


import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "serie")
public class Serie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_seriesubserie")
    private Integer idSeriesubserie;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estados")
    private boolean estados;

    @Column(name = "tipo_de_soporte")
    private Integer tipo_de_soporte;

    @ManyToOne
    @JoinColumn(name = "idpadre")
    private Serie padre;

    @Column(name = "fecha_vigencia_inicial")
    private LocalDate fecha_vigencia_inicial;

    @Column(name = "fecha_vigencia_final")
    private LocalDate fecha_vigencia_final;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "id_alfresco")
    private String idAlfresco;

    @Column(name="idccd")
    private Integer idccd;

    @ManyToOne
    @JoinColumn(name = "idseccionsubseccion")
    private SeccionSubSeccion seccionSubSeccion;

}
