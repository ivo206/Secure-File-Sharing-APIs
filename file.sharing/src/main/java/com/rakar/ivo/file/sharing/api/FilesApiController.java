package com.rakar.ivo.file.sharing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.model.Files;
import com.rakar.ivo.file.sharing.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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

    public ResponseEntity<UUID> createFile(File body) {
        File file = fileService.createFile(body);
        return new ResponseEntity<UUID>(file.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<byte[]> downloadFile(String fileId, String token) {

        try {
            byte[] fileData = fileService.downloadFile(UUID.fromString(fileId), token);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(fileData, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            return new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Files> listFiles(Integer limit) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {

                return new ResponseEntity<Files>(objectMapper.readValue("[ {\n  \"descr\" : \"descr\",\n  \"signature\" : \"signature\",\n  \"name\" : \"name\",\n  \"id\" : 0,\n  \"virus\" : true,\n  \"ownedBy\" : 6\n}, {\n  \"descr\" : \"descr\",\n  \"signature\" : \"signature\",\n  \"name\" : \"name\",\n  \"id\" : 0,\n  \"virus\" : true,\n  \"ownedBy\" : 6\n} ]", Files.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Files>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Files>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Boolean> recoverFile(String fileId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Boolean>(objectMapper.readValue("false", Boolean.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Boolean>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Resource> shareableURL(String fileId, String userName, String expires) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Resource>(objectMapper.readValue("\"\"", Resource.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Resource>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Resource>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<File> showFileById(String fileId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<File>(objectMapper.readValue("{\n  \"descr\" : \"descr\",\n  \"signature\" : \"signature\",\n  \"name\" : \"name\",\n  \"id\" : 0,\n  \"virus\" : true,\n  \"ownedBy\" : 6\n}", File.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<File>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<File>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<String> uploadFile(String fileId, MultipartFile file) {
        fileService.uploadFile(fileId, file);
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }
}
