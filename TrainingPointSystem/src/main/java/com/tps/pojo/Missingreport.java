package com.tps.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "missingreport")
public class Missingreport {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Id
    @Column(name = "mission_id")
    private Integer missionId;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Byte isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


}
