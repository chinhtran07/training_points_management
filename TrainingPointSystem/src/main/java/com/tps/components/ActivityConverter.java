package com.tps.components;

import com.tps.dto.ActivityDTO;
import com.tps.dto.ActivityDetailDTO;
import com.tps.pojo.Activity;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.stream.Collectors;

@Component
public class ActivityConverter {

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    MissionConverter missionConverter;


    public Activity toEntity(ActivityDTO dto) {
        Activity activity = new Activity();

        activity.setId(dto.getId());
        activity.setName(dto.getName());
        activity.setPointgroup(pointGroupService.getPointgroup(dto.getPointGroupId()));
        activity.setMaxPoint(dto.getMaxPoint());
        return activity;
    }

    public ActivityDTO toDTO(Activity activity) {
        ActivityDTO dto = new ActivityDTO();

        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setPointGroupId(activity.getPointgroup().getId());
        dto.setMaxPoint(activity.getMaxPoint());

        return dto;
    }

    public ActivityDetailDTO toDetailDTO(Activity activity) {
        ActivityDetailDTO dto = new ActivityDetailDTO();

        dto.setId(activity.getId());
        dto.setName(activity.getName());
        dto.setPointGroup(PointGroupConverter.toDTO(pointGroupService.getPointgroup(activity.getPointgroup().getId())));
        dto.setMissions(activity.getMissions().stream().map(missionConverter::toDTO).collect(Collectors.toList()));

        return dto;
    }
}
