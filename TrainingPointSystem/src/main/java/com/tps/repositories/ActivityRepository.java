package com.tps.repositories;

import com.tps.pojo.Activity;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public interface ActivityRepository {
    void addActivity(Activity activity);
    List<Activity> getActivities(Map<String, String> params);
    Activity getActivityById(int id);
    Activity updateActivity(Activity activity);
    void deleteActivity(int id);
    List<Activity> findByExpirationDateBeforeAndIsActive(Instant currentDate);
}
