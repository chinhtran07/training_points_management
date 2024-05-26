package com.tps.controllers;


import com.tps.pojo.Student;
import com.tps.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students/")
public class APIStudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/result-training-point")
    public ResponseEntity<List<Object>> getResultOfTrainingPoint(@RequestParam int id) {
        try {
            List<Object> result = this.studentService.getResultOfTrainingPointById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
