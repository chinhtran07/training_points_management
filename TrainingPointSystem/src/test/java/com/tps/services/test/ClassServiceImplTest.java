package com.tps.services.test;

import com.tps.pojo.Class;
import com.tps.repositories.ClassRepository;
import com.tps.services.impl.ClassServiceImpl;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassServiceImpl classService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addClass() {
        Class c = new Class();
        doNothing().when(classRepository).addClass(any(Class.class));

        classService.addClass(c);

        verify(classRepository).addClass(c);
    }

    @Test
    void updateClass() {
        Class c = new Class();
        doNothing().when(classRepository).updateClass(any(Class.class));

        classService.updateClass(c);

        verify(classRepository).updateClass(c);
    }

    @Test
    void deleteClass() {
        Class c = new Class();
        doNothing().when(classRepository).deleteClass(any(Class.class));

        classService.deleteClass(c);

        verify(classRepository).deleteClass(c);
    }

    @Test
    void getClassById() {
        Class c = new Class();
        when(classRepository.getClassById(anyInt())).thenReturn(c);

        Class result = classService.getClassById(1);

        assertEquals(c, result);
        verify(classRepository).getClassById(1);
    }

    @Test
    void getAllClass() {
        List<Class> classes = Collections.singletonList(new Class());
        when(classRepository.getAllClass(any(Map.class))).thenReturn(classes);

        List<Class> result = classService.getAllClass(Collections.emptyMap());

        assertEquals(classes, result);
        verify(classRepository).getAllClass(Collections.emptyMap());
    }
}