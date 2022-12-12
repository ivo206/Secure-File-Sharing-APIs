package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.model.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    File createFile(File file);
    void uploadFile(UUID fileId, MultipartFile file);
    void deleteFileByID(UUID fileId);
    byte[] downloadFile(UUID fileId, String token) throws IOException;
    Files listFiles(Integer limit);
}
