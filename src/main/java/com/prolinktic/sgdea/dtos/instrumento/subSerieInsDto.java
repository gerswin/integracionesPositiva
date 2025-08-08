package com.prolinktic.sgdea.dtos.instrumento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class subSerieInsDto {

    private String codigo_subserie;
    private String nombre_subserie;

    public subSerieInsDto(String codigo, String nombre) {
        this.codigo_subserie = codigo;
        this.nombre_subserie = nombre;
    }
}
