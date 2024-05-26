package com.tps.services;

import com.tps.pojo.Activity;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ActivityService {
    int addActivity(int pointGroupId, Activity activity);
    List<Activity> getActivities(Map<String, String> params);
    Activity getActivityById(int id);
    Activity updateActivity(Activity activity);
    void deleteActivity(int id);
    List<Activity> getExpiredActivity(Instant currentTime);
}
