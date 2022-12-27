package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.configuration.FileStorageProperties;
import com.rakar.ivo.file.sharing.exceptions.FileNotFoundException;
import com.rakar.ivo.file.sharing.exceptions.FileStorageException;
import com.rakar.ivo.file.sharing.exceptions.NotSupportedFileExtensionException;
import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.persistence.FileEntity;
import com.rakar.ivo.file.sharing.persistence.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final FileExtensionValidator extensionValidator;
    private final Path fileStorageLocation;

    @Autowired
    public FileServiceImpl(FileRepository repository, FileMapper fileMapper, FileExtensionValidator extensionValidator, FileStorageProperties fileStorageProperties) {
        this.repository = repository;
        this.fileMapper = fileMapper;
        this.extensionValidator = extensionValidator;
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
    public void uploadFile(UUID fileId, MultipartFile file) {
        Optional<FileEntity> maybeEntity = repository.findById(fileId);

        if(!maybeEntity.isPresent())
            throw new FileNotFoundException(String.format("No file exists with the file id s%", fileId));

        FileEntity entity = maybeEntity.get();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        if(!extensionValidator.isValid(fileExtension))
            throw new NotSupportedFileExtensionException("Not supported exception");

        entity.setExtension(fileExtension);

        try {
            Path targetLocation = fileStorageLocation.resolve(fileId+"."+fileExtension);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + file.getName() + "Please try again.", e);
        }

        repository.save(entity);
    }

    @Override
    public void deleteFileByID(UUID fileId) {
        Optional<FileEntity> maybeFile = repository.findById(fileId);

        if(!maybeFile.isPresent())
            throw new FileNotFoundException(String.format("No file exists with the file id s%", fileId));

        FileEntity fileEntity = maybeFile.get();
        Path fileLocation = fileStorageLocation.resolve(fileId+"."+fileEntity.getExtension());
        java.io.File file = new java.io.File(fileLocation.toUri());

        repository.delete(fileEntity);

        if(!file.exists())
            throw new FileNotFoundException("The file with id "+fileId+" does not exist");

        file.delete();
    }

    @Override
    public byte[] downloadFile(UUID fileId, String token) throws IOException {
        Optional<FileEntity> maybeFile = repository.findById(fileId);
        if(!maybeFile.isPresent())
            throw new FileNotFoundException("The file with id "+fileId+" does not exist");

        FileEntity entity = maybeFile.get();

        Path fileLocation = fileStorageLocation.resolve(fileId+"."+entity.getExtension()).normalize();;
        java.io.File file = new java.io.File(fileLocation.toUri());

        if(!file.exists())
            throw new FileNotFoundException("The file with id "+fileId+" does not exist");

        return Files.readAllBytes(file.toPath());
    }

    @Override
    public com.rakar.ivo.file.sharing.model.Files listFiles(Integer limit) {
        if(limit==null) limit = 100;

        Pageable filter = PageRequest.of(0, limit);
        Page<FileEntity> files = repository.findAll(filter);
        return fileMapper.entityToModel(files.getContent());
    }

    @Override
    public File get(UUID fileId) {
        Optional<FileEntity> maybeFile = repository.findById(fileId);
        if(!maybeFile.isPresent())
            throw new FileNotFoundException("The file with id "+fileId+" does not exist");

        return fileMapper.entityToModel(maybeFile.get());
    }
}
