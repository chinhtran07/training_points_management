package com.tps.services.impl;

import com.tps.repositories.MissingReportRepository;
import com.tps.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissingReportServiceImpl implements MissingReportService {

    @Autowired
    private MissingReportRepository repository;

    @Override
    public List<Object[]> getMissionReportByFaculty(int facultyId) {
        return this.repository.getMissionReportByFaculty(facultyId);
    }
}
