package com.rakar.ivo.file.sharing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.model.Files;
import com.rakar.ivo.file.sharing.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FilesApiController implements FilesApi {

    private static final Logger log = LoggerFactory.getLogger(FilesApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private final FileService fileService;

    @Autowired
    public FilesApiController(ObjectMapper objectMapper, HttpServletRequest request, FileService fileService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<UUID> createFile(File body) {
        File file = fileService.createFile(body);
        return new ResponseEntity<>(file.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(String fileId, String token) {

        try {
            byte[] fileData = fileService.downloadFile(UUID.fromString(fileId), token);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(fileData, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<File> getFileInfoById(String fileId) {
        try {
            File fileData = fileService.get(UUID.fromString(fileId));
            return new ResponseEntity<>(fileData, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Files listFiles(Integer limit) {
        Files files = fileService.listFiles(limit);
        return files;
    }

    @Override
    public ResponseEntity<Void> deleteFileByID(String fileId) {
        try {
            fileService.deleteFileByID(UUID.fromString(fileId));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> uploadFile(String fileId, MultipartFile file) {
        try {
            fileService.uploadFile(UUID.fromString(fileId), file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
