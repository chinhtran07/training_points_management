
import javax.persistence.*;

@Entity
@Table(name = "assistant")
public class Assistant {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_active")
    private java.lang.Byte isActive;

    @Column(name = "faculty_id")
    private Integer facultyId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.lang.Byte getIsActive() {
        return this.isActive;
    }

    public void setIsActive(java.lang.Byte isActive) {
        this.isActive = isActive;
    }

    public Integer getFacultyId() {
        return this.facultyId;
    }

    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
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
