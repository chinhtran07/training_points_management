package com.tps.repositories;

import java.util.List;

public interface MissingReportRepository {
    List<Object[]> getMissionReportByFaculty(int facultyId);
}
