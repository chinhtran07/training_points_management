package com.tps.services.test;

import com.tps.repositories.StatsRepository;
import com.tps.services.impl.StatsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {

    @Mock
    private StatsRepository statsRepository;

    @InjectMocks
    private StatsServiceImpl statsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void statsTrainingPointByFaculty() {
        Map<String, String> params = new HashMap<>();
        when(statsRepository.statsTrainingPointByFaculty(anyMap())).thenReturn(Collections.emptyList());

        // Call the service method
        List<Object[]> result = statsService.statsTrainingPointByFaculty(params);

        // Assert the result
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void statsTrainingPointByRank() {
        Map<String, String> params = new HashMap<>();
        when(statsRepository.statsTrainingPointByRank(anyMap())).thenReturn(Collections.emptyList());

        // Call the service method
        List<Object[]> result = statsService.statsTrainingPointByRank(params);

        // Assert the result
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void statsTrainingPoints() {
        Map<String, String> params = new HashMap<>();
        when(statsRepository.statsTrainingPoint(anyMap())).thenReturn(Collections.emptyList());

        // Call the service method
        List<Object[]> result = statsService.statsTrainingPoints(params);

        // Assert the result
        assertEquals(Collections.emptyList(), result);
    }
}