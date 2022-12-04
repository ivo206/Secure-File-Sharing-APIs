package com.rakar.ivo.file.sharing.services;

import com.rakar.ivo.file.sharing.model.File;
import com.rakar.ivo.file.sharing.persistence.FileEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FileMapper {

    File entityToModel(FileEntity entity);

    FileEntity modelToEntity(File model);
}
