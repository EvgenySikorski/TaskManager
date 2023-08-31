package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.dao.entity;

import by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums.ETaskStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name ="task", schema = "app")
public class Task implements Serializable {

    @Id
    private UUID uuid;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "dt_create", precision = 3)
    private LocalDateTime dtCreat;

    @UpdateTimestamp(source = SourceType.DB)
    @Version
    @Column(name = "dt_update", precision = 3)
    private LocalDateTime dtUpdate;

    @ManyToOne
    @JoinColumn(name = "project", nullable = false)
    private Project project;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ETaskStatus status;

    @Column(name = "implementer")
    private UUID implementer;

    public Task() {
    }

    public Task(UUID uuid, LocalDateTime dtCreat, LocalDateTime dtUpdate, Project project,
                String title, String description, ETaskStatus status, UUID implementer) {
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
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

    public UUID getImplementer() {
        return implementer;
    }

    public void setImplementer(UUID implementer) {
        this.implementer = implementer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(uuid, task.uuid) && Objects.equals(dtCreat, task.dtCreat) && Objects.equals(dtUpdate, task.dtUpdate) && Objects.equals(project, task.project) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status && Objects.equals(implementer, task.implementer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreat, dtUpdate, project, title, description, status, implementer);
    }
}
