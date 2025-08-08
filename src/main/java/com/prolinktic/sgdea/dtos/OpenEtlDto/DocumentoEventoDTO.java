package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoEventoDTO {
    private String ofe_identificacion;
    private String pro_identificacion;
    private String tde_codigo;
    private String rfa_prefijo;
    private String cdo_consecutivo;
    private String cdo_fecha;
    private String cdo_observacion;
    private String cre_codigo;
    private String cdo_cufe;

    public DocumentoEventoDTO() {
    }

    public DocumentoEventoDTO(String ofe_identificacion, String pro_identificacion,
                              String tde_codigo, String rfa_prefijo, String cdo_consecutivo,
                              String cdo_fecha, String cdo_observacion, String cre_codigo) {
        this.ofe_identificacion = ofe_identificacion;
        this.pro_identificacion = pro_identificacion;
        this.tde_codigo = tde_codigo;
        this.rfa_prefijo = rfa_prefijo;
        this.cdo_consecutivo = cdo_consecutivo;
        this.cdo_fecha = cdo_fecha;
        this.cdo_observacion = cdo_observacion;
        this.cre_codigo = cre_codigo;
    }

    public DocumentoEventoDTO(String cdo_cufe, String cdo_fecha,
                              String cdo_observacion, String cre_codigo) {
        this.cdo_cufe = cdo_cufe;
        this.cdo_fecha = cdo_fecha;
        this.cdo_observacion = cdo_observacion;
        this.cre_codigo = cre_codigo;
    }
}