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
@CrossOrigin
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

    @PostMapping(path = "/{activityId}/missions", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<ActivityDetailDTO> addMission(@RequestBody Mission mission, @PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        this.missionService.addMission(mission, activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ActivityDetailDTO dto = activityConverter.toDetailDTO(activity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/{activityId}/missions")
    public ResponseEntity<MissionDTO> addMission(@PathVariable int activityId,
                                                 @RequestBody MissionDTO missionDTO) {
        Activity activity = activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.missionService.addMission(missionConverter.toEntity(missionDTO), activityId);
        return new ResponseEntity<>(missionDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}/missions")
    public ResponseEntity<List<MissionDTO>> getMissionByActivity(@PathVariable int activityId) {
        Activity activity = activityService.getActivityById(activityId);
        if(activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Mission> missions = activityService.getMissionsByActivity(activityId);
        List<MissionDTO> missionDTOs = missions.stream().map(m -> missionConverter.toDTO(m)).collect(Collectors.toList());
        return new ResponseEntity<>(missionDTOs, HttpStatus.OK);
    }


    @PutMapping("/{activityId}")
    public ResponseEntity updateActivity(@PathVariable int activityId, @RequestBody ActivityDTO activityDTO) {
        Activity activity = this.activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        activityDTO.setId(activityId);
        this.activityService.updateActivity(activityConverter.toEntity(activityDTO));
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
    public void upload(@RequestParam("file")MultipartFile file, @PathVariable String activityId) {
        this.registerMissionService.updateRegisterMission(file, activityId);
    }

}
