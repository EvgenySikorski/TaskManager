package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserUpdateDTO {

    private UUID uuid;
    private String mail;
    private String fio;
    private String role;
    private String status;
    private String password;
    @JsonProperty("dt_update")
    private LocalDateTime dateUpdate;


    public UserUpdateDTO() {
    }

    public UserUpdateDTO(UUID uuid, String mail, String fio, String role,
                         String status, String password, LocalDateTime dateUpdate) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dateUpdate = dateUpdate;
    }

    public UserUpdateDTO(UUID uuid, String mail, String fio, String role, String status, String password) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty("dt_update")
    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
