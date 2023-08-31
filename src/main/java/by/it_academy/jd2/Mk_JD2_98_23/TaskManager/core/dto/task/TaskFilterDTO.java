package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;

import java.util.List;
import java.util.UUID;

public class TaskFilterDTO {

    private List<UUID> project;

    private List<UUID> implementer;

    private List<ETaskStatus> status;

    public TaskFilterDTO() {
    }

    public TaskFilterDTO(List<UUID> project, List<UUID> implementer, List<ETaskStatus> status) {
        this.project = project;
        this.implementer = implementer;
        this.status = status;
    }

    public List<UUID> getProject() {
        return project;
    }

    public void setProject(List<UUID> project) {
        this.project = project;
    }

    public List<UUID> getImplementer() {
        return implementer;
    }

    public void setImplementer(List<UUID> implementer) {
        this.implementer = implementer;
    }

    public List<ETaskStatus> getStatus() {
        return status;
    }

    public void setStatus(List<ETaskStatus> status) {
        this.status = status;
    }
}
