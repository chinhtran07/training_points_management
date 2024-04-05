
import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "is_student")
    private java.lang.Byte isStudent;

    @Column(name = "is_assistant")
    private java.lang.Byte isAssistant;

    @Column(name = "is_superuser")
    private java.lang.Byte isSuperuser;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    private LocalDate dob;

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

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public java.lang.Byte getIsStudent() {
        return this.isStudent;
    }

    public void setIsStudent(java.lang.Byte isStudent) {
        this.isStudent = isStudent;
    }

    public java.lang.Byte getIsAssistant() {
        return this.isAssistant;
    }

    public void setIsAssistant(java.lang.Byte isAssistant) {
        this.isAssistant = isAssistant;
    }

    public java.lang.Byte getIsSuperuser() {
        return this.isSuperuser;
    }

    public void setIsSuperuser(java.lang.Byte isSuperuser) {
        this.isSuperuser = isSuperuser;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
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
