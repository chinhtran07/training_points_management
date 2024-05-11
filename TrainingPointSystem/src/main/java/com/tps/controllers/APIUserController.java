package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.components.UserConverter;
import com.tps.dto.UserDTO;
import com.tps.dto.UserRegisterDTO;
import com.tps.pojo.User;
import com.tps.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping(path = "/api")
public class APIUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserConverter userConverter;

    @PostMapping(path = "/user/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
    })
    public ResponseEntity<UserDTO> createUser(@RequestBody HashMap<String, String> params) {

        User user = new User();
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setEmail(params.get("email"));

//        if (files.length > 0) {
//            user.setFile(files[0]);
//        }

        User createdUser = userService.addUser(user);

        if (createdUser != null) {
            UserDTO userDTO = this.userConverter.convertToDTO(createdUser);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
    })
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();
        if (userService.authUser(user.getUsername(), user.getPassword())) {
            String token = jwtService.generateTokenLogin(user.getUsername());
            response.put("token", token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("error", "Unauthorized");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(path = "/user/current")
    public ResponseEntity<UserDTO> getCurrentUser(@RequestHeader("Authorization") String authToken) throws ParseException {
        String username = jwtService.getUsernameFormToken(authToken);
        User user = userService.getUserByUsername(username);

        if(user != null) {
            UserDTO userDTO = this.userConverter.convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id ) {
        User user = userService.findById(id);
        if(user != null) {
            UserDTO userDTO = this.userConverter.convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
