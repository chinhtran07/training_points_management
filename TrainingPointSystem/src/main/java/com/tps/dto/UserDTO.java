package com.tps.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String avatar;
}
