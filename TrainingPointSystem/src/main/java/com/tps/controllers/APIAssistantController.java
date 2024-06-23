package com.tps.controllers;

import com.tps.components.FacultyConverter;
import com.tps.components.UserConverter;
import com.tps.dto.FacultyDTO;
import com.tps.dto.UserAssistantDTO;
import com.tps.dto.UserDTO;
import com.tps.pojo.Assistant;
import com.tps.pojo.User;
import com.tps.services.AssistantService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assistants")
public class APIAssistantController {

    @Autowired
    AssistantService assistantService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyConverter facultyConverter;

    @Autowired
    private UserConverter userConverter;

    @GetMapping("/faculties")
    public ResponseEntity<FacultyDTO> getFaculty(Principal principal) {
        User user = this.userService.getUserByUsername(principal.getName());

        return new ResponseEntity<>(facultyConverter.toDTO(user.getAssistant().getFaculty()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserAssistantDTO>> getAllAssistant() {
        List<UserAssistantDTO> assistants = this.assistantService.getAllAssistant().stream().map(assistant -> userConverter.toUserAssistantDTO(assistant)).collect(Collectors.toList());
        return new ResponseEntity<>(assistants, HttpStatus.OK);
    }
}
