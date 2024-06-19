package com.tps.controllers;

import com.tps.components.FacultyConverter;
import com.tps.dto.FacultyDTO;
import com.tps.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RequestMapping("/api/faculties")
public class APIFacultyController {
    @Autowired
    FacultyService facultyService;

    @Autowired
    FacultyConverter facultyConverter;

    @GetMapping("")
    public ResponseEntity<List<FacultyDTO>> getAllFaculty(@RequestParam Map<String, String> params) {
        List<FacultyDTO> faculties = facultyService.getAllFaculty(params).stream().map(f -> facultyConverter.toDTO(f)).collect(Collectors.toList());
        return new ResponseEntity<>(faculties, HttpStatus.OK);
    }
}
