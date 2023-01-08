package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.exceptions.InvalidMimeTypeException;
import com.rakar.ivo.file.sharing.exceptions.NotSupportedFileExtensionException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileValidatorImpl implements FileValidator {

    private Map<String, String> allowedExtensions;

    public FileValidatorImpl() {
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
    public void validate(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if(!allowedExtensions.containsKey(fileExtension))
            throw new NotSupportedFileExtensionException("Not supported exception");

        if(!allowedExtensions.get(fileExtension).equals(file.getContentType()))
            throw new InvalidMimeTypeException("MIME type not supported");
    }
}
