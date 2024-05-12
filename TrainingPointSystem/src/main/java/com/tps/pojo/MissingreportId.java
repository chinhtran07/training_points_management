package com.tps.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class MissingreportId implements java.io.Serializable {
    private static final long serialVersionUID = -3211180921008221797L;
    @NotNull
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @NotNull
    @Column(name = "mission_id", nullable = false)
    private Integer missionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MissingreportId entity = (MissingreportId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.missionId, entity.missionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, missionId);
    }

}