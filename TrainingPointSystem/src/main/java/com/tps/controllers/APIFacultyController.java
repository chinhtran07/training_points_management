package com.tps.controllers;

import com.google.api.Http;
import com.tps.components.FacultyConverter;
import com.tps.dto.ClassDTO;
import com.tps.dto.FacultyDTO;
import com.tps.pojo.Faculty;
import com.tps.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{facultyId}/classes")
    public ResponseEntity<List<ClassDTO>> getFacultyClasses(@PathVariable int facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        if (faculty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ClassDTO> classDTOS = this.facultyService.getFacultyClasses(facultyId).stream().map(clazz -> {
            ClassDTO classDTO = new ClassDTO();
            classDTO.setId(clazz.getId());
            classDTO.setName(clazz.getName());
            return classDTO;
        }).collect(Collectors.toList());

        return new ResponseEntity<>(classDTOS, HttpStatus.OK);
    }
}
