package com.tps.controllers;

import com.tps.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;

@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private FacultyService facultyService;

    @ModelAttribute
    public void commonAttribute(Model model) {
        HashMap<String, String> views = new HashMap<>();
        views.put("users", "Quản lý người dùng");
        views.put("pointGroups", "Quản lý điều lệ");
        model.addAttribute("views", views);
    }

    @ModelAttribute
    public void faculties(Model model) {
        model.addAttribute("faculties", this.facultyService.getAllFaculty(new HashMap<>()));
    }

}
