package com.tps.controllers;


import com.tps.components.ActivityConverter;
import com.tps.components.MissionConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.ActivityDetailDTO;
import com.tps.dto.MissionDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.Mission;
import com.tps.services.ActivityService;
import com.tps.services.MissionService;
import com.tps.services.RegisterMissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/activities")
@Api(value = "API")
public class APIActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private RegisterMissionService registerMissionService;

    @Autowired
    ActivityConverter activityConverter;

    @Autowired
    private MissionConverter missionConverter;

    @GetMapping()
    public ResponseEntity<List<ActivityDTO>> getAllActivities(@RequestParam Map<String, String> params) {
        List<ActivityDTO> activities = this.activityService.getActivities(params).stream().map(a -> activityConverter.toDTO(a)).collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityDetailDTO> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        return new ResponseEntity<>(activityConverter.toDetailDTO(activity), HttpStatus.OK);
    }


    @PostMapping("/{activityId}/missions")
    public ResponseEntity<MissionDTO> addMission(@PathVariable int activityId,
                                                 @RequestBody MissionDTO missionDTO) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MissionDTO result = missionConverter.toDTO(this.missionService.addMission(missionConverter.toEntity(missionDTO), activityId));
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}/missions")
    public ResponseEntity<List<MissionDTO>> getActivityMission(@PathVariable int activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<MissionDTO> result = activity.getMissions().stream().map(mission -> missionConverter.toDTO(mission)).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity updateActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = this.activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        activityDTO.setId(activityId);
        activity.setName(activityDTO.getName());
        activity.setMaxPoint(activityDTO.getMaxPoint());
        activity.getPointGroup().setId(activityDTO.getPointGroup());
        activity.getFaculty().setId(activityDTO.getFaculty());
        activity.getPeriod().setId(activityDTO.getPeriod());
        this.activityService.updateActivity(activity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(@PathVariable int activityId) {
        this.activityService.deleteActivity(activityId);
    }


    @PostMapping(value = "/{activityId}/upload", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    @ResponseStatus(HttpStatus.OK)
    public void upload(@RequestParam("file") MultipartFile file, @PathVariable String activityId) {
        this.registerMissionService.updateRegisterMission(file, activityId);
    }

}
