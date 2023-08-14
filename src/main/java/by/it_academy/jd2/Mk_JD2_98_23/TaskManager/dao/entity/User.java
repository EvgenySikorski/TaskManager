package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "app")
public class User implements Serializable {
    @Id
    private UUID uuid;
    @Column(name = "fio", nullable = false)
    private String fio;

    @Column(name = "email", nullable = false, unique = true)
    private String mail;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private EUserRole role;

    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "dt_create", precision = 3)
    private LocalDateTime dt_create;
    @UpdateTimestamp(source = SourceType.DB)
    @Version
    @Column(name = "dt_update", precision = 3)
    private LocalDateTime dt_update;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EUserStatus status;

    @Column(name = "activation_code")
    private UUID activationCode;

    public User() {
    }

    public User(UUID uuid, String mail, String fio) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
    }

    public User(UUID uuid, String fio, String mail, EUserRole role,
                String password, LocalDateTime dt_create, LocalDateTime dt_update,
                EUserStatus status, UUID activationCode) {
        this.uuid = uuid;
        this.fio = fio;
        this.mail = mail;
        this.role = role;
        this.password = password;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.status = status;
        this.activationCode = activationCode;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDt_create() {
        return dt_create;
    }

    public void setDt_create(LocalDateTime dt_create) {
        this.dt_create = dt_create;
    }

    public LocalDateTime getDt_update() {
        return dt_update;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public EUserStatus getStatus() {
        return status;
    }

    public void setStatus(EUserStatus status) {
        this.status = status;
    }

    public UUID getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(UUID activationCode) {
        this.activationCode = activationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(fio, user.fio) && Objects.equals(mail, user.mail) && role == user.role && Objects.equals(password, user.password) && Objects.equals(dt_create, user.dt_create) && Objects.equals(dt_update, user.dt_update) && status == user.status && Objects.equals(activationCode, user.activationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fio, mail, role, password, dt_create, dt_update, status, activationCode);
    }
}
