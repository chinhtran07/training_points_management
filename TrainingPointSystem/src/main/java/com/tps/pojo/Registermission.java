package com.tps.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "registermission")
public class Registermission implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_date")
    @CreationTimestamp
    private Instant createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Instant updatedDate;

}