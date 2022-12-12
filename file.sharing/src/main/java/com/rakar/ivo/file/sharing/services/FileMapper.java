package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.model.Files;
import com.rakar.ivo.file.sharing.persistence.FileEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FileMapper {

    File entityToModel(FileEntity entity);

    Files entityToModel(List<FileEntity> entities);

    FileEntity modelToEntity(File model);

}
