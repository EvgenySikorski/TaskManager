package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private UUID uuid;

    public UserNotFoundException(UUID uuid) {
        super();
        this.uuid = uuid;
    }

    public UserNotFoundException() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}

