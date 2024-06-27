package com.tps.services;

import com.tps.pojo.Activity;
import com.tps.pojo.Mission;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Activity addActivity(int pointGroupId, Activity activity);

    List<Activity> getActivities(Map<String, String> params);

    Activity getActivityById(int id);

    void updateActivity(Activity activity);

    void deleteActivity(int id);

    List<Mission> getMissionsByActivity(int id);
}
