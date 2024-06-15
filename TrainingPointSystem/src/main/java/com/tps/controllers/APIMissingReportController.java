package com.tps.controllers;

import com.tps.components.MissingReportConverter;
import com.tps.dto.MissingReportDTO;
import com.tps.dto.MissingReportFacultyDTO;

import com.tps.pojo.MissingReport;
import com.tps.pojo.RegisterMission;
import com.tps.dto.MissingReportStudentDTO;
import com.tps.services.MissingReportService;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/missing-report")
public class APIMissingReportController {

    @Autowired
    private MissingReportService missingReportService;

    @Autowired
    private MissingReportConverter missingReportConverter;

    @Autowired
    private RegisterMissionService registerMissionService;


    @GetMapping(value = "/faculty")
    public ResponseEntity<List<MissingReportFacultyDTO>> getMissingReportByFaculty(@RequestParam String facultyId) {
        if (facultyId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            List<Object[]> result = this.missingReportService.getMissionReportByFaculty(Integer.parseInt(facultyId));
            List<MissingReportFacultyDTO> missingReportDTOList = new ArrayList<>();
            for (Object[] o : result) {
                MissingReportFacultyDTO missingReportDTO = missingReportConverter.toDTOObject(o);
                missingReportDTOList.add(missingReportDTO);
            }
            return new ResponseEntity<>(missingReportDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{missingReportId}")
    public ResponseEntity<MissingReportDTO> getMissingReportById(@PathVariable int missingReportId) {
        MissingReport missingReport = missingReportService.getMissingById(missingReportId);
        if (missingReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(missingReportConverter.toDTO(missingReport), HttpStatus.OK);
    }

    @PostMapping("/{missingReportId}")
    public ResponseEntity updateMissingReport(@PathVariable int missingReportId,
                                              @RequestBody Map<String, String> body) {
        String status = body.get("status");
        MissingReport missingReport = missingReportService.getMissingById(missingReportId);
        if (missingReport == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (Objects.equals(status, MissingReport.Status.ACCEPT.toString())) {
            missingReport.setStatus(MissingReport.Status.ACCEPT);
            RegisterMission registerMission = registerMissionService
                    .getRegisterByStudentMission(missingReport.getStudent().getId(),
                            missingReport.getMission().getId());
            if (registerMission == null) {
                return new ResponseEntity<>("Chưa đăng ký nhiệm vụ này", HttpStatus.NOT_FOUND);
            }
            registerMission.setIsCompleted(true);
            registerMissionService.updateRegistermission(registerMission);
            missingReportService.updateMissingReport(missingReport);
        } else {
            missingReport.setStatus(MissingReport.Status.DENY);
            missingReportService.updateMissingReport(missingReport);
        }
        return new ResponseEntity<>(missingReportConverter.toDTO(missingReport), HttpStatus.OK);
    }
    @GetMapping("/student")
    public ResponseEntity<List<MissingReportStudentDTO>> getMissingReportOfStudent(@RequestParam int studentId, @RequestParam int periodId) {
        if (studentId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<MissingReportStudentDTO> missingReportStudentDTOS = this.missingReportService.getMissingReportByStudentId(studentId, periodId)
                .stream().map(m -> this.missingReportConverter.toMissingReportStudentDTO(m)).collect(Collectors.toList());

        return new ResponseEntity<>(missingReportStudentDTOS, HttpStatus.OK);
    }
}
