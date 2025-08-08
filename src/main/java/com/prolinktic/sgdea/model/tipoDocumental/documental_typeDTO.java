package com.prolinktic.sgdea.model.tipoDocumental;


public class documental_typeDTO {
    private Integer id;
    private String descripcion;
    private Integer terminoDelTramite;
    private Integer tipoDeRadicadoId;//este dato deberia tener una relacion con la parte de radicacion
    private Boolean estado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTerminoDelTramite() {
        return terminoDelTramite;
    }

    public void setTerminoDelTramite(Integer terminoDelTramite) {
        this.terminoDelTramite = terminoDelTramite;
    }

    public Integer getTipoDeRadicado() {
        return tipoDeRadicadoId;
    }

    public void setTipoDeRadicado(Integer tipoDeRadicado) {
        this.tipoDeRadicadoId = tipoDeRadicado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
