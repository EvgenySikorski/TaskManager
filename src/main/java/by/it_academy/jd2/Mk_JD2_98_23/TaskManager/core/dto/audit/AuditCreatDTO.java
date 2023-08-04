package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditCreatDTO {
    private UUID userUuid;
    private String mail;
    private String fio;
    private EUserRole role;
    private String text;
    private EEssenceType type;
    private String id;

    public AuditCreatDTO() {
    }

    public AuditCreatDTO(UUID userUuid, String mail, String fio, EUserRole role, String text, EEssenceType type, String id) {
        this.userUuid = userUuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
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
