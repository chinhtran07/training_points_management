package com.tps.services.impl;

import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.pojo.User;
import com.tps.repositories.AssistantRepository;
import com.tps.repositories.FacultyRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.AssistantService;
import com.tps.services.FacultyService;
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
    private UserRepository userRepository;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addAssistant(Assistant assistant) {
        User user = new User();
        user.setUsername(assistant.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(assistant.getUser().getPassword()));
        user.setEmail(assistant.getUser().getEmail());
        user.setDob(assistant.getUser().getDob());
        user.setFirstName(assistant.getUser().getFirstName());
        user.setLastName(assistant.getUser().getLastName());
        user.setGender(assistant.getUser().getGender());
        user.setAvatar(assistant.getUser().getAvatar());
        user.setRole(User.ASSISTANT);
        user.setPhone(assistant.getUser().getPhone());
        user.setIsActive(assistant.getUser().getIsActive());

        this.userRepository.addUser(user);
        assistant.setUser(this.userRepository.getUserByUsername(user.getUsername()));

        Faculty faculty = this.facultyService.getFacultyById(assistant.getFaculty().getId());
        assistant.setFaculty(faculty);

        this.assistantRepository.addAssistant(assistant);
    }

    @Override
    public void updateAssistant(Assistant assistant) {
        assistant.getUser().setRole(assistant.getUser().getRole());
        User user = this.userRepository.getUserByUsername(assistant.getUser().getUsername());
        if (passwordEncoder.matches(assistant.getUser().getPassword(), user.getPassword())) {
            assistant.getUser().setPassword(passwordEncoder.encode(assistant.getUser().getPassword()));
        }
        this.userRepository.updateUser(assistant.getUser());
        Faculty faculty = this.facultyService.getFacultyById(assistant.getFaculty().getId());
        assistant.setFaculty(faculty);
        this.assistantRepository.updateAssistant(assistant);
    }

    @Override
    public void deleteAssistant(Assistant assistant) {
        this.assistantRepository.deleteAssistant(assistant);
    }

    @Override
    public List<Assistant> getAllAssistants(Map<String, String> params) {
        return this.assistantRepository.getAllAssistants(params);
    }

    @Override
    public Assistant getAssistantById(int id) {
        return this.assistantRepository.getAssistantById(id);
    }

    @Override
    public List<Object[]> getUserAssistants(Map<String, String> params) {
        return this.assistantRepository.getUserAssistants(params);
    }
}
