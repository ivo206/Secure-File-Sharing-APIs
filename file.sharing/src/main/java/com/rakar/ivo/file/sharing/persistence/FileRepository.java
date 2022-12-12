package com.rakar.ivo.file.sharing.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FileRepository extends PagingAndSortingRepository<FileEntity, UUID> {
}
