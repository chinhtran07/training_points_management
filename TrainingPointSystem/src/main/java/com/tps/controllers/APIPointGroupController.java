package com.tps.controllers;

import com.tps.pojo.Activity;
import com.tps.pojo.Pointgroup;
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

    @PostMapping(path = "/{pointGroupId}/activities")
    public void addActivity(@PathVariable int pointGroupId, @RequestBody Activity activity) {
        this.activityService.addActivity(pointGroupId ,activity);
    }
}
