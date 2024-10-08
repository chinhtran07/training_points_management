package com.tps.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "training_point")
public class User implements Serializable {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String ASSISTANT = "ROLE_ASSISTANT";
    public static final String STUDENT = "ROLE_STUDENT";
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Size(max = 100)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 50)
    @NotNull
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Lob
    @Column(name = "role")
    private String role;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar = "https://res.cloudinary.com/dbd7vfk12/image/upload/v1716969657/samples/default-avatar_kyi5ml.webp";

    @Lob
    @Column(name = "gender")
    private String gender;

    @Column(name = "dob")
    @JsonIgnore
    private LocalDate dob;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @ManyToMany
    @JoinTable(name = "assistant",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "faculty_id"))
    private Set<Faculty> faculties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> posts = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Assistant assistant;

    @Transient
    private String confirmPassword;

    @OneToMany(mappedBy = "assistant")
    private Set<Activity> activities = new LinkedHashSet<>();


    @OneToMany(mappedBy = "user")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Reaction> reactions = new LinkedHashSet<>();

    @Transient
    private MultipartFile file;

    public Set<GrantedAuthority> getGrantedAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(this.getRole()));

        return authorities;
    }

}