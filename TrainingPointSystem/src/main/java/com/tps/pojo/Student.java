package com.tps.pojo;

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
@Table(name = "student", schema = "training_point")
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

    @Size(max = 11)
    @NotNull
    @Column(name = "student_id", nullable = false, length = 11)
    private String studentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Class classField;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "student")
    private Set<MissingReport> missingReports = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<RegisterMission> registerMissions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Reaction> reactions = new LinkedHashSet<>();

}