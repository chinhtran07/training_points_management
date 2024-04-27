package com.tps.controllers;


import com.tps.pojo.Activity;
import com.tps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping()
    public ResponseEntity<List<Activity>> getAllActivities(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.activityService.getActivities(params), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        Activity createdActivity = this.activityService.addActivity(activity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable int activityId) {
        Activity activity = this.activityService.getActivityById(activityId);
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<Activity> updateActivity(@PathVariable int activityId, @RequestBody Activity activity) {
        Activity updatedActivity = this.activityService.getActivityById(activityId);
        if (updatedActivity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.activityService.updateActivity(updatedActivity);
        return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
    }

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteActivity(@PathVariable int activityId) {
        this.activityService.deleteActivity(activityId);
    }
}
