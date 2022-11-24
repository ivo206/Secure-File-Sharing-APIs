package com.rakar.ivo.file.sharing.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

@Validated
public class File {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("name")
    private String name = null;

    @JsonProperty("descr")
    private String descr = null;

    @JsonProperty("virus")
    private Boolean virus = null;

    @JsonProperty("ownedBy")
    private Long ownedBy = null;

    @JsonProperty("signature")
    private String signature = null;

    public File id(Long id) {
        this.id = id;
        return this;
    }

    /**
     * Get id
     * @return id
     **/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Name of the file
     * @return name
     **/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File descr(String descr) {
        this.descr = descr;
        return this;
    }

    /**
     * Short description of what the file is about
     * @return descr
     **/


    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public File virus(Boolean virus) {
        this.virus = virus;
        return this;
    }

    /**
     * Determines whether file uploaded is virus
     * @return virus
     **/

    public Boolean isVirus() {
        return virus;
    }

    public void setVirus(Boolean virus) {
        this.virus = virus;
    }

    public File ownedBy(Long ownedBy) {
        this.ownedBy = ownedBy;
        return this;
    }

    /**
     * Determines the user Id of the file owner
     * @return ownedBy
     **/

    public Long getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(Long ownedBy) {
        this.ownedBy = ownedBy;
    }

    public File signature(String signature) {
        this.signature = signature;
        return this;
    }

    /**
     * Digital Signature created by the file owner.
     * @return signature
     **/

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return Objects.equals(this.id, file.id) &&
                Objects.equals(this.name, file.name) &&
                Objects.equals(this.descr, file.descr) &&
                Objects.equals(this.virus, file.virus) &&
                Objects.equals(this.ownedBy, file.ownedBy) &&
                Objects.equals(this.signature, file.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, descr, virus, ownedBy, signature);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class File {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    descr: ").append(toIndentedString(descr)).append("\n");
        sb.append("    virus: ").append(toIndentedString(virus)).append("\n");
        sb.append("    ownedBy: ").append(toIndentedString(ownedBy)).append("\n");
        sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
