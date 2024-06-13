package com.tps.services.impl;


import com.tps.pojo.Faculty;
import com.tps.repositories.FacultyRepository;
import com.tps.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public void addFaculty(Faculty faculty) {
        this.facultyRepository.addFaculty(faculty);
    }

    @Override
    public void updateFaculty(Faculty faculty) {
        this.facultyRepository.updateFaculty(faculty);
    }

    @Override
    public void deleteFaculty(Faculty faculty) {
        this.facultyRepository.deleteFaculty(faculty);
    }

    @Override
    public Faculty getFacultyById(int id) {
        return this.facultyRepository.getFacultyById(id);
    }

    @Override
    public List<Faculty> getAllFaculty(Map<String, String> params) {
        return this.facultyRepository.getAllFaculty(params);
    }
}
