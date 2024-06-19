package com.tps.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String studentId;
    private String className;
    private UserDTO user;
}
