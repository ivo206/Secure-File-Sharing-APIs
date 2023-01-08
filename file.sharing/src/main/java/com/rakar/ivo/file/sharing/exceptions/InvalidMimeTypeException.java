package com.rakar.ivo.file.sharing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMimeTypeException extends RuntimeException {
    public InvalidMimeTypeException(String message) {
        super(message);
    }

    public InvalidMimeTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
