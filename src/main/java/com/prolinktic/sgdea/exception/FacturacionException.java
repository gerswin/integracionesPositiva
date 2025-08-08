package com.prolinktic.sgdea.exception;

import org.springframework.http.HttpStatus;


public class FacturacionException extends CustomHttpException{


    public static final String FALLO_CONSULTAR_DOC = "Fallo al consultar el documento electronico";
    public static final String VALIDACION_CONSULTAR_DOC = "El documento electrónico ha sido rechazado o pasa por aceptación tácita.";
    public static final String ERROR_CLASIFICACION_DOCUMENTO = "Error al radicar documento electrónico en OPEN";
    public static final String VALIDACION_TIPO_DOCUMENTO = "Tipo de documento no soportado";

    public static final String DOCUMENTO_EXISTENTE_TEMPLATE = "Ya existe un documento clasificado con el prefijo '%s' y el consecutivo '%s'.";
    public FacturacionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
