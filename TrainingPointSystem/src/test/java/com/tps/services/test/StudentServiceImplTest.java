package com.tps.services.test;

import com.tps.dto.ActivityResultDTO;
import com.tps.dto.MissionResultDTO;
import com.tps.dto.StudentResultDTO;
import com.tps.pojo.Faculty;
import com.tps.pojo.Student;
import com.tps.pojo.User;
import com.tps.pojo.Class;
import com.tps.repositories.StudentRepository;
import com.tps.services.ClassService;
import com.tps.services.FacultyService;
import com.tps.services.UserService;
import com.tps.services.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ClassService classService;

    @Mock
    private FacultyService facultyService;

    @Mock
    private UserService userService;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Map<String, String> params;
    private MultipartFile[] files;

    @BeforeEach
    void setUp() {
        params = new HashMap<>();
        params.put("firstName", "John");
        params.put("lastName", "Doe");
        params.put("username", "johndoe");
        params.put("password", "password");
        params.put("email", "john.doe@example.com");
        params.put("dob", "2000-01-01");
        params.put("class", "1");
        params.put("faculty", "1");

        files = new MultipartFile[0];
    }

    @Test
    void findStudentByStudentId() {
        String studentId = "2151010040";
        when(studentRepository.findStudentByStudentId(studentId)).thenReturn(new Student());

        Student result = studentService.findStudentByStudentId(studentId);

        assertNotNull(result);
        verify(studentRepository).findStudentByStudentId(studentId);
    }

    @Test
    void getResultOfTrainingPointById() {
        List<Object[]> results = new ArrayList<>();
        Object[] result1 = {1, "Point Group 1", "Content 1", 100, 1, "Activity 1", 50, 1, "Mission 1", 10};
        Object[] result2 = {2, "Point Group 2", "Content 2", 120, 2, "Activity 2", 60, 2, "Mission 2", 20};
        results.add(result1);
        results.add(result2);

        when(studentRepository.getResultOfTrainingPointById(anyInt())).thenReturn(Collections.singletonList(results));

        // Call the service method
        List<Object> studentResultDTOList = studentService.getResultOfTrainingPointById(1);

        // Assertions
        assertEquals(2, studentResultDTOList.size());

        // Assertions for first student result
        StudentResultDTO studentResult1 = (StudentResultDTO) studentResultDTOList.get(0);
        assertEquals(1, studentResult1.getId());
        assertEquals("Point Group 1", studentResult1.getName());
        assertEquals(1, studentResult1.getListActivity().size());

        // Assertions for first activity in first student result
        ActivityResultDTO activityResult1 = studentResult1.getListActivity().get(0);
        assertEquals(1, activityResult1.getActivityId());
        assertEquals("Activity 1", activityResult1.getActivityName());
        assertEquals(1, activityResult1.getMissionResultDTOList().size());

        // Assertions for first mission in first activity
        MissionResultDTO missionResult1 = activityResult1.getMissionResultDTOList().get(0);
        assertEquals(1, missionResult1.getMissionId());
        assertEquals("Mission 1", missionResult1.getMissionName());
        assertEquals(10, missionResult1.getPoint());

        verify(studentRepository).getResultOfTrainingPointById(1);
    }

    @Test
    void addStudent() {
        Class studentClass = new Class();
        when(classService.getClassById(anyInt())).thenReturn(studentClass);

        Faculty faculty = new Faculty();
        when(facultyService.getFacultyById(anyInt())).thenReturn(faculty);

        User user = new User();
        when(userService.addUser(any(User.class))).thenReturn(user);

        // Mock repository behavior
        Student student = new Student();
        student.setId(1);
        when(studentRepository.addStudent(any(Student.class))).thenReturn(student);

        // Call the service method
        User result = studentService.addStudent(params, files);

        // Assertions
        assertNotNull(result);
        assertEquals(user, result);
        assertNotNull(user.getStudent());
        assertEquals(student, user.getStudent());
        assertEquals(studentClass, user.getStudent().getClassField());
        assertEquals(faculty, user.getStudent().getFaculty());

        verify(classService).getClassById(1);
        verify(facultyService).getFacultyById(1);
        verify(userService).addUser(any(User.class));
        verify(studentRepository).addStudent(any(Student.class));
    }
}