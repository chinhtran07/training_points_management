package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.dto.UserDTO;
import com.tps.dto.UserRegisterDTO;
import com.tps.pojo.User;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin()
@RestController
@RequestMapping(path = "/api")
public class APIUserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @PostMapping(path = "/user/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegisterDTO user) throws ParseException {
        Map<String, String> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("email", user.getEmail());
        params.put("phoneNumber", user.getPhoneNumber());

        User createdUser = userService.addUser(params);

        if (createdUser != null) {
            UserDTO userDTO = convertToDTO(createdUser);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
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
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }

//        String authToken = authorizationHeader.substring(7); // Bỏ "Bearer " ở đầu
        String username = jwtService.getUsernameFormToken(authToken);
        User user = userService.getUserByUsername(username);

        if(user != null) {
            UserDTO userDTO = convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id ) {
        User user = userService.findById(id);
        if(user != null) {
            UserDTO userDTO = convertToDTO(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PatchMapping(path = "/user/{id}/update")
//    public ResponseEntity<User> updateUser(@PathVariable int id) {
//        User user = userService.updateUser()
//    }
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    private User convertDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

}
