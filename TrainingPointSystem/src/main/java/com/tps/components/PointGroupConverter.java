package com.tps.components;

import com.tps.dto.PointGroupDTO;
import com.tps.pojo.Pointgroup;
import org.springframework.stereotype.Component;

@Component
public class PointGroupConverter {

    public static Pointgroup toEntity(PointGroupDTO pointGroup) {
        Pointgroup pointGroupEntity = new Pointgroup();

        pointGroupEntity.setId(pointGroup.getId());
        pointGroupEntity.setName(pointGroup.getName());
        pointGroupEntity.setContent(pointGroup.getContent());
        pointGroupEntity.setMaxPoint(pointGroup.getMaxPoint());

        return pointGroupEntity;
    }

    public static PointGroupDTO toDTO(Pointgroup pointGroup) {
        PointGroupDTO pointGroupDTO = new PointGroupDTO();

        pointGroupDTO.setId(pointGroup.getId());
        pointGroupDTO.setName(pointGroup.getName());
        pointGroupDTO.setContent(pointGroup.getContent());
        pointGroupDTO.setMaxPoint(pointGroup.getMaxPoint());

        return pointGroupDTO;
    }
}
