package com.tps.controllers;

import com.tps.components.StatsConverter;
import com.tps.dto.StudentTotalPointsDTO;
import com.tps.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class APIStatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private StatsConverter statsConverter;

    @GetMapping("/training-points")
    public ResponseEntity<List<StudentTotalPointsDTO>> statsTrainingPointByFaculty(@RequestParam Map<String, String> params) {
        List<Object[]> result = this.statsService.statsTrainingPoint(params);
        List<StudentTotalPointsDTO> studentTotalPointsDTOList = new ArrayList<>();
        for(Object[] o : result) {
            StudentTotalPointsDTO s = statsConverter.toDTO(o);
            studentTotalPointsDTOList.add(s);
        }
        return new ResponseEntity<>(studentTotalPointsDTOList, HttpStatus.OK);
    }

}
