package com.prolinktic.sgdea.model.AsignacionTiposDocumentales;

public class TipoDocumentalDTO {

    private String id;
    private String descripcion;
    private Integer terminoDelTramite;
    private Boolean estado;
    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
