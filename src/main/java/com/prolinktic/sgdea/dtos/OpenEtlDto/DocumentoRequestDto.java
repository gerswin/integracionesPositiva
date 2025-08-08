package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoRequestDto {
    @JsonProperty("ofe_identificacion")
    private String ofeIdentificacion;

    @JsonProperty("pro_identificacion")
    private String proIdentificacion;

    @JsonProperty("tde_codigo")
    private String tdeCodigo;

    @JsonProperty("rfa_prefijo")
    private String rfaPrefijo;

    @JsonProperty("cdo_consecutivo")
    private String cdoConsecutivo;

    @JsonProperty("cdo_fecha")
    private String cdoFecha;

    @JsonProperty("cdo_observacion")
    private String cdoObservacion;

    @JsonProperty("cre_codigo")
    private String creCodigo;
}