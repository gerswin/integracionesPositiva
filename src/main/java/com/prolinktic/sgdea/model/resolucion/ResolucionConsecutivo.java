package com.prolinktic.sgdea.model.resolucion;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "resolucion_consecutivo")
public class ResolucionConsecutivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consecutivo")
    private Integer idConsecutivo;

    @Column(name = "consecutivo")
    private Long consecutivo;

    @Column(name = "consecutivoini")
    private Long consecutivoIni;

    @Column(name = "consecutivofin")
    private Long consecutivoFin;

    @Column(name = "rfa_prefijo", length = 10)
    private String rfaPrefijo;

    @Column(name = "rfa_resolucion", length = 100)
    private String rfaResolucion;

}
