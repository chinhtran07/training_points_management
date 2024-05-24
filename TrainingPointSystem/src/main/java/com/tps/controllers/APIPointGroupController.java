package com.tps.controllers;

import com.tps.components.ActivityConverter;
import com.tps.dto.ActivityDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.PointGroup;
import com.tps.repositories.ActivityRepository;
import com.tps.services.ActivityService;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> addActivity(@PathVariable int pointGroupId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = activityConverter.toEntity(activityDTO);
        int activityId = this.activityService.addActivity(pointGroupId ,activity);

        return new ResponseEntity(activityId, HttpStatus.CREATED);
    }
}
