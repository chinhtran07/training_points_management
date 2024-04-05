
import javax.persistence.*;

@Entity
@Table(name = "like")
public class Like {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "post_id")
    private Integer postId;

    @Column(name = "is_active")
    private java.lang.Byte isActive;

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

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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
