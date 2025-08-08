package com.prolinktic.sgdea.model.SeccionSubSeccion;
import com.prolinktic.sgdea.model.Fondo.Fondo;
import com.prolinktic.sgdea.model.versionestrd.VersionesTRD;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "seccionsubSeccion")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeccionSubSeccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idseccionsubseccion")
    private int idSeccionSubSeccion;

    @Column(name="nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idpadre")
    private SeccionSubSeccion padre;

    @Transient
    private Fondo fondo;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "observaciones")
    private String observacion;

    @Column(name = "idalfresco")
    private String idAlfresco;

    @Column(name = "idnodealfresco")
    private String idNodeAlfresco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idversiontrd")
    private VersionesTRD idversiontrd;

}

