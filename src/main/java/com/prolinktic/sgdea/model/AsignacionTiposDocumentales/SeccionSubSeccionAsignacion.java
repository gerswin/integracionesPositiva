package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "SeccionSubSeccion")
@Data
public class SeccionSubSeccionAsignacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idseccionsubseccion")
    private Integer idSeccionSubSeccion;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idpadre")
    private SeccionSubSeccionAsignacion padre;



    // Constructor, getters, and setters
}