package com.tps.services.impl;

import com.tps.components.MissionConverter;
import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Mission;
import com.tps.pojo.RegisterMission;
import com.tps.repositories.ActivityRepository;
import com.tps.repositories.MissionRepository;
import com.tps.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MissionServiceImpl implements MissionService {
    @Autowired
    MissionRepository missionRepository;
    @Autowired
    MissionConverter missionConverter;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Mission> getMission(Map<String, String> params) {
        return null;
    }

    @Override
    public List<RegisterMissionDTO> getUserMission(int userId, Map<String, String> params) {
        List<Object[]> results = missionRepository.getUserMission(userId, params);
        List<RegisterMissionDTO> registerMissionDTOS = results.stream()
                .map(result -> {
                    Mission mission = (Mission) result[0];
                    Boolean isCompleted = false;
                    String registerDate = null;
                    boolean isActive = false;


                    if (result[1] != null) {
                        isCompleted = ((RegisterMission) result[1]).getIsCompleted();
                        registerDate = ((RegisterMission) result[1]).getCreatedDate().toString();
                        isActive = ((RegisterMission) result[1]).getIsActive();
                    }

                    RegisterMissionDTO registerMissionDTO = new RegisterMissionDTO();
                    registerMissionDTO.setMission(missionConverter.toDTO(mission));
                    registerMissionDTO.setIsCompleted(isCompleted);
                    registerMissionDTO.setRegisterDate(registerDate);
                    registerMissionDTO.setActive(isActive);

                    return registerMissionDTO;
                })
                .collect(Collectors.toList());

        String isRegisted = params.get("isRegisted");
        if (isRegisted != null && !isRegisted.isEmpty()) {
            registerMissionDTOS = registerMissionDTOS.stream().filter(RegisterMissionDTO::isActive)
                    .collect(Collectors.toList());
        }
        String isCompleted = params.get("isCompleted");
        if (isCompleted != null && !isCompleted.isEmpty()) {
            registerMissionDTOS = registerMissionDTOS.stream().filter(RegisterMissionDTO::getIsCompleted)
                    .collect(Collectors.toList());
        }

        return registerMissionDTOS;
    }

    @Override
    public Mission getMissionById(int id) {
        return this.missionRepository.getMissionById(id);
    }

    @Override
    public Mission addMission(Mission mission, int activityId) {
        return this.missionRepository.addMission(mission, activityId);
    }

    @Override
    public MissionDTO updateMission(int missionId, MissionCreateDTO missionDTO) {
        Mission mission = missionConverter.toEntity(missionDTO);
        mission.setId(missionId);
        mission = this.missionRepository.updateMission(mission);
        return missionConverter.toDTO(mission);
    }

    @Override
    public void deleteMission(int id) {
        this.missionRepository.deleteMission(id);
    }

    @Override
    public Boolean checkMissionBelongToActivity(String activityId, String missionId) {
        return this.missionRepository.checkMissionBelongToActivity(activityId, missionId);
    }

    @Override
    public List<Mission> getExpiredMissions(LocalDate currentTime) {
        return this.missionRepository.getExpiredMissions(currentTime);
    }

    @Override
    public Mission updateMission(Mission mission) {
        return this.missionRepository.updateMission(mission);
    }
}
