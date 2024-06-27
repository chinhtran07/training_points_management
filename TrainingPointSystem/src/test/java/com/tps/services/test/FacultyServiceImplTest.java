package com.tps.services.test;

import com.tps.pojo.Faculty;
import com.tps.repositories.FacultyRepository;
import com.tps.services.impl.FacultyServiceImpl;
import com.tps.pojo.Class;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void addFaculty() {
        Faculty faculty = new Faculty();
        doNothing().when(facultyRepository).addFaculty(any(Faculty.class));

        facultyService.addFaculty(faculty);

        verify(facultyRepository).addFaculty(faculty);
    }

    @Test
    void updateFaculty() {
        Faculty faculty = new Faculty();
        doNothing().when(facultyRepository).updateFaculty(any(Faculty.class));

        facultyService.updateFaculty(faculty);

        verify(facultyRepository).updateFaculty(faculty);
    }

    @Test
    void deleteFaculty() {
        Faculty faculty = new Faculty();
        doNothing().when(facultyRepository).deleteFaculty(any(Faculty.class));

        facultyService.deleteFaculty(faculty);

        verify(facultyRepository).deleteFaculty(faculty);
    }

    @Test
    void getFacultyById() {
        Faculty faculty = new Faculty();
        when(facultyRepository.getFacultyById(anyInt())).thenReturn(faculty);

        Faculty result = facultyService.getFacultyById(1);

        assertEquals(faculty, result);
        verify(facultyRepository).getFacultyById(1);
    }

    @Test
    void getAllFaculty() {
        List<Faculty> faculties = Collections.singletonList(new Faculty());
        when(facultyRepository.getAllFaculty(any(Map.class))).thenReturn(faculties);

        List<Faculty> result = facultyService.getAllFaculty(Collections.emptyMap());

        assertEquals(faculties, result);
        verify(facultyRepository).getAllFaculty(Collections.emptyMap());
    }

    @Test
    void getFacultyClasses() {
        int facultyId = 1;
        List<Class> classes = Collections.singletonList(new Class());
        when(facultyRepository.getFacultyClasses(anyInt())).thenReturn(classes);

        List<Class> result = facultyService.getFacultyClasses(facultyId);

        assertEquals(classes, result);
        verify(facultyRepository).getFacultyClasses(facultyId);
    }
}