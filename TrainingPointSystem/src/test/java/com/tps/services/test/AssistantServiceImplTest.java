package com.tps.services.test;

import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.pojo.User;
import com.tps.repositories.AssistantRepository;
import com.tps.services.FacultyService;
import com.tps.services.UserService;
import com.tps.services.impl.AssistantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssistantServiceImplTest {
    @Mock
    private AssistantRepository assistantRepository;

    @Mock
    private UserService userService;

    @Mock
    private FacultyService facultyService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AssistantServiceImpl assistantService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addAssistant() {
        Assistant assistant = new Assistant();
        User user = new User();
        assistant.setUser(user);
        Faculty faculty = new Faculty();
        faculty.setId(1);
        assistant.setFaculty(faculty);

        when(userService.addUser(any(User.class))).thenReturn(user);
        when(facultyService.getFacultyById(anyInt())).thenReturn(faculty);
        doNothing().when(assistantRepository).addAssistant(any(Assistant.class));

        assistantService.addAssistant(assistant);

        verify(userService).addUser(user);
        verify(facultyService).getFacultyById(faculty.getId());
        verify(assistantRepository).addAssistant(assistant);
        assertEquals(User.ASSISTANT, user.getRole());
    }

    @Test
    void updateAssistant() {
        Assistant assistant = new Assistant();
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        assistant.setUser(user);
        Faculty faculty = new Faculty();
        faculty.setId(1);
        assistant.setFaculty(faculty);

        when(userService.getUserByUsername(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");
        when(facultyService.getFacultyById(anyInt())).thenReturn(faculty);
        doNothing().when(userService).updateUser(any(User.class));
        doNothing().when(assistantRepository).updateAssistant(any(Assistant.class));

        assistantService.updateAssistant(assistant);

        verify(userService).getUserByUsername(user.getUsername());
        verify(passwordEncoder).matches(anyString(), anyString());
        verify(passwordEncoder).encode(anyString());
        verify(userService).updateUser(user);
        verify(assistantRepository).updateAssistant(assistant);
    }

    @Test
    void getAssistantById() {
        Assistant assistant = new Assistant();
        when(assistantRepository.getAssistantById(anyInt())).thenReturn(assistant);

        Assistant result = assistantService.getAssistantById(1);

        assertEquals(assistant, result);
        verify(assistantRepository).getAssistantById(1);
    }

    @Test
    void getUserAssistants() {
        List<Object[]> userAssistants = Collections.singletonList(new Object[]{});
        when(assistantRepository.getUserAssistants(any(Map.class))).thenReturn(userAssistants);

        List<Object[]> result = assistantService.getUserAssistants(Collections.emptyMap());

        assertEquals(userAssistants, result);
        verify(assistantRepository).getUserAssistants(Collections.emptyMap());
    }

    @Test
    void getAllAssistant() {
        List<Assistant> assistants = Collections.singletonList(new Assistant());
        when(assistantRepository.getAllAssistants(any())).thenReturn(assistants);

        List<Assistant> result = assistantService.getAllAssistant();

        assertEquals(assistants, result);
        verify(assistantRepository).getAllAssistants(null);
    }
}