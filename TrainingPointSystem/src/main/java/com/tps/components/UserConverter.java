package com.tps.components;

import com.tps.dto.UserAssistantDTO;
import com.tps.dto.UserDTO;
import com.tps.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class UserConverter {

    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
//        dto.setDob(user.getDob().toString());
        return dto;
    }

    public User convertDTOToUser(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        return user;
    }

    public UserAssistantDTO toUserAssistantDTO(Object[] o) {
        UserAssistantDTO dto = new UserAssistantDTO();
        dto.setId((Integer) o[0]);
        dto.setUsername(o[1].toString());
        dto.setFirstName(o[2].toString());
        dto.setLastName(o[3].toString());
        dto.setFaculty(o[4].toString());
        dto.setIsActive((Boolean) o[5]);

        return dto;
    }
}
