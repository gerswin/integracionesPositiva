package com.prolinktic.sgdea.exception;

import org.springframework.http.HttpStatus;

public class OpenException extends CustomHttpException{

    public OpenException(String message, HttpStatus httpStatus){super(message, httpStatus);}
}
