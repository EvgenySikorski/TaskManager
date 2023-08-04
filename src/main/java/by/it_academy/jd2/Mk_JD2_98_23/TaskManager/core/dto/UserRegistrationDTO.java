package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto;

public class UserRegistrationDTO {

    private String mail;
    private String fio;
    private String password;

    public UserRegistrationDTO(String mail, String fio, String password) {
        this.mail = mail;
        this.fio = fio;
        this.password = password;
    }

    public UserRegistrationDTO() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
