package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.model.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface FileService {
    File createFile(File file);
    void uploadFile(String fileId, MultipartFile file);
    byte[] downloadFile(UUID fileId, String token) throws IOException;
}
