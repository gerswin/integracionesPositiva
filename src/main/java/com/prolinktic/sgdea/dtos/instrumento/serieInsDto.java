package com.prolinktic.sgdea.dtos.instrumento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class serieInsDto {

    private String codigo;
    private String nombre;
    private Integer padre;
    public serieInsDto(String codigo, String nombre,Integer padre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.padre = padre;
    }
}
