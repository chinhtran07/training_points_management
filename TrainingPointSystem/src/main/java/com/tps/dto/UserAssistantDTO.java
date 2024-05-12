package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAssistantDTO implements Serializable {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String faculty;
}
