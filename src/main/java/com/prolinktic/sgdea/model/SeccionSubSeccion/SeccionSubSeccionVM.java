package com.prolinktic.sgdea.model.SeccionSubSeccion;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeccionSubSeccionVM {
    private int id_dependencia;
    private Integer id_padre;
    private String codigo_dependencia;
    private String nombre;
    private String direccion;
    private String sigla;
    private int estado;
}