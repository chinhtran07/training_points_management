package com.tps.components;

import com.tps.pojo.Activity;
import com.tps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ActivityExpirationScheduler {

    @Autowired
    private ActivityService activityService;

    // run at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void checkProductExpiration() {
        Instant currentTime = Instant.now();
        List<Activity> expiredActivities = this.activityService.getExpiredActivity(currentTime);

        for (Activity activity : expiredActivities) {
            activity.setIsActive(false);
            this.activityService.updateActivity(activity);
        }
    }
}
