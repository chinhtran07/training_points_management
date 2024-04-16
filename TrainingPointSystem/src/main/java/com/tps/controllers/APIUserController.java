package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.pojo.User;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api")
public class APIUserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping(path = "/user/register")
    @CrossOrigin
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());

        User createdUser = userService.addUser(params);

        if (createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        if (userService.authUser(user.getUsername(), user.getPassword())) {
            String token = jwtService.generateTokenLogin(user.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return new ResponseEntity<>("Fail", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/user/current")
    @CrossOrigin
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authToken) throws ParseException {
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

//        String authToken = authorizationHeader.substring(7); // Bỏ "Bearer " ở đầu
        String username = jwtService.getUsernameFormToken(authToken);
        User user = userService.getUserByUsername(username);

        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id ) {
        User user = userService.findById(id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @PatchMapping(path = "/user/{id}/update")
//    public ResponseEntity<User> updateUser(@PathVariable int id) {
//        User user = userService.updateUser()
//    }

}
