package com.rakar.ivo.file.sharing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotSupportedFileExtensionException extends RuntimeException {
    public NotSupportedFileExtensionException(String message) {
        super(message);
    }

    public NotSupportedFileExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
