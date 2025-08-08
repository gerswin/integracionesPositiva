package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturacionDescargaDto {
    private String ofe;
    private String proveedor;
    private String tipo;
    private String prefijo;
    private String consecutivo;
    private String cufe;
    private String fecha;
    private String hora;
    private Double valor;
    private boolean marca;
}