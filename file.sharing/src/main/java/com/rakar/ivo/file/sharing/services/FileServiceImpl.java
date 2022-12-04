package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.configuration.FileStorageProperties;
import com.rakar.ivo.file.sharing.exceptions.FileNotFoundException;
import com.rakar.ivo.file.sharing.exceptions.FileStorageException;
import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.persistence.FileEntity;
import com.rakar.ivo.file.sharing.persistence.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository repository;
    private final FileMapper fileMapper;
    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl(FileRepository repository, FileMapper fileMapper, FileStorageProperties fileStorageProperties) {
        this.repository = repository;
        this.fileMapper = fileMapper;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageException("Can't create the directory where the files will be stored");
        }
    }

    @Override
    public File createFile(File file) {
        FileEntity entity = fileMapper.modelToEntity(file);
        FileEntity newEntity = repository.save(entity);

        return fileMapper.entityToModel(newEntity);
    }

    @Override
    public void uploadFile(String fileId, MultipartFile file) {
        Optional<FileEntity> entity = repository.findById(UUID.fromString(fileId));

        if(!entity.isPresent())
            throw new FileNotFoundException(String.format("No file exists with the file id s%", fileId));

        File fileRecord = fileMapper.entityToModel(entity.get());
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        fileRecord.setExtension(fileExtension);

        try {
            Path targetLocation = fileStorageLocation.resolve(fileRecord.getName());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + file.getName() + "Please try again.", e);
        }

        FileEntity updatedEntity = fileMapper.modelToEntity(fileRecord);
        repository.save(updatedEntity);
    }
}
