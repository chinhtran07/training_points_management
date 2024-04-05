
import javax.persistence.*;

@Entity
@Table(name = "registermission")
public class Registermission {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Id
    @Column(name = "mission_id")
    private Integer missionId;

    @Column(name = "is_completed")
    private java.lang.Byte isCompleted;

    @Column(name = "is_active")
    private java.lang.Byte isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getMissionId() {
        return this.missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public java.lang.Byte getIsCompleted() {
        return this.isCompleted;
    }

    public void setIsCompleted(java.lang.Byte isCompleted) {
        this.isCompleted = isCompleted;
    }

    public java.lang.Byte getIsActive() {
        return this.isActive;
    }

    public void setIsActive(java.lang.Byte isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
