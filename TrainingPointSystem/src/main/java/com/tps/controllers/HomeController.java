package com.tps.controllers;

import com.tps.pojo.Pointgroup;
import com.tps.services.PointGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private PointGroupService pointGroupService;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("pointGroups", this.pointGroupService.getAllPointGroups());
        return "admin";
    }
}
