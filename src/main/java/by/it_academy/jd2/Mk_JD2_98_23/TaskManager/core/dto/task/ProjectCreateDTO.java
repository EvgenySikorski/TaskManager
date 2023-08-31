package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;

import java.util.Set;

public class ProjectCreateDTO {

    private String name;
    private String description;
    private UserRefDTO manager;
    private Set<UserRefDTO> staff;
    private EProjectStatus status;

    public ProjectCreateDTO() {
    }

    public ProjectCreateDTO(String name, String description, UserRefDTO manager,
                            Set<UserRefDTO> staff, EProjectStatus status) {
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserRefDTO getManager() {
        return manager;
    }

    public void setManager(UserRefDTO manager) {
        this.manager = manager;
    }

    public Set<UserRefDTO> getStaff() {
        return staff;
    }

    public void setStaff(Set<UserRefDTO> staff) {
        this.staff = staff;
    }

    public EProjectStatus getStatus() {
        return status;
    }

    public void setStatus(EProjectStatus status) {
        this.status = status;
    }
}
