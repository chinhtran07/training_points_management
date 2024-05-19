package com.tps.controllers;


import com.tps.components.ActivityConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.ActivityDetailDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.Mission;
import com.tps.services.ActivityService;
import com.tps.services.MissionService;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/activities")
@CrossOrigin()
public class APIActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityConverter converter;

    @Autowired
    private MissionService missionService;

    @Autowired
    private RegisterMissionService registerMissionService;

    @GetMapping()
    public ResponseEntity<List<Activity>> getAllActivities(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.activityService.getActivities(params), HttpStatus.OK);
    }


    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PostMapping(path = "/{activityId}/missions", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    @ResponseStatus(HttpStatus.CREATED)
    public void addMission(@RequestBody Mission mission, @PathVariable int activityId) {
        this.missionService.addOrUpdateMission(mission, activityId);
    }

    @PostMapping("/{activityId}/upload")
    @ResponseStatus(HttpStatus.OK)
    public void upload(@RequestParam("file")MultipartFile file, @PathVariable int activityId) {
        this.registerMissionService.updateRegisterMission(file, activityId);
    }

}
