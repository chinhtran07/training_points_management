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
    private String email;
    private String phone;
    private String gender;
    private Date dob;
    private String faculty;
    private Boolean isActive;

    public static UserAssistantDTO toDto(Object[] assistantInfo) {
        UserAssistantDTO dto = new UserAssistantDTO();
        try {
            dto.setId((Integer) assistantInfo[0]);
            dto.setUsername((String) assistantInfo[1]);
            dto.setFirstName((String) assistantInfo[2]);
            dto.setLastName((String) assistantInfo[3]);
            dto.setEmail((String) assistantInfo[4]);
            dto.setPhone((String) assistantInfo[5]);
            dto.setGender((String) assistantInfo[6]);
            dto.setDob((Date) assistantInfo[7]);
            dto.setFaculty((String) assistantInfo[8]);
            dto.setIsActive((Boolean) assistantInfo[9]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dto;
    }

    public static List<UserAssistantDTO> toDtoList(List<Object[]> assistantInfo) {
        List<UserAssistantDTO> dtoList = new ArrayList<UserAssistantDTO>();
        assistantInfo.forEach(info -> dtoList.add(toDto(info)));
        return dtoList;
    }
}
