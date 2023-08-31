package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskUpdateDTO {

    private UUID uuid;
    private ProjectRefDTO project;
    private String title;
    private String description;
    private ETaskStatus status;
    private UserRefDTO implementer;
    @JsonProperty ("dt_update")
    private LocalDateTime dateUpdate;

    public TaskUpdateDTO() {
    }

    public TaskUpdateDTO(UUID uuid, ProjectRefDTO project, String title, String description,
                         ETaskStatus status, UserRefDTO implementer, LocalDateTime dateUpdate) {
        this.uuid = uuid;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
        this.dateUpdate = dateUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProjectRefDTO getProject() {
        return project;
    }

    public void setProject(ProjectRefDTO project) {
        this.project = project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ETaskStatus getStatus() {
        return status;
    }

    public void setStatus(ETaskStatus status) {
        this.status = status;
    }

    public UserRefDTO getImplementer() {
        return implementer;
    }

    public void setImplementer(UserRefDTO implementer) {
        this.implementer = implementer;
    }

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
