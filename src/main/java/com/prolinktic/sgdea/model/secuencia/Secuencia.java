package com.prolinktic.sgdea.model.secuencia;



import com.prolinktic.sgdea.model.parametrizacion_secuencia.ParametrizacionSecuencia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="secuencia")
@Getter
@Setter
public class Secuencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsecuencia")
    private Long idSecuencia;

    private String nombre;

    @Column(name = "nombrecorto")
    private String nombreCorto;

    @ManyToOne
    @JoinColumn(name = "idtiposecuencia")
    private TipoSecuenciaEntity tipoSecuencia;

    private String prefijo;

    @Column(name = "cantidad_digitos_secuencia")
    private Long digitosSecuencia;

    private String sufijo;

    private String comentario;

    @Column(name = "reinicioanual")
    private Boolean reinicioAnual;

    private Boolean activo;

    private String observacion;

    @ManyToOne
    @JoinColumn(name = "idparametrizacionano")
    private ParametrizacionSecuencia parametrizacionAno;

    @ManyToOne
    @JoinColumn(name = "idparametrizaciondependencia")
    private ParametrizacionSecuencia parametrizacionDependencia;

    @ManyToOne
    @JoinColumn(name = "idparametrizacionserie")
    private ParametrizacionSecuencia parametrizacionSerie;

    @ManyToOne
    @JoinColumn(name = "idparametrizacionsubserie")
    private ParametrizacionSecuencia parametrizacionSubserie;

    @ManyToOne
    @JoinColumn(name = "idparametrizacionradicado")
    private ParametrizacionSecuencia parametrizacionRadicado;

    @ManyToOne
    @JoinColumn(name = "idparametrizaciondocumental")
    private ParametrizacionSecuencia parametrizacionDocumental;
}
