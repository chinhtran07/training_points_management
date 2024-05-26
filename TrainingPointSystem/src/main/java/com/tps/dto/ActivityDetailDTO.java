package com.tps.dto;

import com.tps.pojo.Mission;
import com.tps.pojo.PointGroup;
import lombok.Data;

import java.util.List;

@Data
public class ActivityDetailDTO {

    private int Id;
    private String Name;
    private PointGroupDTO pointGroup;
    private List<MissionDTO> missions;
}
