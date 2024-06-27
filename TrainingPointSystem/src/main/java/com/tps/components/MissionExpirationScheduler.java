package com.tps.components;

import com.tps.pojo.Mission;
import com.tps.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MissionExpirationScheduler {

    @Autowired
    private MissionService missionService;

    // run at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void checkProductExpiration() {
        LocalDate currentTime = LocalDate.now();

        List<Mission> missions = this.missionService.getExpiredMissions(currentTime);

        for (Mission mission : missions) {
            mission.setIsActive(false);
            this.missionService.updateMission(mission);
        }
    }
}
