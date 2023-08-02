package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto;

import java.util.UUID;

public class UserDTO {

    private UUID uuid;
    private Long dt_create;
    private Long dt_update;
    private String mail;
    private String fio;
    private String role; //должен быть String или Enum
    private String status;

    public UserDTO() {
    }

    public UserDTO(UUID uuid, Long dt_create, Long dt_update,
                   String mail, String fio, String role, String status) {
        this.uuid = uuid;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public UserDTO(String fio, String mail, String role, String status) {
        this.fio = fio;
        this.mail = mail;
        this.role = role;
        this.status = status;
    }

    public UserDTO(UUID uuid, String mail, String fio, String role, String status) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public UserDTO(UUID uuid, String mail, String fio, String role, String status,
                   Long dt_update) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.dt_update = dt_update;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Long getDt_create() {
        return dt_create;
    }

    public void setDt_create(Long dt_create) {
        this.dt_create = dt_create;
    }

    public Long getDt_update() {
        return dt_update;
    }

    public void setDt_update(Long dt_update) {
        this.dt_update = dt_update;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
