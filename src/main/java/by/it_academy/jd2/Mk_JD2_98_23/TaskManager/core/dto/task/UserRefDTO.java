package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import java.util.UUID;

public class UserRefDTO {

    private UUID uuid;

    public UserRefDTO() {
    }

    public UserRefDTO(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
