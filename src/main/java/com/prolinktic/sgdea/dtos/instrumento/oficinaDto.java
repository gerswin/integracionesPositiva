package com.prolinktic.sgdea.dtos.instrumento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class oficinaDto {

    private String codigo_oficina;
    private String nombre_oficina;

    public oficinaDto(String codigo, String nombre) {
        this.codigo_oficina = codigo;
        this.nombre_oficina = nombre;
    }
}
