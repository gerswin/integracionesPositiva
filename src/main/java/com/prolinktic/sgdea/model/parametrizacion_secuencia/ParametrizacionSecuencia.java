package com.prolinktic.sgdea.model.parametrizacion_secuencia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="parametrizacionsecuencia")
@Getter
@Setter
public class ParametrizacionSecuencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idparametrizacionsecuencia")
    private Long idParametrizacionSecuencia;

    @Column(name = "antesprefijo")
    private Boolean antesPrefijo;

    @Column(name = "despuesprefijo")
    private Boolean despuesPrefijo;

    @Column(name = "antessufijo")
    private Boolean antesSufijo;

    @Column(name = "despuessufijo")
    private Boolean despuesSufijo;
}
