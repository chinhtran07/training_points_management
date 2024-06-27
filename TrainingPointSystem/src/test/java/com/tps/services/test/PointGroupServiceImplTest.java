package com.tps.services.test;

import com.tps.pojo.PointGroup;
import com.tps.repositories.PointGroupRepository;
import com.tps.services.impl.PointGroupServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PointGroupServiceImplTest {

    @Mock
    private PointGroupRepository pointGroupRepository;

    @InjectMocks
    private PointGroupServiceImpl pointGroupService;

    @Test
    void addOrUpdate() {
        PointGroup pointGroup = new PointGroup();

        // When
        pointGroupService.addOrUpdate(pointGroup);

        // Then
        verify(pointGroupRepository, times(1)).addOrUpdate(pointGroup);
    }

    @Test
    void delete() {
        PointGroup pointGroup = new PointGroup();

        // When
        pointGroupService.delete(pointGroup);

        // Then
        verify(pointGroupRepository, times(1)).delete(pointGroup);
    }

    @Test
    void getPointGroup() {
        int id = 1;
        PointGroup expectedPointGroup = new PointGroup();
        when(pointGroupRepository.getPointGroup(id)).thenReturn(expectedPointGroup);

        // When
        PointGroup actualPointGroup = pointGroupService.getPointGroup(id);

        // Then
        assertEquals(expectedPointGroup, actualPointGroup);
        verify(pointGroupRepository, times(1)).getPointGroup(id);
    }

    @Test
    void getAllPointGroups() {
        int id = 1;
        PointGroup expectedPointGroup = new PointGroup();
        when(pointGroupRepository.getPointGroup(id)).thenReturn(expectedPointGroup);

        // When
        PointGroup actualPointGroup = pointGroupService.getPointGroup(id);

        // Then
        assertEquals(expectedPointGroup, actualPointGroup);
        verify(pointGroupRepository, times(1)).getPointGroup(id);
    }
}