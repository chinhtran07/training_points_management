package com.tps.pojo;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "assistant")
public class Assistant {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_active")
    private Byte isActive;

    @Column(name = "faculty_id")
    private Integer facultyId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;
}
