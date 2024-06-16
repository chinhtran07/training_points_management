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
@Table(name = "period")
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @Size(max = 4)
    @Column(name = "year", length = 4)
    private String year;

    @OneToMany(mappedBy = "period")
    private Set<Activity> activities = new LinkedHashSet<>();

    @NotNull
    @Column(name = "isActive", nullable = false)
    private Boolean isActive = false;

}