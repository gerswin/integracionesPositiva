package com.prolinktic.sgdea.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class InvalidFileExtensionException extends RuntimeException {
    public InvalidFileExtensionException(String message) {
        super(message);
    }
}
