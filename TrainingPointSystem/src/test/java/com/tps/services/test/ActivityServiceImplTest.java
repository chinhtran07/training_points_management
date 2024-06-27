package com.tps.services.test;

import com.tps.pojo.Activity;
import com.tps.pojo.Mission;
import com.tps.pojo.PointGroup;
import com.tps.repositories.ActivityRepository;
import com.tps.services.PointGroupService;
import com.tps.services.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityServiceImplTest {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private PointGroupService pointGroupService;

    @InjectMocks
    private ActivityServiceImpl activityService;

    private Activity activity;
    private PointGroup pointGroup;

    @BeforeEach
    void setUp() {
        activity = new Activity();
        pointGroup = new PointGroup();
    }

    @Test
    void addActivity() {
        int pointGroupId = 1;
        when(pointGroupService.getPointGroup(pointGroupId)).thenReturn(pointGroup);
        when(activityRepository.addActivity(activity)).thenReturn(activity);

        Activity result = activityService.addActivity(pointGroupId, activity);

        assertNotNull(result);
        assertEquals(pointGroup, activity.getPointGroup());
        verify(pointGroupService).getPointGroup(pointGroupId);
        verify(activityRepository).addActivity(activity);
    }

    @Test
    void getActivities() {
        List<Activity> activities = Collections.singletonList(new Activity());
        when(activityRepository.getActivities(any(Map.class))).thenReturn(activities);

        List<Activity> result = activityService.getActivities(Collections.emptyMap());

        assertEquals(activities, result);
        verify(activityRepository).getActivities(Collections.emptyMap());

    }

    @Test
    void getActivityById() {
        Activity activity = new Activity();
        when(activityRepository.getActivityById(anyInt())).thenReturn(activity);

        Activity result = activityService.getActivityById(1);

        assertEquals(activity, result);
        verify(activityRepository).getActivityById(1);
    }

    @Test
    void updateActivity() {
        Activity activity = new Activity();

        activityService.updateActivity(activity);

        verify(activityRepository).updateActivity(activity);
    }

    @Test
    void deleteActivity() {
        activityService.deleteActivity(1);

        verify(activityRepository).deleteActivity(1);
    }

    @Test
    void getMissionsByActivity() {
        List<Mission> missions = Collections.singletonList(new Mission());
        when(activityRepository.getMissionsByActivity(anyInt())).thenReturn(missions);

        List<Mission> result = activityService.getMissionsByActivity(1);

        assertEquals(missions, result);
        verify(activityRepository).getMissionsByActivity(1);
    }
}