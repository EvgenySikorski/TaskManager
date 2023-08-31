package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskStatusUpdateDTO {

    private UUID uuid;
    @JsonProperty("dt_update")
    private LocalDateTime dateUpdate;
    private ETaskStatus status;

    public TaskStatusUpdateDTO() {
    }

    public TaskStatusUpdateDTO(UUID uuid, LocalDateTime dateUpdate, ETaskStatus status) {
        this.uuid = uuid;
        this.dateUpdate = dateUpdate;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }
}
