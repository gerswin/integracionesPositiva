package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatoDocOpen {
    private int id;
    private String ofe_identificacion;
    private String pro_identificacion;
    private String cdo_clasificacion;
    private String resolucion;
    private String prefijo;
    private String consecutivo;
    private String fecha_documento;
    private String hora_documento;
    private String estado;
    private String cufe;
    private String qr;
    private String signaturevalue;
//    @JsonDeserialize(using = CustomUltimoEstadoDeserializer.class)
    private Ultimo_estado ultimo_estado;
    private List<Ultimo_estado> historico_estados;
}

