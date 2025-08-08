package com.prolinktic.sgdea.dtos.OpenEtlDto.resolucion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResolucionDTO {
    @JsonProperty("rfa_id")
    private Integer rfaId;

    @JsonProperty("ofe_id")
    private Integer ofeId;

    @JsonProperty("rfa_resolucion")
    private String rfaResolucion;

    @JsonProperty("rfa_prefijo")
    private String rfaPrefijo;

    @JsonProperty("rfa_clave_tecnica")
    private String rfaClaveTecnica;

    @JsonProperty("rfa_tipo")
    private String rfaTipo;

    @JsonProperty("rfa_fecha_desde")
    private String rfaFechaDesde;

    @JsonProperty("rfa_fecha_hasta")
    private String rfaFechaHasta;

    @JsonProperty("rfa_consecutivo_inicial")
    private String rfaConsecutivoInicial;

    @JsonProperty("rfa_consecutivo_final")
    private String rfaConsecutivoFinal;

    @JsonProperty("cdo_control_consecutivos")
    private String cdoControlConsecutivos;

    @JsonProperty("cdo_consecutivo_provisional")
    private String cdoConsecutivoProvisional;

    @JsonProperty("rfa_dias_aviso")
    private Integer rfaDiasAviso;

    @JsonProperty("rfa_consecutivos_aviso")
    private Integer rfaConsecutivosAviso;

    @JsonProperty("fecha_creacion")
    private String fechaCreacion;

    @JsonProperty("fecha_modificacion")
    private String fechaModificacion;

    @JsonProperty("usuario_creacion")
    private Integer usuarioCreacion;

    private String estado;

    @JsonProperty("get_usuario_creacion")
    private UsuarioCreacionDTO getUsuarioCreacion;

    @JsonProperty("get_configuracion_obligado_facturar_electronicamente")
    private ConfiguracionOFEDTO getConfiguracionObligadoFacturarElectronicamente;

}