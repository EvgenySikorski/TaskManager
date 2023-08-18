package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.dto.task.UserRefDTO;
import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.EProjectStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "project", schema = "app")
public class Project implements Serializable {
    @Id
    private UUID uuid;
    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "dt_create", precision = 3)
    private LocalDateTime dtCreat;
    @UpdateTimestamp(source = SourceType.DB)
    @Version
    @Column(name = "dt_update", precision = 3)
    private LocalDateTime dtUpdate;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "manager")
    private UUID manager;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_user", schema = "app", joinColumns = @JoinColumn(name = "uuid_project"))
    @Column(name = "uuid_user")
    private Set<UUID> staff;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EProjectStatus status;

    public Project() {
    }

    public Project(UUID uuid, LocalDateTime dtCreat, LocalDateTime dtUpdate,
                   String name, String description, UUID manager,
                   Set<UUID> staff, EProjectStatus status) {
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

    public LocalDateTime getDtCreat() {
        return dtCreat;
    }

    public void setDtCreat(LocalDateTime dtCreat) {
        this.dtCreat = dtCreat;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
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

    public UUID getManager() {
        return manager;
    }

    public void setManager(UUID manager) {
        this.manager = manager;
    }

    public Set<UUID> getStaff() {
        return staff;
    }

    public void setStaff(Set<UUID> staff) {
        this.staff = staff;
    }

    public EProjectStatus getStatus() {
        return status;
    }

    public void setStatus(EProjectStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(uuid, project.uuid) && Objects.equals(dtCreat, project.dtCreat) && Objects.equals(dtUpdate, project.dtUpdate) && Objects.equals(name, project.name) && Objects.equals(description, project.description) && Objects.equals(manager, project.manager) && Objects.equals(staff, project.staff) && status == project.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreat, dtUpdate, name, description, manager, staff, status);
    }
}
