package com.rakar.ivo.file.sharing.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends CrudRepository<FileEntity, UUID> {
}
