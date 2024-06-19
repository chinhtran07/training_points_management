package com.tps.components;

import com.tps.dto.StudentDTO;
import com.tps.dto.UserAssistantDTO;
import com.tps.dto.UserChat;
import com.tps.dto.UserDTO;
import com.tps.pojo.Assistant;
import com.tps.pojo.Student;
import com.tps.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserConverter {
    @Autowired
    FacultyConverter facultyConverter;

    public UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        dto.setDob(user.getDob().toString());
        dto.setRole(user.getRole());
        if (Objects.equals(user.getRole(), User.ASSISTANT)) {
            dto.setFaculty(facultyConverter.toDTO(user.getAssistant().getFaculty()));
        }
        else if (Objects.equals(user.getRole(), User.STUDENT)) {
            dto.setFaculty(facultyConverter.toDTO(user.getStudent().getFaculty()));
        }
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

    public UserAssistantDTO toUserAssistantDTO(Assistant assistant) {
        UserAssistantDTO dto = new UserAssistantDTO();
        dto.setId(assistant.getId());
        dto.setUsername(assistant.getUser().getUsername());
        dto.setFirstName(assistant.getUser().getFirstName());
        dto.setLastName(assistant.getUser().getLastName());
        dto.setFaculty(assistant.getFaculty().getName());
        dto.setIsActive(assistant.getUser().getIsActive());
        dto.setAvatar(assistant.getUser().getAvatar());
        return dto;
    }

    public StudentDTO toStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setClassName(student.getClassField().getName());
        studentDTO.setUser(this.convertToDTO(student.getUser()));

        return studentDTO;
    }

    public UserChat toUserChat(User user) {
        UserChat userChat = new UserChat();
        userChat.setId(String.valueOf(user.getId()));
        userChat.setName(user.getLastName() + user.getFirstName());

        return userChat;
    }
}
