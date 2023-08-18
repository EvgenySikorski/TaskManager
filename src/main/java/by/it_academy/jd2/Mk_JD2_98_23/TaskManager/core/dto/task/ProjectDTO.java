package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.UUID;

public class ProjectDTO {

    private UUID uuid;
    @JsonProperty ("dt_create")
    private Long dtCreat;
    @JsonProperty ("dt_update")
    private Long dtUpdate;
    private String name;
    private String description;
    private UserRefDTO manager;
    private Set<UserRefDTO> staff;
    private EProjectStatus status;

    public ProjectDTO() {
    }

    public ProjectDTO(UUID uuid, Long dtCreat, Long dtUpdate, String name,
                      String description, UserRefDTO manager, Set<UserRefDTO> staff,
                      EProjectStatus status) {
        this.uuid = uuid;
        this.dtCreat = dtCreat;
        this.dtUpdate = dtUpdate;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
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
