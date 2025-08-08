package com.prolinktic.sgdea.dtos.OpenEtlDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentoList {
    private String ofe;
    private String proveedor;
    private String tipo;
    private String prefijo;
    private String consecutivo;
    private String cufe;
    private String fecha;
    private String hora;
    private Long valor;
}
