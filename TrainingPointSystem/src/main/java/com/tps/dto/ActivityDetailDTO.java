package com.tps.dto;

import com.tps.pojo.Pointgroup;
import lombok.Data;

@Data
public class ActivityDetailDTO {

    private int Id;
    private String Name;
    private PointGroupDTO pointGroup;
}
