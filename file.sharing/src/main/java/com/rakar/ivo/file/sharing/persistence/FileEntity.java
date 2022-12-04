package com.rakar.ivo.file.sharing.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "files")
public class FileEntity {

    public FileEntity() {
    }

    public FileEntity(UUID id, String name, String descr, Boolean virus, Long ownedBy, String signature) {
        this.id = id;
        this.name = name;
        this.descr = descr;
        this.virus = virus;
        this.ownedBy = ownedBy;
        this.signature = signature;
    }

    @Id
    @GeneratedValue
    private UUID id = null;

    private String name = null;

    private String descr = null;

    private Boolean virus = null;

    private Long ownedBy = null;

    private String signature = null;

    private String extension = null;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getExtension() { return extension; }

    public void setExtension(String extension) { this.extension = extension; }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Boolean isVirus() {
        return virus;
    }

    public void setVirus(Boolean virus) {
        this.virus = virus;
    }

    public Long getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Long ownedBy) {
        this.ownedBy = ownedBy;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
