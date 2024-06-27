package com.tps.services.test;

import com.tps.pojo.Period;
import com.tps.repositories.PeriodRepository;
import com.tps.services.impl.PeriodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeriodServiceImplTest {

    @Mock
    private PeriodRepository periodRepository;

    @InjectMocks
    private PeriodServiceImpl periodService;

    @Test
    void getYears() {
        List<String> expectedYears = Arrays.asList("2022", "2023", "2024");
        when(periodRepository.getYears()).thenReturn(expectedYears);

        // When
        List<String> actualYears = periodService.getYears();

        // Then
        assertEquals(expectedYears, actualYears);
        verify(periodRepository, times(1)).getYears();
    }

    @Test
    void getPeriod() {
        String year = "2023";
        int semesterId = 1;
        Period expectedPeriod = new Period();
        expectedPeriod.setYear(year);
        when(periodRepository.getPeriod(year, semesterId)).thenReturn(expectedPeriod);

        // When
        Period actualPeriod = periodService.getPeriod(year, semesterId);

        // Then
        assertEquals(expectedPeriod, actualPeriod);
        verify(periodRepository, times(1)).getPeriod(year, semesterId);
    }

    @Test
    void getAllPeriods() {
        String year = "2023";
        List<Period> expectedPeriods = Arrays.asList(new Period(), new Period());
        when(periodRepository.getAllPeriods(year)).thenReturn(expectedPeriods);

        // When
        List<Period> actualPeriods = periodService.getAllPeriods(year);

        // Then
        assertEquals(expectedPeriods, actualPeriods);
        verify(periodRepository, times(1)).getAllPeriods(year);
    }
}