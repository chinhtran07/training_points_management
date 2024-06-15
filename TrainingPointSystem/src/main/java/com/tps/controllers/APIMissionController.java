package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.components.MissionConverter;
import com.tps.components.RegisterMissionConverter;
import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.*;
import com.tps.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/missions")
public class APIMissionController {

    @Autowired
    UserService userService;

    @Autowired
    MissionService missionService;

    @Autowired
    RegisterMissionService registerMissionService;

    @Autowired
    MissingReportService missingReportService;

    @Autowired
    MissionConverter missionConverter;

    @Autowired
    RegisterMissionConverter registerMissionConverter;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/user-mission")
    public ResponseEntity<List<RegisterMissionDTO>> getUserMission(@RequestParam Map<String, String> params,
                                                                  Principal principal) throws ParseException {
        User user = userService.getUserByUsername(principal.getName());

        List<RegisterMissionDTO> registerMission = missionService.getUserMission(user.getId(), params);
        return new ResponseEntity<>(registerMission, HttpStatus.OK);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<MissionDTO> updateMission
            (@PathVariable int missionId, @RequestBody(required = false) MissionCreateDTO missionDTO) {
        Mission mission = missionService.getMissionById(missionId);

        if (mission == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(missionService.updateMission(missionId, missionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{missionId}")
    public ResponseEntity deleteMission(@PathVariable int missionId) {

        if (missionService.getMissionById(missionId) == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        missionService.deleteMission(missionId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{missionId}/register")
    public ResponseEntity<RegisterMissionDTO> registerMission(@PathVariable int missionId, Principal p) {
        Student student = userService.getUserByUsername(p.getName()).getStudent();
        RegisterMission registermission = registerMissionService
                .getRegisterByStudentMission(student.getId(), missionId);
        if (registermission == null) {
            registermission = registerMissionService.addRegisterMission(student.getId(), missionId);
            return new ResponseEntity<>(registerMissionConverter.toDTO(registermission), HttpStatus.CREATED);
        }

        registermission.setIsActive(!registermission.getIsActive());
        registerMissionService.updateRegistermission(registermission);
        return new ResponseEntity<>(registerMissionConverter.toDTO(registermission), HttpStatus.OK);
    }

    @PostMapping(value = "/{missionId}/missing", consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity<MissingReport> reportMissing(@PathVariable int missionId,
                                                       Principal principal,
                                                       @RequestParam(required = false) Map<String, String> params,
                                                       @RequestParam List<MultipartFile> files) {
        Student student = userService.getUserByUsername(principal.getName()).getStudent();
        MissingReport missingreport = missingReportService
                .getMissingByStudentMission(student.getId(), missionId);
        if (missingreport == null) {
            missingreport = missingReportService.addMissingReport(student.getId(), missionId, params);
            missingReportService.uploadMissingImages(files, missingreport.getId());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }


        if (params != null && params.get("description") != null && !params.get("description").isEmpty()) {
            missingreport.setDescription(params.get("description"));
        } else {
            missingreport.setDescription(null);
            missingreport.setIsActive(!missingreport.getIsActive());
        }
        missingReportService.updateMissingReport(missingreport);
        missingReportService.uploadMissingImages(files, missingreport.getId());
        return new ResponseEntity<>(missingreport, HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<MissionDTO>> getAllMissions(@RequestParam String activityId) {
        Activity activity = activityService.getActivityById(Integer.parseInt(activityId));
        if (activity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(this.activityService.getMissionsByActivity(Integer.parseInt(activityId))
                .stream().map(m -> missionConverter.toDTO(m)).collect(Collectors.toList()), HttpStatus.OK);
    }
}
