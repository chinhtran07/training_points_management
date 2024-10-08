package com.tps.services.impl;


import com.tps.pojo.Activity;
import com.tps.pojo.Mission;
import com.tps.repositories.ActivityRepository;
import com.tps.services.ActivityService;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PointGroupService pointGroupService;


    @Override
    public Activity addActivity(int pointGroupId, Activity activity) {
        activity.setPointGroup(this.pointGroupService.getPointGroup(pointGroupId));
        return this.activityRepository.addActivity(activity);
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
        this.activityRepository.updateActivity(activity);
    }

    @Override
    public void deleteActivity(int id) {
        this.activityRepository.deleteActivity(id);
    }

    @Override
    public List<Mission> getMissionsByActivity(int id) {
        return this.activityRepository.getMissionsByActivity(id);
    }
}
