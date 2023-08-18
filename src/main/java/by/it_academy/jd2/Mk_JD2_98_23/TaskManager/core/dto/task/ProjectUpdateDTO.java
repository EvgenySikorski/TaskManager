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
    private LocalDateTime dtUpdate;

    public ProjectUpdateDTO() {
    }

    public ProjectUpdateDTO(UUID uuid, String name, String description,
                            UserRefDTO manager, Set<UserRefDTO> staff,
                            EProjectStatus status, LocalDateTime dtUpdate) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.manager = manager;
        this.staff = staff;
        this.status = status;
        this.dtUpdate = dtUpdate;
    }
}
