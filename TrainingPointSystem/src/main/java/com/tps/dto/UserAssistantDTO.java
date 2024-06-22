package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAssistantDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String faculty;
    private Boolean isActive;
    private String avatar;
}
