package com.prolinktic.sgdea.model.clasificacionDocumento;

import com.prolinktic.sgdea.dtos.OpenEtlDto.Clasificacion;
import com.prolinktic.sgdea.dtos.OpenEtlDto.ClasificacionDocumento;
import com.prolinktic.sgdea.dtos.OpenEtlDto.TipoDocumentoFAC;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "documento_clasificado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoClasificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "evento")
    private String evento;

    @Column(name = "ofe_identificacion", nullable = false)
    private String ofeIdentificacion;

    @Column(name = "pro_identificacion", nullable = false)
    private String proIdentificacion;

    @Column(name = "tde_codigo", nullable = false)
    private String tdeCodigo;

    @Column(name = "rfa_prefijo", nullable = false)
    private String rfaPrefijo;

    @Column(name = "cdo_consecutivo", nullable = false)
    private String cdoConsecutivo;

    @Column(name = "cdo_fecha", nullable = false)
    private String cdoFecha;

    @Column(name = "cdo_observacion")
    private String cdoObservacion;

    @Column(name = "cre_codigo")
    private String creCodigo;

    @Column(name = "clasificacion", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClasificacionDocumento clasificacion;

    @Column(name = "clasificacion_otros", nullable = false)
    @Enumerated(EnumType.STRING)
    private Clasificacion clasificacionOtros;

    @Column(name = "tipo_documento_fac", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumentoFAC tipoDocumentoFAC;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "formato_archivo")
    private String formatoArchivo;

    @Column(name = "fecha_cargue")
    private String fechaCargue;

    @Column(name = "tipo_archivo")
    private String tipoArchivo;

    @Column(name = "nombre_archivo")
    private String nombreArchivo;

}
