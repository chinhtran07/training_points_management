package com.tps.controllers;

import com.tps.components.ActivityConverter;
import com.tps.components.PointGroupConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.PointGroupDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.Assistant;
import com.tps.pojo.PointGroup;
import com.tps.pojo.User;
import com.tps.repositories.ActivityRepository;
import com.tps.services.ActivityService;
import com.tps.services.PointGroupService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/point-groups")
public class APIPointGroupController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserService userService;

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private ActivityConverter activityConverter;

    @Autowired
    private PointGroupConverter pointGroupConverter;

    @PostMapping(path = "/{pointGroupId}/activities")
    public ResponseEntity<ActivityDTO> addActivity(@PathVariable int pointGroupId,
                                                   @RequestBody ActivityDTO activityDTO,
                                                   Principal principal) {
        User assistant = userService.getUserByUsername(principal.getName());
        Activity activity = activityConverter.toEntity(activityDTO);
        activity.setAssistant(assistant);
        ActivityDTO result =activityConverter.toDTO(this.activityService.addActivity(pointGroupId ,activity));

        return new ResponseEntity(result, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PointGroupDTO> getAllPointGroups() {

        return pointGroupService.getAllPointGroups().stream().map(pg -> pointGroupConverter.toDTO(pg)).collect(Collectors.toList());
    }
}
