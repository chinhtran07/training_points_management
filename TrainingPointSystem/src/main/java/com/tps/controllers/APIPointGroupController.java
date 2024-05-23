package com.tps.controllers;

import com.tps.components.ActivityConverter;
import com.tps.dto.ActivityDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.PointGroup;
import com.tps.repositories.ActivityRepository;
import com.tps.services.ActivityService;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/point-groups")
public class APIPointGroupController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private ActivityConverter activityConverter;

    @PostMapping(path = "/{pointGroupId}/activities")
    public void addActivity(@PathVariable int pointGroupId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = activityConverter.toEntity(activityDTO);
        this.activityService.addActivity(pointGroupId ,activity);
    }
}
