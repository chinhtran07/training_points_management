package com.tps.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
//    private String phoneNumber;
    private FacultyDTO faculty;
    private String avatar;
    private String dob;
    private String role;
}
