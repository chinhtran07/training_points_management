package com.tps.controllers;

import com.tps.components.FacultyConverter;
import com.tps.dto.FacultyDTO;
import com.tps.pojo.User;
import com.tps.services.AssistantService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assistants")
public class APIAssistantController {

    @Autowired
    AssistantService assistantService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyConverter facultyConverter;

    @GetMapping("/faculties")
    public ResponseEntity<FacultyDTO> getFaculty(Principal principal) {
        User user = this.userService.getUserByUsername(principal.getName());

        return new ResponseEntity<>(facultyConverter.toDTO(user.getAssistant().getFaculty()), HttpStatus.OK);
    }
}
