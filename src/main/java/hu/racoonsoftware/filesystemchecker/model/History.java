package hu.racoonsoftware.filesystemchecker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * This class represent a history table element
 */
@Table(schema = "rcswfilesystem",name="history")
public class History implements Persistable<UUID> {
    @Id
    private UUID id;
    @Column("requestby")
    private String requestBy;
    @Column("result")
    private Map<String, Integer> result;
    @Column("requestedon")
    private LocalDateTime requestedOn;
    @Column("path")
    private String path;
    @Column("extension")
    private String extension;

    @Transient
    private boolean isNew = false;

    public History(UUID id, String requestBy, Map<String, Integer> result, LocalDateTime requestedOn, String path, String extension) {
        this.id = id;
        this.requestBy = requestBy;
        this.result = result;
        this.requestedOn = requestedOn;
        this.path = path;
        this.extension = extension;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean isNewEntity) {
        isNew = isNewEntity;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public Map<String, Integer> getResult() {
        return result;
    }

    public void setResult(Map<String, Integer> result) {
        this.result = result;
    }

    public LocalDateTime getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDateTime requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


}
