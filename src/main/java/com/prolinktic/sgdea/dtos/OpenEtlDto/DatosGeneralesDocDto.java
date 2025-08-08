package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosGeneralesDocDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("ofe_identificacion")
    private String ofeIdentificacion;

    @JsonProperty("adquirente_identificacion")
    private String adquirenteIdentificacion;

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

    @JsonProperty("cdo_clasificacion")
    private String cdoClasificacion;

    @JsonProperty("resultado_validacion_previa")
    private ValidacionPreviaDto resultadoValidacionPrevia;

    @Data
    public static class EstadoDto {
        @JsonProperty("estado")
        private String estado;

        @JsonProperty("resultado")
        private String resultado;

        @JsonProperty("mensaje_resultado")
        private String mensajeResultado;

        @JsonProperty("correos_notificacion")
        private String correosNotificacion;

        @JsonProperty("fecha")
        private String fecha;
    }

    @Data
    public static class ValidacionPreviaDto {
        @JsonProperty("estado")
        private String estado;

        @JsonProperty("descripcion_estado")
        private String descripcionEstado;

        @JsonProperty("document_key")
        private String documentKey;

        @JsonProperty("notificaciones")
        private String notificaciones;

        @JsonProperty("cdo_fecha_validacion_dian")
        private String cdoFechaValidacionDian;
    }
}

