package com.tps.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "registermission")
public class Registermission {
    @Id
    @Column(name = "student_id")
    private Integer studentId;

    @Id
    @Column(name = "mission_id")
    private Integer missionId;

    @Column(name = "is_completed")
    private Byte isCompleted;

    @Column(name = "is_active")
    private Byte isActive;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;


}
