package com.prolinktic.sgdea.model.Serie.DTOS;

import lombok.Data;

@Data
public class DependenciaSerieDto {
    private Integer codigo;
    private String descripcion;

    @Override
    public String toString() {
        return "{" +
                "codigo=" + codigo +
                ", descrcipcion='" + descripcion + '\'' +
                '}';
    }

    public DependenciaSerieDto() {
    }

    public DependenciaSerieDto(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
