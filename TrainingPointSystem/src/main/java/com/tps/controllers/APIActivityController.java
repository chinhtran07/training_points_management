package com.tps.controllers;


import com.tps.components.ActivityConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.ActivityDetailDTO;
import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/activities")
@CrossOrigin()
public class APIActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private MissionService missionService;

    @Autowired
    private RegisterMissionService registerMissionService;

    @Autowired
    MissionService missionService;

    @Autowired
    ActivityConverter activityConverter;

    @GetMapping()
    public ResponseEntity<List<ActivityDTO>> getAllActivities(@RequestParam Map<String, String> params) {
        List<ActivityDTO> activities = this.activityService.getActivities(params).stream().map(a -> converter.toDTO(a)).collect(Collectors.toList());
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PostMapping(path = "/{activityId}/missions", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ActivityDetailDTO> addMission(@RequestBody Mission mission, @PathVariable int activityId) {
        this.missionService.addOrUpdateMission(mission, activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ActivityDetailDTO dto = converter.toDetailDTO(activity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Activity> createActivity(@RequestBody ActivityDTO activity) {
        this.activityService.addActivity(converter.toEntity(activity));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{activityId}/missions")
    public ResponseEntity<MissionDTO> addMission(@PathVariable int activityId,
                                                 @RequestBody MissionDTO missionDTO) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        missionDTO.setActivity(activityConverter.toDTO(activity));
        return new ResponseEntity<>(missionService.addMission(missionDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityDTO> updateActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = this.activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        activityDTO.setId(activityId);
        Activity updatedActivity = this.activityService.updateActivity(converter.toEntity(activityDTO));
        return new ResponseEntity<>(converter.toDTO(updatedActivity), HttpStatus.OK);
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
    public void upload(@RequestParam("file")MultipartFile file, @PathVariable int activityId) {
        this.registerMissionService.updateRegisterMission(file, activityId);
    }

}
