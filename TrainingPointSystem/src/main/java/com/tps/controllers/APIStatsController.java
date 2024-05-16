package com.tps.controllers;

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

    @GetMapping("/training-points")
    public ResponseEntity<List<StudentTotalPointsDTO>> statsTrainingPointByFaculty(@RequestParam Map<String, String> params) {
        List<Object[]> result = this.statsService.statsTrainingPoint(params);
        List<StudentTotalPointsDTO> studentTotalPointsDTOList = new ArrayList<>();
        for (Object[] row : result) {
            StudentTotalPointsDTO dto = new StudentTotalPointsDTO();
            dto.setId(Integer.parseInt(row[0].toString()));
            dto.setFirstName(row[1].toString());
            dto.setLastName(row[2].toString());
            dto.setClassName(row[3].toString());
            dto.setFacultyName(row[4].toString());
            dto.setTotalPoints(Integer.parseInt(row[5].toString()));
            studentTotalPointsDTOList.add(dto);
        }
        return new ResponseEntity<>(studentTotalPointsDTOList, HttpStatus.OK);
    }

}
