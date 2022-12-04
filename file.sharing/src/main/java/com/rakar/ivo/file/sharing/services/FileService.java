package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File createFile(File file);
    void uploadFile(String fileId, MultipartFile file);
}
