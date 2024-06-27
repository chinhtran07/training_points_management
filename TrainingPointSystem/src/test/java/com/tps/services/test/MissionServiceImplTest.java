package com.tps.services.test;

import com.tps.components.MissionConverter;
import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Mission;
import com.tps.pojo.RegisterMission;
import com.tps.repositories.ActivityRepository;
import com.tps.repositories.MissionRepository;
import com.tps.services.impl.MissionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MissionServiceImplTest {

    @Mock
    private MissionRepository missionRepository;

    @Mock
    private MissionConverter missionConverter;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private MissionServiceImpl missionService;

//    @Test
//    void getMission() {
//        Map<String, String> params = new HashMap<>();
//        List<Mission> expectedMissions = new ArrayList<>();
//
//        // Mock the behavior
//        when(missionRepository.getMission(params)).thenReturn(expectedMissions);
//
//        // When
//        List<Mission> actualMissions = missionService.getMission(params);
//
//        // Then
//        assertEquals(expectedMissions, actualMissions);
//        verify(missionRepository).getMission(params);
//    }

    @Test
    void getUserMission() {
        int userId = 1;
        Map<String, String> params = new HashMap<>();
        List<Object[]> results = new ArrayList<>();
        Mission mission = new Mission();
        RegisterMission registerMission = new RegisterMission();
        registerMission.setIsCompleted(true);
        registerMission.setCreatedDate(Instant.now());
        registerMission.setIsActive(true);
        results.add(new Object[]{mission, registerMission});

        // Mock the behavior
        when(missionRepository.getUserMission(userId, params)).thenReturn(results);
        when(missionConverter.toDTO(any(Mission.class))).thenReturn(new MissionDTO());

        // When
        List<RegisterMissionDTO> actualRegisterMissions = missionService.getUserMission(userId, params);

        // Then
        assertNotNull(actualRegisterMissions);
        assertEquals(1, actualRegisterMissions.size());
        verify(missionRepository).getUserMission(userId, params);
    }

    @Test
    void getMissionById() {
        int missionId = 1;
        Mission expectedMission = new Mission();

        // Mock the behavior
        when(missionRepository.getMissionById(missionId)).thenReturn(expectedMission);

        // When
        Mission actualMission = missionService.getMissionById(missionId);

        // Then
        assertEquals(expectedMission, actualMission);
        verify(missionRepository).getMissionById(missionId);
    }

    @Test
    void addMission() {
        Mission mission = new Mission();
        int activityId = 1;
        Mission expectedMission = new Mission();

        // Mock the behavior
        when(missionRepository.addMission(mission, activityId)).thenReturn(expectedMission);

        // When
        Mission actualMission = missionService.addMission(mission, activityId);

        // Then
        assertEquals(expectedMission, actualMission);
        verify(missionRepository).addMission(mission, activityId);
    }

    @Test
    void updateMission() {
        int missionId = 1;
        MissionCreateDTO missionDTO = new MissionCreateDTO();
        Mission mission = new Mission();
        Mission expectedMission = new Mission();

        // Mock the behavior
        when(missionConverter.toEntity(missionDTO)).thenReturn(mission);
        when(missionRepository.updateMission(mission)).thenReturn(expectedMission);
        when(missionConverter.toDTO(expectedMission)).thenReturn(new MissionDTO());

        // When
        MissionDTO actualMissionDTO = missionService.updateMission(missionId, missionDTO);

        // Then
        assertNotNull(actualMissionDTO);
        verify(missionConverter).toEntity(missionDTO);
        verify(missionRepository).updateMission(mission);
        verify(missionConverter).toDTO(expectedMission);
    }

    @Test
    void deleteMission() {
        int missionId = 1;

        // When
        missionService.deleteMission(missionId);

        // Then
        verify(missionRepository).deleteMission(missionId);
    }

    @Test
    void checkMissionBelongToActivity() {
        String activityId = "1";
        String missionId = "1";
        boolean expectedResult = true;

        // Mock the behavior
        when(missionRepository.checkMissionBelongToActivity(activityId, missionId)).thenReturn(expectedResult);

        // When
        Boolean actualResult = missionService.checkMissionBelongToActivity(activityId, missionId);

        // Then
        assertEquals(expectedResult, actualResult);
        verify(missionRepository).checkMissionBelongToActivity(activityId, missionId);
    }

    @Test
    void getExpiredMissions() {
        LocalDate currentTime = LocalDate.now();
        List<Mission> expectedMissions = new ArrayList<>();

        // Mock the behavior
        when(missionRepository.getExpiredMissions(currentTime)).thenReturn(expectedMissions);

        // When
        List<Mission> actualMissions = missionService.getExpiredMissions(currentTime);

        // Then
        assertEquals(expectedMissions, actualMissions);
        verify(missionRepository).getExpiredMissions(currentTime);
    }

    @Test
    void testUpdateMission() {
        Mission mission = new Mission();
        Mission expectedMission = new Mission();

        // Mock the behavior
        when(missionRepository.updateMission(mission)).thenReturn(expectedMission);

        // When
        Mission actualMission = missionService.updateMission(mission);

        // Then
        assertEquals(expectedMission, actualMission);
        verify(missionRepository).updateMission(mission);
    }
}