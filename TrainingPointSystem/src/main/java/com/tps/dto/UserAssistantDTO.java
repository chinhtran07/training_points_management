package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
