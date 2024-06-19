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

    @Basic
    @Size(max = 4)
    @Column(name = "year", length = 4, nullable = true)
    private String year;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "period")
    private Set<Activity> activities = new LinkedHashSet<>();

    @Basic
    @NotNull
    @Column(name = "is_active", nullable = true)
    private Boolean isActive = false;

    @Override
    public String toString() {
        return String.format("%s", "Học kì" + this.getSemester().getId() + " - " + this.getYear());
    }
}