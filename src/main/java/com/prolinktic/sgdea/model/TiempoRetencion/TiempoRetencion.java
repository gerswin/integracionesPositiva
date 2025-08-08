package com.prolinktic.sgdea.model.TiempoRetencion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tiemporetencion")
public class TiempoRetencion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tiempo_retencion")
    private int idTiempoRetencion;
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tiempo_retencion")
    private int tiempoRetencion;

    public TiempoRetencion() {
    }

    public TiempoRetencion(int idTiempoRetencion, String descripcion, int tiempoRetencion) {
        this.idTiempoRetencion = idTiempoRetencion;
        this.descripcion = descripcion;
        this.tiempoRetencion = tiempoRetencion;
    }

    public int getIdTiempoRetencion() {
        return idTiempoRetencion;
    }

    public void setIdTiempoRetencion(int idTiempoRetencion) {
        this.idTiempoRetencion = idTiempoRetencion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTiempoRetencion() {
        return tiempoRetencion;
    }

    public void setTiempoRetencion(int tiempoRetencion) {
        this.tiempoRetencion = tiempoRetencion;
    }
}
