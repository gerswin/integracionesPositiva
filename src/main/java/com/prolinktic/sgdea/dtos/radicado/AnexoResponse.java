package com.prolinktic.sgdea.dtos.radicado;

import java.util.List;

public class AnexoResponse {
    private String nodo;
    private String idNodo;
    private List<String> archivosCargados;

    // Constructores, getters y setters

    // Constructor por defecto
    public AnexoResponse() {}

    // Constructor con parámetros
    public AnexoResponse(String nodo, String idNodo, List<String> archivosCargados) {
        this.nodo = nodo;
        this.idNodo = idNodo;
        this.archivosCargados = archivosCargados;
    }

    // Getters y setters
    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getIdNodo() {
        return idNodo;
    }

    public void setIdNodo(String idNodo) {
        this.idNodo = idNodo;
    }

    public List<String> getArchivosCargados() {
        return archivosCargados;
    }

    public void setArchivosCargados(List<String> archivosCargados) {
        this.archivosCargados = archivosCargados;
    }

    @Override
    public String toString() {
        return "AnexoResponse{" +
                "nodo='" + nodo + '\'' +
                ", idNodo='" + idNodo + '\'' +
                ", archivosCargados=" + archivosCargados +
                '}';
    }
}
