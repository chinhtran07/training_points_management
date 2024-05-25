package com.tps.controllers;

import com.tps.components.PointGroupConverter;
import com.tps.dto.PointGroupDTO;
import com.tps.pojo.Activity;
import com.tps.pojo.Pointgroup;
import com.tps.repositories.ActivityRepository;
import com.tps.services.ActivityService;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/point-groups")
public class APIPointGroupController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private PointGroupConverter pointGroupConverter;

    @PostMapping(path = "/{id}/activities")
    public void addActivity(@PathVariable int id, @RequestBody Activity activity) {
        Pointgroup pointgroup = this.pointGroupService.getPointgroup(id);
        activity.setPointgroup(pointgroup);
        this.activityService.addActivity(activity);
    }

    @GetMapping
    public List<PointGroupDTO> getAllPointGroups() {
        List<Pointgroup> pointgroups = pointGroupService.getAllPointGroups();
        List<PointGroupDTO> pgDTO = pointgroups.stream().map(PointGroupConverter::toDTO).collect(Collectors.toList());

        return pgDTO;
    }
}
