package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDocDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("ofe_identificacion")
    private String ofeIdentificacion;

    @JsonProperty("pro_identificacion")
    private String proveedorIdentificacion;

    @JsonProperty("cdo_clasificacion")
    private String cdoClasificacion;

    @JsonProperty("resolucion")
    private String resolucion;

    @JsonProperty("prefijo")
    private String prefijo;

    @JsonProperty("consecutivo")
    private String consecutivo;

    @JsonProperty("fecha_documento")
    private String fechaDocumento;

    @JsonProperty("hora_documento")
    private String horaDocumento;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("cufe")
    private String cufe;

    @JsonProperty("qr")
    private String qr;

    @JsonProperty("signaturevalue")
    private String signatureValue;

    @JsonProperty("ultimo_estado")
    private EstadoDto ultimoEstado;

    @JsonProperty("historico_estados")
    private List<EstadoDto> historicoEstados;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EstadoDto {
        @JsonProperty("estado")
        private String estado;

        @JsonProperty("resultado")
        private String resultado;

        @JsonProperty("mensaje_resultado")
        private String mensajeResultado;

        @JsonProperty("archivo")
        private String archivo;

        @JsonProperty("xml")
        private String xml;

        @JsonProperty("fecha")
        private String fecha;
    }
}
