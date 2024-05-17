package com.tps.services.impl;

import com.tps.dto.ActivityDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.Pointgroup;
import com.tps.repositories.ActivityRepository;
import com.tps.repositories.PointGroupRepository;
import com.tps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    @Override
    public void addActivity(Activity activity) {
        activity.setCreatedDate(Instant.now());
        this.activityRepository.addActivity(activity);
    }

    @Override
    public List<Activity> getActivities(Map<String, String> params) {
        return this.activityRepository.getActivities(params);
    }

    @Override
    public Activity getActivityById(int id) {
        return this.activityRepository.getActivityById(id);
    }

    @Override
    public void updateActivity(Activity activity) {
        activity.setUpdatedDate(Instant.now());
        this.activityRepository.updateActivity(activity);
    }

    @Override
    public void deleteActivity(int id) {
        this.activityRepository.deleteActivity(id);
    }

    @Override
    public List<Activity> getExpiredActivity(Instant currentTime) {
        return this.activityRepository.findByExpirationDateBeforeAndIsActive(currentTime);
    }
}
