package com.tps.controllers;

import com.tps.components.JwtService;
import com.tps.components.MissionConverter;
import com.tps.components.RegisterMissionConverter;
import com.tps.dto.ActivityDTO;
import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.*;
import com.tps.services.MissingReportService;
import com.tps.services.MissionService;
import com.tps.services.RegisterMissionService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/missions")
public class APIMissionController {

    @Autowired
    JwtService jwtService;

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


    @GetMapping()
    public ResponseEntity<List<RegisterMissionDTO>> getUseMission(@RequestParam Map<String, String> params,
                                                                  Principal principal) throws ParseException {
        User user = userService.getUserByUsername(principal.getName());

        List<RegisterMissionDTO> registerMission = missionService.getUserMission(user.getId(), params);
        return new ResponseEntity<>(registerMission, HttpStatus.OK);
    }

    @PutMapping("/{missionId}")
    public ResponseEntity<MissionDTO> updateMission
            (@PathVariable int missionId, @RequestBody MissionCreateDTO missionDTO) {
        Mission mission = missionService.getMissionById(missionId);

        if (mission == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(missionService.updateMission(missionId, missionDTO), HttpStatus.OK);
    }

//    @PostMapping()
//    public ResponseEntity<MissionDTO> addMission(@RequestBody MissionDTO missionDTO) {
//        return new ResponseEntity<>(missionService.addMission(missionDTO), HttpStatus.CREATED);
//    }

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
        Registermission registermission = registerMissionService
                .getRegisterByStudentMission(student.getId(), missionId);
        if (registermission == null) {
            registermission = registerMissionService.addRegisterMission(student.getId(), missionId);
            return new ResponseEntity<>(registerMissionConverter.toDTO(registermission), HttpStatus.CREATED);
        }

        registermission.setIsActive(!registermission.getIsActive());
        registerMissionService.updateRegistermission(registermission);
        return new ResponseEntity<>(registerMissionConverter.toDTO(registermission), HttpStatus.OK);
    }

    @PostMapping("/{missionId}/missing")
    public ResponseEntity<Missingreport> reportMissing(@PathVariable int missionId,
                                                       Principal principal,
                                                       @RequestBody(required = false) Map<String, String> params) {
        Student student = userService.getUserByUsername(principal.getName()).getStudent();
        Missingreport missingreport = missingReportService
                .getMissingByStudentMission(student.getId(), missionId);
        if (missingreport == null) {
            missingreport = missingReportService.addMissingreport(student.getId(), missionId, params);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }


        if (params != null && params.get("description") != null &&  !params.get("description").isEmpty()) {
            missingreport.setDescription(params.get("description"));
        }
        else {
            missingreport.setDescription(null);
            missingreport.setIsActive(!missingreport.getIsActive());
        }
        missingReportService.updateMissingreport(missingreport);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
