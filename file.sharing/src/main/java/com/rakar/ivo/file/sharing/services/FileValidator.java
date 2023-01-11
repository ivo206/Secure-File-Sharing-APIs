package com.rakar.ivo.file.sharing.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileValidator {
    void validate(MultipartFile file) throws IOException;
}
