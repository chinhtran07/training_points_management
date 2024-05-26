package com.tps.controllers;

import com.tps.components.MissingReportConverter;
import com.tps.dto.MissingReportDTO;
import com.tps.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/missing-report")
public class APIMissingReportController {

    @Autowired
    private MissingReportService missingReportService;

    @Autowired
    private MissingReportConverter missingReportConverter;

    @GetMapping("/")
    public ResponseEntity<List<MissingReportDTO>> getMissingReportByFaculty(@RequestParam String facultyId) {
        if (facultyId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Object[]> result = this.missingReportService.getMissionReportByFaculty(Integer.parseInt(facultyId));
            List<MissingReportDTO> missingReportDTOList = new ArrayList<>();
            for (Object[] o : result) {
                MissingReportDTO missingReportDTO = missingReportConverter.toDTO(o);
                missingReportDTOList.add(missingReportDTO);
            }
            return new ResponseEntity<>(missingReportDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
