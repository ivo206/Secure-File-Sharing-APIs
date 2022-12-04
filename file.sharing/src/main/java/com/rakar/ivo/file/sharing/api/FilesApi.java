package com.rakar.ivo.file.sharing.api;

import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.model.Files;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FilesApi {

    @RequestMapping(value = "/files",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<UUID> createFile(@RequestBody File body);

    @RequestMapping(value = "/files/{fileId}/download",
            produces = { "application/octet-stream", "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") String fileId, @RequestParam(value = "token", required = false) String token);

    @RequestMapping(value = "/files",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Files> listFiles(@RequestParam(value = "limit", required = false) Integer limit);

    @RequestMapping(value = "/files/{fileId}/recover",
            produces = { "text/plain", "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Boolean> recoverFile(@PathVariable("fileId") String fileId);

    @RequestMapping(value = "/files/{fileId}/share",
            produces = { "application/octet-stream", "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<Resource> shareableURL(@PathVariable("fileId") String fileId, @RequestParam(value = "userName", required = false) String userName, @RequestParam(value = "expires", required = false) String expires);

    @RequestMapping(value = "/files/{fileId}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<File> showFileById(@PathVariable("fileId") String fileId);

    @RequestMapping(value = "/files/{fileId}/upload",
            produces = { "text/plain", "application/json" },
            consumes = { "multipart/form-data" },
            method = RequestMethod.POST)
    ResponseEntity<String> uploadFile(@PathVariable("fileId") String fileId, @RequestParam("file") MultipartFile file);
}
