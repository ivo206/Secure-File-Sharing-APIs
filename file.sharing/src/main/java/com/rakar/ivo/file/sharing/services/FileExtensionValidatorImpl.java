package com.rakar.ivo.file.sharing.services;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileExtensionValidatorImpl implements FileExtensionValidator {

    private Map<String, String> allowedExtensions;

    public FileExtensionValidatorImpl() {
        allowedExtensions = new HashMap<>();
        //images
        allowedExtensions.put("jpeg", "image/jpeg");
        allowedExtensions.put("png", "image/png");
        allowedExtensions.put("jpg", "image/jpg");
        //documents
        allowedExtensions.put("pdf", "application/pdf");
        allowedExtensions.put("doc", "application/msword");
        allowedExtensions.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        allowedExtensions.put("txt", "text/plain");
        allowedExtensions.put("xls", "application/vnd.ms-excel");
        allowedExtensions.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        //music
        allowedExtensions.put("mp3", "audio/mpeg");

    }

    @Override
    public boolean isValid(String extension) {
        return allowedExtensions.containsKey(extension);
    }
}
