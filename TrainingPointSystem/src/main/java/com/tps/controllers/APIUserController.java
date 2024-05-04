package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.pojo.User;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class APIUserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping(path = "/user/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE,
    })
    public ResponseEntity<User> createUser(@RequestBody HashMap<String, String> params,
                                           @RequestPart MultipartFile[] files) {

        User user = new User();
        user.setFirstName(params.get("firstName"));
        user.setLastName(params.get("lastName"));
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setEmail(params.get("email"));
        user.setPhone(params.get("phone"));

        if (files.length > 0) {
            user.setFile(files[0]);
        }

        User createdUser = userService.addUser(user);

        if (createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
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
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authToken) throws ParseException {
        String username = jwtService.getUsernameFormToken(authToken);
        User user = userService.getUserByUsername(username);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PatchMapping(path = "/user/{id}/update")
//    public ResponseEntity<User> updateUser(@PathVariable int id) {
//        User user = userService.updateUser()
//    }

}
