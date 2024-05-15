package com.tps.controllers;

import com.tps.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class APIStatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("")
    public ResponseEntity<List<Object[]>> statsTrainingPointByFaculty(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.statsService.statsTrainingPoint(params), HttpStatus.OK);
    }

}
