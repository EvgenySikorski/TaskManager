package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditDTO {

    private UUID uuid;
    private LocalDateTime dt_create;
    private AuditUserDTO user;
    private String text;
    private EEssenceType type;
    private String id;

    public AuditDTO() {
    }

    public AuditDTO(UUID uuid, LocalDateTime dt_create, AuditUserDTO user, String text, EEssenceType type, String id) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.user = user;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public AuditUserDTO getUser() {
        return user;
    }

    public void setUser(AuditUserDTO user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public EEssenceType getType() {
        return type;
    }

    public void setType(EEssenceType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
