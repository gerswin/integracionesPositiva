package com.prolinktic.sgdea.model.tipoDocumental;

import javax.persistence.*;

@Entity
@Table(name="ClasificacionDocumento")
public class TipoRadicadoTipoDocumental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clasificacionDocumento")
    private Integer  id_tipo_de_radicado;
    @Column(name = "descripcion")
    private String descripcion;

    public Integer getId_tipo_de_radicado() {
        return id_tipo_de_radicado;
    }

    public void setId_tipo_de_radicado(Integer id_tipo_de_radicado) {
        this.id_tipo_de_radicado = id_tipo_de_radicado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
