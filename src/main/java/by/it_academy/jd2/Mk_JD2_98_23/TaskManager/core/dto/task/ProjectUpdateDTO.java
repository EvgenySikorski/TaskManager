package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class ProjectUpdateDTO {

    private UUID uuid;
    private String name;
    private String description;
    private UserRefDTO manager;
    private Set<UserRefDTO> staff;
    private EProjectStatus status;
    @JsonProperty ("dt_update")
    private LocalDateTime dateUpdate;

    public ProjectUpdateDTO() {
    }

    public ProjectUpdateDTO(UUID uuid, String name, String description,
                            UserRefDTO manager, Set<UserRefDTO> staff,
                            EProjectStatus status, LocalDateTime dateUpdate) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
        this.dateUpdate = dateUpdate;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public LocalDateTime getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(LocalDateTime dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
