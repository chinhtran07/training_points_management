package com.tps.services.impl;

import com.tps.pojo.Semester;
import com.tps.repositories.SemesterRepository;
import com.tps.services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    @Override
    public List<Semester> getSemesters() {
        return this.semesterRepository.getAllSemesters();
    }
}
