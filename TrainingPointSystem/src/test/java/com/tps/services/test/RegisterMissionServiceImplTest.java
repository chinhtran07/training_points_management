package com.tps.services.test;

import com.opencsv.exceptions.CsvValidationException;
import com.tps.pojo.Mission;
import com.tps.pojo.RegisterMission;
import com.tps.pojo.Student;
import com.tps.pojo.User;
import com.tps.repositories.RegisterMissionRepository;
import com.tps.services.MissionService;
import com.tps.services.StudentService;
import com.tps.services.UserService;
import com.tps.services.impl.RegisterMissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterMissionServiceImplTest {

    @Mock
    private RegisterMissionRepository registerMissionRepository;

    @Mock
    private UserService userService;

    @Mock
    private StudentService studentService;

    @Mock
    private MissionService missionService;

    @InjectMocks
    private RegisterMissionServiceImpl registerMissionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getRegisterByStudentMission() {
        RegisterMission registerMission = new RegisterMission();
        when(registerMissionRepository.getRegisterByStudentMission(1, 1)).thenReturn(registerMission);

        RegisterMission result = registerMissionService.getRegisterByStudentMission(1, 1);
        assertNotNull(result);
        verify(registerMissionRepository, times(1)).getRegisterByStudentMission(1, 1);
    }

    @Test
    void updateRegistermission() {
        RegisterMission registerMission = new RegisterMission();
        doNothing().when(registerMissionRepository).updateRegistermission(registerMission);

        registerMissionService.updateRegistermission(registerMission);
        verify(registerMissionRepository, times(1)).updateRegistermission(registerMission);
    }

    @Test
    void addRegisterMission() {
        User user = new User();
        Mission mission = new Mission();
        Student student = new Student();
        student.setUser(user);

        RegisterMission registerMission = new RegisterMission();
        when(missionService.getMissionById(1)).thenReturn(mission);
        when(userService.getUserById(1)).thenReturn(user);
        when(registerMissionRepository.addRegisterMission(any(RegisterMission.class))).thenReturn(registerMission);

        RegisterMission result = registerMissionService.addRegisterMission(1, 1);
        assertNotNull(result);
        verify(registerMissionRepository, times(1)).addRegisterMission(any(RegisterMission.class));
    }

    @Test
    void updateRegisterMission() throws IOException, CsvValidationException {
        MultipartFile file = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("S123,1,1\n".getBytes());
        when(file.getInputStream()).thenReturn(inputStream);
        when(missionService.checkMissionBelongToActivity(anyString(), anyString())).thenReturn(true);
        when(studentService.findStudentByStudentId(anyString())).thenReturn(new Student());

        RegisterMission registerMission = new RegisterMission();
        when(registerMissionRepository.getRegisterByStudentMission(1, 1)).thenReturn(registerMission);

        doNothing().when(registerMissionRepository).updateStatus(anyList());

        // Call the method to test
        registerMissionService.updateRegisterMission(file, "1");

        verify(registerMissionRepository, times(1)).updateStatus(anyList());
    }
}