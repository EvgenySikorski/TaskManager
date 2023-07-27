package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto;

import java.util.UUID;

public class UserUpdateDTO {

    private UUID uuid;
    private String mail;
    private String fio;
    private String role;
    private String status;
    private String password;
    private Long dt_update;


    public UserUpdateDTO() {
    }

    public UserUpdateDTO(UUID uuid, String mail, String fio, String role,
                         String status, String password, Long dt_update) {
        this.uuid = uuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
        this.password = password;
        this.dt_update = dt_update;
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

    public Long getDt_update() {
        return dt_update;
    }

    public void setDt_update(Long dt_update) {
        this.dt_update = dt_update;
    }
}
