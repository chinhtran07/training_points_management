package com.tps.controllers;

import com.tps.components.MissingReportConverter;
import com.tps.dto.MissingReportDTO;
import com.tps.dto.MissingReportFacultyDTO;
import com.tps.dto.MissingReportStudentDTO;
import com.tps.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/missing-report")
public class APIMissingReportController {

    @Autowired
    private MissingReportService missingReportService;

    @Autowired
    private MissingReportConverter missingReportConverter;

    @GetMapping(value = "/faculty")
    public ResponseEntity<List<MissingReportFacultyDTO>> getMissingReportByFaculty(@RequestParam String facultyId) {
        if (facultyId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Object[]> result = this.missingReportService.getMissionReportByFaculty(Integer.parseInt(facultyId));
            List<MissingReportFacultyDTO> missingReportDTOList = new ArrayList<>();
            for (Object[] o : result) {
                MissingReportFacultyDTO missingReportDTO = missingReportConverter.toDTOObject(o);
                missingReportDTOList.add(missingReportDTO);
            }
            return new ResponseEntity<>(missingReportDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/student")
    public ResponseEntity<List<MissingReportStudentDTO>> getMissingReportOfStudent(@RequestParam int studentId, @RequestParam int periodId) {
        if (studentId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MissingReportStudentDTO> missingReportStudentDTOS = this.missingReportService.getMissingReportByStudentId(studentId, periodId)
                .stream().map(m -> this.missingReportConverter.toMissingReportStudentDTO(m)).collect(Collectors.toList());

        return new ResponseEntity<>(missingReportStudentDTOS, HttpStatus.OK);
    }
}
