package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class TaskDTO {

    private UUID uuid;
    @JsonProperty ("dt_create")
    private Long dtCreat;
    @JsonProperty ("dt_update")
    private Long dtUpdate;
    private ProjectRefDTO project;
    private String title;
    private String description;
    private ETaskStatus status;
    private UserRefDTO implementer;

    public TaskDTO() {
    }

    public TaskDTO(UUID uuid, Long dtCreat, Long dtUpdate, ProjectRefDTO project,
                   String title, String description, ETaskStatus status, UserRefDTO implementer) {
        this.uuid = uuid;
        this.dtCreat = dtCreat;
        this.dtUpdate = dtUpdate;
        this.project = project;
        this.title = title;
        this.description = description;
        this.status = status;
        this.implementer = implementer;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long getDtCreat() {
        return dtCreat;
    }

    public void setDtCreat(Long dtCreat) {
        this.dtCreat = dtCreat;
    }

    public Long getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Long dtUpdate) {
        this.dtUpdate = dtUpdate;
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
}

