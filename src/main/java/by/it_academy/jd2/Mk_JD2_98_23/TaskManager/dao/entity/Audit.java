package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EEssenceType;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Entity
@Table(name = "audit", schema = "app")
public class Audit implements Serializable {
    @Id
    private UUID uuid;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "dt_create")
    private LocalDateTime dt_create;

    @Column(name = "user_uuid", nullable = false)
    private UUID userUuid;

    @Column(name = "email", nullable = false, unique = true)
    private String mail;

    @Column(name = "fio", nullable = false)
    private String fio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EUserRole role;

    @Column(name = "text", nullable = false)
    private String text;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EEssenceType type;

    @Column(name = "id", nullable = false)
    private String id;

    public Audit() {
    }

    public Audit(UUID userUuid, String mail, String fio, EUserRole role, String text, EEssenceType type, String id) {
        this.userUuid = userUuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.text = text;
        this.type = type;
        this.id = id;
    }

    public Audit(UUID uuid, LocalDateTime dt_create, UUID userUuid, String mail, String fio, EUserRole role,
                 String text, EEssenceType type, String id) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.userUuid = userUuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audit audit = (Audit) o;
        return Objects.equals(uuid, audit.uuid) && Objects.equals(dt_create, audit.dt_create) && Objects.equals(userUuid, audit.userUuid) && Objects.equals(mail, audit.mail) && Objects.equals(fio, audit.fio) && role == audit.role && Objects.equals(text, audit.text) && type == audit.type && Objects.equals(id, audit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dt_create, userUuid, mail, fio, role, text, type, id);
    }
}





