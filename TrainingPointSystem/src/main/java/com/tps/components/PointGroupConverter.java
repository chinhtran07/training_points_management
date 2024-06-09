package com.tps.components;

import com.tps.dto.PointGroupDTO;
import com.tps.pojo.PointGroup;
import org.springframework.stereotype.Component;

@Component
public class PointGroupConverter {

    public PointGroup toEntity(PointGroupDTO pointGroup) {
        PointGroup pointGroupEntity = new PointGroup();

        pointGroupEntity.setId(pointGroup.getId());
        pointGroupEntity.setName(pointGroup.getName());
        pointGroupEntity.setContent(pointGroup.getContent());
        pointGroupEntity.setMaxPoint(pointGroup.getMaxPoint());

        return pointGroupEntity;
    }

    public PointGroupDTO toDTO(PointGroup pointGroup) {
        PointGroupDTO pointGroupDTO = new PointGroupDTO();

        pointGroupDTO.setId(pointGroup.getId());
        pointGroupDTO.setName(pointGroup.getName());
        pointGroupDTO.setContent(pointGroup.getContent());
        pointGroupDTO.setMaxPoint(pointGroup.getMaxPoint());

        return pointGroupDTO;
    }
}
