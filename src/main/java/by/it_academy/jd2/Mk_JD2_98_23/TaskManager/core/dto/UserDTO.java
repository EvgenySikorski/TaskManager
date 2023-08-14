package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserDTO {

    private UUID uuid;
    @JsonProperty("dt_create")
    private Long dtCreate;
    @JsonProperty("dt_update")
    private Long dtUpdate;
    private String mail;
    private String fio;
    private String role; //должен быть String или Enum
    private String status;

    public UserDTO() {
    }

    public UserDTO(UUID uuid, Long dtCreate, Long dtUpdate,
                   String mail, String fio, String role, String status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
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
                   Long dtUpdate) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.dtUpdate = dtUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Long getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Long dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
        this.dtUpdate = dtUpdate;
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
