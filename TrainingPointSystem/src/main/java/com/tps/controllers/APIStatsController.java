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
        List<Map<String, Object>> result = this.statsService.statsTrainingPoint(params);
        List<StudentTotalPointsDTO> studentTotalPointsDTOList = new ArrayList<>();
        for (Map<String, Object> row : result) {
            StudentTotalPointsDTO dto = new StudentTotalPointsDTO();
            dto.setId(Integer.parseInt(row.get("id").toString()));
            dto.setFirstName(row.get("first_name").toString());
            dto.setLastName(row.get("last_name").toString());
            dto.setClassName(row.get("class_name").toString());
            dto.setFacultyName(row.get("faculty_name").toString());
            dto.setTotalPoints(Integer.parseInt(row.get("total_points").toString()));
            studentTotalPointsDTOList.add(dto);
        }
        return new ResponseEntity<>(studentTotalPointsDTOList, HttpStatus.OK);
    }

}
