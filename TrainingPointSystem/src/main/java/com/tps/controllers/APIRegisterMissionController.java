package com.tps.controllers;

import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/register-mission")
public class APIRegisterMissionController {

    @Autowired
    private RegisterMissionService registerMissionService;

    @PostMapping(path = "/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        this.registerMissionService.updateRegisterMission(file);
    }
}
