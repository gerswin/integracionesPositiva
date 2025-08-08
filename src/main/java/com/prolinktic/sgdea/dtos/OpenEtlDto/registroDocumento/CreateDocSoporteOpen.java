package com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateDocSoporteOpen {
    private DocSoporteOpen documentos;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DocSoporteOpen {
        @JsonProperty("DS")
        private List<DSDocumentoRequestDTO> DS;

        @JsonProperty("DS_NC")
        private List<DSNCDocumentoRequestDTO> DS_NC;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DSDocumentoRequestDTO {
        private String tde_codigo;
        private String top_codigo;
        private String ofe_identificacion;
        private String adq_identificacion;
        private String rfa_resolucion;
        private String rfa_prefijo;
        private String cdo_consecutivo;
        private String cdo_fecha;
        private String cdo_hora;
        private String cdo_vencimiento;
        private List<String> cdo_observacion;
        private String cdo_representacion_grafica_documento;
        private String cdo_representacion_grafica_acuse;
        private List<DocumentoReferencia> cdo_documento_referencia;
        private OrdenReferencia dad_orden_referencia;
        private List<MedioPago> cdo_medios_pago;
        private InformacionAdicional cdo_informacion_adicional;
        private MonedaAlternativa dad_moneda_alternativa;
        private String mon_codigo;
        private String mon_codigo_extranjera;
        private String cdo_trm;
        private String cdo_trm_fecha;
        private String cdo_envio_dian_moneda_extranjera;
        private String cdo_valor_sin_impuestos;
        private String cdo_valor_sin_impuestos_moneda_extranjera;
        private String cdo_impuestos;
        private String cdo_impuestos_moneda_extranjera;
        private String cdo_retenciones;
        private String cdo_retenciones_moneda_extranjera;
        private String cdo_total;
        private String cdo_total_moneda_extranjera;
        private String cdo_cargos;
        private String cdo_cargos_moneda_extranjera;
        private String cdo_descuentos;
        private String cdo_descuentos_moneda_extranjera;
        private String cdo_retenciones_sugeridas;
        private String cdo_retenciones_sugeridas_moneda_extranjera;
        private String cdo_redondeo;
        private String cdo_redondeo_moneda_extranjera;
        private List<DetalleRetencionSugerida> cdo_detalle_retenciones_sugeridas;
        private List<DetalleCargo> cdo_detalle_cargos;
        private List<DetalleDescuento> cdo_detalle_descuentos;
        private List<Item> items;
        private List<Tributo> tributos;
        private List<Retencion> retenciones;
        private List<Object> vendedor;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DSNCDocumentoRequestDTO {
        private String tde_codigo;
        private String top_codigo;
        private String atributo_top_codigo_id;
        private String ofe_identificacion;
        private String adq_identificacion;
        private String rfa_prefijo;
        private String cdo_consecutivo;
        private String cdo_fecha;
        private String cdo_hora;
        private String cdo_vencimiento;
        private List<String> cdo_observacion;
        private String cdo_representacion_grafica_documento;
        private String cdo_representacion_grafica_acuse;
        private List<DocumentoReferencia> cdo_documento_referencia;
        private List<ConceptoCorreccion> cdo_conceptos_correccion;
        private PeriodoFacturacion cdo_periodo_facturacion;
        private OrdenReferencia dad_orden_referencia;
        private List<MedioPago> cdo_medios_pago;
        private InformacionAdicional cdo_informacion_adicional;
        private MonedaAlternativa dad_moneda_alternativa;
        private String mon_codigo_extranjera;
        private String cdo_trm;
        private String cdo_trm_fecha;
        private String cdo_envio_dian_moneda_extranjera;
        private String cdo_valor_sin_impuestos;
        private String cdo_valor_sin_impuestos_moneda_extranjera;
        private String cdo_impuestos;
        private String cdo_impuestos_moneda_extranjera;
        private String cdo_retenciones;
        private String cdo_retenciones_moneda_extranjera;
        private String cdo_total;
        private String cdo_total_moneda_extranjera;
        private String cdo_cargos;
        private String cdo_cargos_moneda_extranjera;
        private String cdo_descuentos;
        private String cdo_descuentos_moneda_extranjera;
        private String cdo_retenciones_sugeridas;
        private String cdo_retenciones_sugeridas_moneda_extranjera;
        private String cdo_redondeo;
        private String cdo_redondeo_moneda_extranjera;
        private List<DetalleRetencionSugerida> cdo_detalle_retenciones_sugeridas;
        private List<DetalleCargo> cdo_detalle_cargos;
        private List<DetalleDescuento> cdo_detalle_descuentos;
        private List<Item> items;
        private List<Tributo> tributos;
        private List<Retencion> retenciones;
        private List<Object> vendedor;
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class DocumentoReferencia {
    private String clasificacion;
    private String prefijo;
    private String consecutivo;
    private String cufe;
    private String fecha_emision;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class OrdenReferencia {
    private String referencia;
    private String fecha_emision_referencia;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class MedioPago {
    private String fpa_codigo;
    private String atributo_fpa_codigo_id;
    private String atributo_fpa_codigo_name;
    private String mpa_codigo;
    private String men_fecha_vencimiento;
    private List<IdentificadorPago> men_identificador_pago;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class IdentificadorPago {
    private String id;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class InformacionAdicional {
    private String cdo_procesar_documento;
    private DireccionDomicilio cdo_direccion_domicilio_correspondencia;
    private String clave;
    private List<String> cdo_informacion_adicional_excluir_xml;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class DireccionDomicilio {
    private String pai_codigo;
    private String dep_codigo;
    private String mun_codigo;
    private String cpo_codigo;
    private String adq_direccion;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class MonedaAlternativa {
    private String dad_codigo_moneda_alternativa;
    private String dad_codigo_moneda_extranjera_alternativa;
    private String dad_trm_alternativa;
    private String dad_trm_fecha_alternativa;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class DetalleRetencionSugerida {
    private String tipo;
    private String razon;
    private String porcentaje;
    private ValorMoneda valor_moneda_nacional;
    private ValorMoneda valor_moneda_extranjera;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class DetalleCargo {
    private String razon;
    private String porcentaje;
    private ValorMoneda valor_moneda_nacional;
    private ValorMoneda valor_moneda_extranjera;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class DetalleDescuento {
    private String cde_codigo;
    private String razon;
    private String porcentaje;
    private ValorMoneda valor_moneda_nacional;
    private ValorMoneda valor_moneda_extranjera;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class ValorMoneda {
    private String base;
    private String valor;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class Item {
    private String ddo_tipo_item;
    private String ddo_secuencia;
    private String cpr_codigo;
    private String ddo_codigo;
    private String ddo_descripcion_uno;
    private String ddo_descripcion_dos;
    private String ddo_descripcion_tres;
    private List<String> ddo_notas;
    private String ddo_cantidad;
    private String ddo_cantidad_paquete;
    private String und_codigo;
    private String ddo_valor_unitario;
    private String ddo_valor_unitario_moneda_extranjera;
    private String ddo_total;
    private String ddo_total_moneda_extranjera;
    private String ddo_indicador_muestra;
    private PrecioReferencia ddo_precio_referencia;
    private String ddo_marca;
    private String ddo_modelo;
    private String ddo_codigo_vendedor;
    private String ddo_codigo_vendedor_subespecificacion;
    private String ddo_codigo_fabricante;
    private String ddo_codigo_fabricante_subespecificacion;
    private String ddo_nombre_fabricante;
    private String pai_codigo;
    private String ddo_identificador;
    private List<PropiedadAdicional> ddo_propiedades_adicionales;
    private List<DetalleCargo> ddo_cargos;
    private List<DetalleDescuento> ddo_descuentos;
    private InformacionAdicional ddo_informacion_adicional;
    private FechaCompra ddo_fecha_compra;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class PrecioReferencia {
    private String pre_codigo;
    private String ddo_valor_muestra;
    private String ddo_valor_muestra_moneda_extranjera;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class PropiedadAdicional {
    private String nombre;
    private String valor;
    private String cantidad;
    private String und_codigo;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class FechaCompra {
    private String fecha_compra;
    private String codigo;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class Tributo {
    private String ddo_secuencia;
    private String tri_codigo;
    private String iid_valor;
    private String iid_valor_moneda_extranjera;
    private String iid_motivo_exencion;
    private String iid_nombre_figura_tributaria;
    private Porcentaje iid_porcentaje;
    private Unidad iid_unidad;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class Porcentaje {
    private String iid_base;
    private String iid_base_moneda_extranjera;
    private String iid_porcentaje;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class Unidad {
    private String iid_cantidad;
    private String und_codigo;
    private String iid_base_unidad_medida;
    private String iid_base_unidad_medida_moneda_extranjera;
    private String iid_valor_unitario;
    private String iid_valor_unitario_moneda_extranjera;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class Retencion {
    private String ddo_secuencia;
    private String tri_codigo;
    private String iid_valor;
    private String iid_valor_moneda_extranjera;
    private String iid_motivo_exencion;
    private String iid_nombre_figura_tributaria;
    private Porcentaje iid_porcentaje;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class ConceptoCorreccion {
    private String cco_codigo;
    private Object cdo_observacion_correccion;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
class PeriodoFacturacion {
    private String dad_periodo_fecha_inicio;
    private String dad_periodo_hora_inicio;
    private String dad_periodo_fecha_fin;
    private String dad_periodo_hora_fin;
}