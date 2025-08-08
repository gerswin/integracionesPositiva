package com.prolinktic.sgdea.dtos.OpenEtlDto.registroDocumento;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DocumentoRequestDTO {
    private String tde_codigo;
    private String top_codigo;
    private String ofe_identificacion;
    private String adq_identificacion;
    private String adq_identificacion_autorizado;
    private String rfa_prefijo;
    private String rfa_resolucion;
    private String cdo_consecutivo;
    private String cdo_fecha;
    private String cdo_hora;
    private String cdo_vencimiento;
    private String cdo_representacion_grafica_documento;
    private String cdo_representacion_grafica_acuse;
    private List<MedioPago> cdo_medios_pago;
    private InformacionAdicional cdo_informacion_adicional;
    private String mon_codigo;
    private String cdo_valor_sin_impuestos;
    private String cdo_impuestos;
    private String cdo_total;
    private String cdo_retenciones_sugeridas;
    private String cdo_anticipo;
    private String cdo_redondeo;
    private List<DetalleAnticipo> cdo_detalle_anticipos;
    private List<DetalleRetencionSugerida> cdo_detalle_retenciones_sugeridas;
    private List<Item> items;
    private List<Tributo> tributos;

    @Data
    public static class MedioPago {
        private String fpa_codigo;
        private String mpa_codigo;
        private String men_fecha_vencimiento;
    }

    @Data
    public static class InformacionAdicional {
        private String cdo_procesar_documento;
        private LocalDate fecha_inicio;
        private LocalDate fecha_fin;
    }

    @Data
    public static class DetalleAnticipo {
        // Define fields here as needed
    }

    @Data
    public static class DetalleRetencionSugerida {
        private String tipo;
        private String razon;
        private String porcentaje;
        private ValorMonedaNacional valor_moneda_nacional;
    }

    @Data
    public static class ValorMonedaNacional {
        private String base;
        private String valor;
    }

    @Data
    public static class Item {
        private String ddo_tipo_item;
        private String ddo_secuencia;
        private String cpr_codigo;
        private String ddo_codigo;
        private String ddo_descripcion_uno;
        private String ddo_cantidad;
        private String und_codigo;
        private String ddo_valor_unitario;
        private String ddo_total;
        private List<Object> ddo_informacion_adicional;
    }

    @Data
    public static class Tributo {
        private String ddo_secuencia;
        private String tri_codigo;
        private String iid_valor;
        private String iid_motivo_exencion;
        private IidPorcentaje iid_porcentaje;
    }

    @Data
    public static class IidPorcentaje {
        private String iid_base;
        private String iid_porcentaje;
    }
}
