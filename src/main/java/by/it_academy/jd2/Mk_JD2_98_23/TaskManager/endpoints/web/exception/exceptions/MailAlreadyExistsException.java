package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

public class MailAlreadyExistsException extends RuntimeException {

    private String email;

    public MailAlreadyExistsException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
