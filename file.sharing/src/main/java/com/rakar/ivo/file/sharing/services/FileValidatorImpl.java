package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.exceptions.InvalidFileNameException;
import com.rakar.ivo.file.sharing.exceptions.InvalidMimeTypeException;
import com.rakar.ivo.file.sharing.exceptions.NotSupportedFileExtensionException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FileValidatorImpl implements FileValidator {

    private Map<String, String> allowedExtensions;

    public FileValidatorImpl() {
        allowedExtensions = new HashMap<>();
        //images
        allowedExtensions.put("jpeg", "image/jpeg");
        allowedExtensions.put("png", "image/png");
        allowedExtensions.put("jpg", "image/jpeg");
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
    public void validate(MultipartFile file) throws IOException {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        validateFileExtension(fileExtension);
        validateMimeType(file, fileExtension);
        validateFileName(file);
    }

    private static void validateFileName(MultipartFile file) {
        String pattern = "[a-zA-Z_\\-\\.]{3,50}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(file.getOriginalFilename());

        if(!m.matches())
            throw new InvalidFileNameException("Invalid file name.");
    }

    private void validateMimeType(MultipartFile file, String fileExtension) throws IOException {
        if(!allowedExtensions.get(fileExtension).equals(file.getContentType()))
            throw new InvalidMimeTypeException("MIME type not supported");

        Tika tika = new Tika();

        if(!allowedExtensions.get(fileExtension).equals(tika.detect(file.getBytes())))
            throw new InvalidMimeTypeException("MIME type not supported");
    }

    private void validateFileExtension(String fileExtension) {
        if(!allowedExtensions.containsKey(fileExtension))
            throw new NotSupportedFileExtensionException("Not supported exception");
    }
}
