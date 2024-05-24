package com.tps.controllers;

import com.tps.components.StatsConverter;
import com.tps.dto.ClassTotalPointsDTO;
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

    @GetMapping("/training-points/faculty")
    public ResponseEntity<List<ClassTotalPointsDTO>> statsTrainingPointByFaculty(@RequestParam String facultyId) {
        List<Object[]> result = this.statsService.statsTrainingPointByFaculty(facultyId);
        List<ClassTotalPointsDTO> list = new ArrayList<>();
        for(Object[] o : result) {
            ClassTotalPointsDTO s = statsConverter.toDTO(o);
            list.add(s);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/training-points/rank")
    public ResponseEntity<List<Object[]>> statsTrainingPointByRank() {
        List<Object[]> result = this.statsService.statsTrainingPointByRank();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/training-points")
    public ResponseEntity<List<Object[]>> statsTrainingPoint() {
        List<Object[]> result = this.statsService.statsTrainingPoints();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
