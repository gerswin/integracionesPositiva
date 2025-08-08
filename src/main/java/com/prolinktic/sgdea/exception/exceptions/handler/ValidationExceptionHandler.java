package com.prolinktic.sgdea.exception.exceptions.handler;

import com.prolinktic.sgdea.exception.exceptions.ControlException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ControlException.class)
    public ResponseEntity<ErrorResponse> pqrdException(ControlException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCodigo(ex.getCodigo().toString());
        errorResponse.setMensaje(ex.getMensaje());

        return ResponseEntity.status(ex.getCodigo()).body(errorResponse);
    }

}
