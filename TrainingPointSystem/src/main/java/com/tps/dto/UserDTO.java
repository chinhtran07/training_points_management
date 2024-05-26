package com.tps.dto;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String dob;
}
