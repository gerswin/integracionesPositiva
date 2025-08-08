package com.prolinktic.sgdea.dtos.radicado;

public enum TipoDocumentoIdentidad {

    TARJETA_IDENTIDAD(1, "Tarjeta de Identidad"),
    CEDULA_CIUDADANIA(12, "Cédula de Ciudadanía"),
    PASAPORTE(3, "Pasaporte"),
    NIT(4, "Nit"),
    CEDULA_EXTRANJERIA(5, "Cedula de Extranjería");

    private final int id;
    private final String nombre;

    TipoDocumentoIdentidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public static String getNombreById(int id) {
        for (TipoDocumentoIdentidad tipo : TipoDocumentoIdentidad.values()) {
            if (tipo.id == id) {
                return tipo.nombre;
            }
        }
        return "Desconocido"; // Valor por defecto si no se encuentra el ID
    }
}