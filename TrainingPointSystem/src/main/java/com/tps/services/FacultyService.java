package com.tps.services;

import com.tps.pojo.Faculty;

import java.util.List;
import java.util.Map;

public interface FacultyService {
    void addFaculty(Faculty faculty);
    void updateFaculty(Faculty faculty);
    void deleteFaculty(Faculty faculty);
    Faculty getFacultyById(int id);
    List<Faculty> getAllFaculty(Map<String,String> params);
}
