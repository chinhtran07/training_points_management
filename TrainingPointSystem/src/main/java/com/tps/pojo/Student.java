package com.tps.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student", uniqueConstraints = {@UniqueConstraint(columnNames = {"student_id"})})
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Size(max = 50)
    @NotNull
    @Column(name = "student_id", nullable = false, length = 50)
    private String studentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnore
    private Class classField;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    @JsonIgnore
    private Faculty faculty;

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<Like> likes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<Missingreport> missingreports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private Set<Registermission> registermissions = new LinkedHashSet<>();

}