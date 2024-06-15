package com.tps.services.impl;

import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.pojo.User;
import com.tps.repositories.AssistantRepository;
import com.tps.repositories.FacultyRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.AssistantService;
import com.tps.services.FacultyService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AssistantServiceImpl implements AssistantService {

    @Autowired
    private AssistantRepository assistantRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addAssistant(Assistant assistant) {
        assistant.getUser().setRole(User.ASSISTANT);

        User u = this.userService.addUser(assistant.getUser());
        assistant.setUser(u);

        Faculty faculty = this.facultyService.getFacultyById(assistant.getFaculty().getId());
        assistant.setFaculty(faculty);

        this.assistantRepository.addAssistant(assistant);
    }

    @Override
    public void updateAssistant(Assistant assistant) {
        User user = this.userService.getUserByUsername(assistant.getUser().getUsername());
        if (passwordEncoder.matches(assistant.getUser().getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(assistant.getUser().getPassword()));
        }

        user.setUsername(assistant.getUser().getUsername());
        user.setEmail(assistant.getUser().getEmail());
        user.setPhone(assistant.getUser().getPhone());
        user.setFirstName(assistant.getUser().getFirstName());
        user.setLastName(assistant.getUser().getLastName());
        user.setGender(assistant.getUser().getGender());
        user.setDob(assistant.getUser().getDob());
        if (assistant.getUser().getFile() != null) {
            user.setFile(assistant.getUser().getFile());
        }

        this.userService.updateUser(user);
        Faculty faculty = this.facultyService.getFacultyById(assistant.getFaculty().getId());
        assistant.setFaculty(faculty);
        assistant.setUser(user);
        this.assistantRepository.updateAssistant(assistant);
    }

//    @Override
//    public void deleteAssistant(Assistant assistant) {
//        this.assistantRepository.deleteAssistant(assistant);
//    }

//    @Override
//    public List<Assistant> getAllAssistants(Map<String, String> params) {
//        return this.assistantRepository.getAllAssistants(params);
//    }

    @Override
    public Assistant getAssistantById(int id) {
        return this.assistantRepository.getAssistantById(id);
    }

    @Override
    public List<Object[]> getUserAssistants(Map<String, String> params) {
        return this.assistantRepository.getUserAssistants(params);
    }
}
