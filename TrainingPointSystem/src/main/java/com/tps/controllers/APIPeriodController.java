package com.tps.controllers;

import com.tps.dto.PeriodDTO;
import com.tps.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/period")
public class APIPeriodController {

    @Autowired
    private PeriodService periodService;

    @GetMapping("")
    public ResponseEntity<List<PeriodDTO>> getAllPeriod(@RequestParam String year) {
        List<PeriodDTO> list = this.periodService.getAllPeriods(year)
                .stream().map(p -> new PeriodDTO(p.getId(), p.getSemester().getId(), p.getYear()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
