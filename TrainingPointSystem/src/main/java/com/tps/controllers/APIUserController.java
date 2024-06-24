package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.components.UserConverter;
import com.tps.dto.UserDTO;
import com.tps.pojo.User;
import com.tps.services.StudentService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class APIUserController {

    @Autowired
    StudentService studentService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping(path = "/user/register", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<UserDTO> createUser(@RequestParam HashMap<String, String> params,
                                              @RequestParam MultipartFile[] files) {

        User createdUser = studentService.addStudent(params, files);

        if (createdUser != null) {
            UserDTO userDTO = userConverter.convertToDTO(createdUser);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> params) {
        Map<String, String> response = new HashMap<>();
        String username = params.get("username");
        String password = params.get("password");
        if (userService.authUser(username, password)) {
            String token = jwtService.generateTokenLogin(username);
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("error", "Unauthorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/user/current")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) throws ParseException {
        User user = userService.getUserByUsername(principal.getName());

        if (user != null) {
            UserDTO userDTO = this.userConverter.convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        User user = userService.findById(id);
        if (user != null) {
            UserDTO userDTO = this.userConverter.convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
