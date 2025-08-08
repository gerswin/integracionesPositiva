package com.prolinktic.sgdea.model.Fondo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prolinktic.sgdea.model.Empresa.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="fondo")
@Data
@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class Fondo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfondo")
    private Integer idFondo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "observacion")
    private String observacion;

    @Column(name = "nitfondo")
    private Integer nitFondo;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "codigo")
    private Integer codigo;


    //@Column(name = "idempresa")
    //private Integer idempresa;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;
}
