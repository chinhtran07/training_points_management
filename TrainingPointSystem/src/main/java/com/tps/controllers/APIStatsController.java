package com.tps.controllers;

import com.tps.components.StatsConverter;
import com.tps.dto.ClassTotalPointsDTO;
import com.tps.dto.RankTotalPointsDTO;
import com.tps.pojo.User;
import com.tps.services.StatsService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stats")
public class APIStatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private StatsConverter statsConverter;

    @Autowired
    private UserService userService;

    @GetMapping("/training-points/faculty")
    public ResponseEntity<List<ClassTotalPointsDTO>> statsTrainingPointByFaculty(Principal principal, @RequestParam Map<String, String> params) {
        User user = this.userService.getUserByUsername(principal.getName());
        params.put("facultyId", String.valueOf(user.getAssistant().getFaculty().getId()));
        List<ClassTotalPointsDTO> result = this.statsService.statsTrainingPointByFaculty(params).stream().map(o -> statsConverter.toClassTotalPointDTO(o)).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/training-points/rank")
    public ResponseEntity<List<RankTotalPointsDTO>> statsTrainingPointByRank(Principal principal, @RequestParam Map<String, String> params) {
        User user = this.userService.getUserByUsername(principal.getName());
        params.put("facultyId", String.valueOf(user.getAssistant().getFaculty().getId()));
        List<RankTotalPointsDTO> dtoList = this.statsService.statsTrainingPointByRank(params).stream().map(o -> statsConverter.toRankTotalPointsDTO(o)).collect(Collectors.toList());
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}
