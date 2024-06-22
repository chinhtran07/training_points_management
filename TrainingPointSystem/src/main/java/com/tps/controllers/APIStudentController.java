package com.tps.controllers;


import com.tps.components.UserConverter;
import com.tps.dto.StudentDTO;
import com.tps.pojo.Student;
import com.tps.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/")
public class APIStudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/result-training-point")
    public ResponseEntity<List<Object>> getResultOfTrainingPoint(@RequestParam int id) {
        List<Object> result = this.studentService.getResultOfTrainingPointById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
//        try {
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentByStudentId(@PathVariable String studentId) {
        Student student = studentService.findStudentByStudentId(studentId);
        if (student == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(userConverter.toStudentDTO(student), HttpStatus.OK);
    }
}
