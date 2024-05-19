package com.tps.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String STUDENT = "ROLE_STUDENT";
    public static final String ASSISTANT = "ROLE_ASSISTANT";

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

    @Transient
    private String confirmPassword;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    @JsonIgnore
    private String phone;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Lob
    @Column(name = "gender")
    private String gender;



    @Column(name = "is_active")
    private Boolean isActive;

    @Lob
    @Column(name = "role", nullable = false)
    private String role;

    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<Faculty> faculties = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Student student;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private Assistant assistant;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Post> posts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "assistant")
    @JsonIgnore
    private Set<Activity> activities = new LinkedHashSet<>();

    @Column(name = "dob")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    @JsonIgnore
    public List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(ADMIN));
        authorities.add(new SimpleGrantedAuthority(STUDENT));
        authorities.add(new SimpleGrantedAuthority(ASSISTANT));

        return authorities;
    }
}