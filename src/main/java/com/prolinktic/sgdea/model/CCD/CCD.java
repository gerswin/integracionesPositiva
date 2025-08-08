package com.prolinktic.sgdea.model.CCD;

import com.prolinktic.sgdea.model.SeccionSubSeccion.SeccionSubSeccion;
import com.prolinktic.sgdea.model.Serie.Serie;
import com.prolinktic.sgdea.model.TiempoRetencion.TiempoRetencion;
import com.prolinktic.sgdea.model.TipoDocumento.TipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ccd")
@AllArgsConstructor // para que funcione las validaciones
@NoArgsConstructor // para que no de error al obtener el producto con el getProduct(Integer id)
@Data //getter y setter
public class CCD {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_ccd")
    private Integer idCcd;

    @ManyToOne
    @JoinColumn(name = "id_serieSubserie")
    private Serie idSerieSubserie;

    @Column(name = "numero_ccd")
    private Integer numeroCcd;

    @Column(name = "nombre_ccd", length = 255)
    private String nombreCcd;

    @Column(name = "procedimiento", length = 255)
    private String procedimiento;

    @Column(name = "id_version")
    private Integer idVersion;

    @ManyToOne
    @JoinColumn(name = "id_dependencia")
    private SeccionSubSeccion idDependencia;

    @ManyToOne
    @JoinColumn(name = "id_tiempo_retencion")
    private TiempoRetencion idTiempoRetencion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc", referencedColumnName = "id_tipo_doc", insertable = false, updatable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "CT", nullable = true)
    private Boolean ct;

    @Column(name = "E", nullable = true)
    private Boolean e;

    @Column(name = "MD", nullable = true)
    private Boolean md;

    @Column(name = "S", nullable = true)
    private Boolean s;

    @Column(name = "tiempo_archivo_gestion", length = 255)
    private String tiempo_archivo_gestion;

    @Column(name = "tiempo_archivo_central", length = 255)
    private String tiempo_archivo_central;

}
