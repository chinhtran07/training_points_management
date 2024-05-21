package com.tps.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tps.dto.StudentTotalPointsDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pointgroup")
public class Pointgroup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "content")
    private String content;

    @Max(value = 20)
    @Column(name = "max_point")
    private Integer maxPoint;

    @OneToMany(mappedBy = "pointgroup")
    @JsonIgnore
    private Set<Activity> activities = new LinkedHashSet<>();

}