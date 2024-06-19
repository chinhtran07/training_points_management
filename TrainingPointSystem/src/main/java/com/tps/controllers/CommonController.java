package com.tps.controllers;

import com.tps.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@ControllerAdvice
@RequestMapping("/")
public class CommonController {

    @Autowired
    private FacultyService facultyService;

    @ModelAttribute
    public void commonAttribute(Model model) {
        HashMap<String, String> views = new HashMap<>();
        views.put("assistants", "Quản lý trợ lý sinh viên");
        views.put("pointGroups", "Quản lý điều lệ");
        model.addAttribute("faculties", this.facultyService.getAllFaculty(new HashMap<>()));
        model.addAttribute("views", views);
    }
}
