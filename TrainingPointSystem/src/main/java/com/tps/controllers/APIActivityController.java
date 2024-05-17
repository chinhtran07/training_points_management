package com.tps.controllers;


import com.tps.components.ActivityConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.ActivityDetailDTO;
import com.tps.pojo.Activity;
import com.tps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping()
    public ResponseEntity<List<Activity>> getAllActivities(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.activityService.getActivities(params), HttpStatus.OK);
    }


    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityDetailDTO> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        ActivityDetailDTO dto = converter.toDetailDTO(activity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
