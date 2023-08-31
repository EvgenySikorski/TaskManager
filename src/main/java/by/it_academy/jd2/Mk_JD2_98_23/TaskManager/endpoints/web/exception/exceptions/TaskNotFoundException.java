package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.endpoints.web.exception.exceptions;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

    private UUID uuid;

    public TaskNotFoundException(UUID uuid) {
        super();
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
