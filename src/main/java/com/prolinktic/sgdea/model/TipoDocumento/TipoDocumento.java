package com.prolinktic.sgdea.model.TipoDocumento;


import com.prolinktic.sgdea.model.tipoDocumental.TipoRadicadoTipoDocumental;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the contactos database table.
 *
 */


@Entity
@Table(name="TipoDocumento")
public class TipoDocumento implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO	)
    @Column(name = "id_tipo_doc")
    private Integer id_tipo_documental;

    @ManyToOne
    @JoinColumn(name = "tipo_de_radicado",referencedColumnName = "id_clasificacionDocumento")
    private TipoRadicadoTipoDocumental tipo_de_radicado;
    private String nombre;
    private String descripcion;
    @Column(name = "termino_del_tramite")
    private Integer terminoTramite;


    private Boolean estado;

    public Integer getId_tipo_documental() {
        return id_tipo_documental;
    }

    public void setId_tipo_documental(Integer id_tipo_documental) {
        this.id_tipo_documental = id_tipo_documental;
    }

    public TipoRadicadoTipoDocumental getTipoRadicadoId() {
        return tipo_de_radicado;
    }

    public void setTipoRadicadoId(TipoRadicadoTipoDocumental tipoRadicadoTipoDocumental) {
        tipo_de_radicado = tipoRadicadoTipoDocumental;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTermino_tramite() {
        return terminoTramite;
    }

    public void setTermino_tramite(int termino_tramite) {
        this.terminoTramite = termino_tramite;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "TipoDocumento{" +
                "id_tipo_doc=" + id_tipo_documental +
                ", id_tipoDeRadicado=" + tipo_de_radicado +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", termino_del_tramite=" + terminoTramite +
                ", estado=" + estado +
                '}';
    }
}