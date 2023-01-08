package com.rakar.ivo.file.sharing.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {
    void validate(MultipartFile file);
}
