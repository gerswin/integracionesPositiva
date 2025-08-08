package com.prolinktic.sgdea.dtos.OpenEtlDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseConsultaDocumentoDto {
    private String mensaje;
    private String cufe;
    private String ofeIdentificacion;
    private String proIdentificacion;
    private String cdoClasificacion;
    private String resolucion;
    private String prefijo;
    private String consecutivo;
    private String fechaDocumento;
    private String horaDocumento;
    private String estado;
    private Boolean marca;
    private String clasificador;
    private String qr;
    private String signaturevalue;
    private String xml;
    private String pdf;
    private ConsultaDocDto.EstadoDto ultimoEstado;
    private List<ConsultaDocDto.EstadoDto> historicoEstados;

}
