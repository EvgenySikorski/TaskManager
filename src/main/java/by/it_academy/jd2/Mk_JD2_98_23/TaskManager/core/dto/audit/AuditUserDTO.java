package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.audit;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EUserRole;

import java.util.UUID;

public class AuditUserDTO {
    private UUID userUuid;
    private String mail;
    private String fio;
    private EUserRole role;

    public AuditUserDTO() {
    }

    public AuditUserDTO(UUID userUuid, String mail, String fio, EUserRole role) {
        this.userUuid = userUuid;
        this.mail = mail;
        this.fio = fio;
        this.role = role;
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
}
