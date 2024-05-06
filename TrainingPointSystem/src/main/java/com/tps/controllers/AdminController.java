package com.tps.controllers;


import com.tps.components.EntityScanner;
import com.tps.components.JwtService;
import com.tps.services.PointGroupService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String admin(Model model) {
        Set<String> pojos = new HashSet<>();
        pojos.add("User");
        pojos.add("Faculty");
        pojos.add("Pointgroup");
        pojos.add("Class");
        model.addAttribute("pojos", pojos);
        return "admin";
    }

    @GetMapping("/pointgroup")
    public String pointgroup(Model model) {
        model.addAttribute("pointGroups", this.pointGroupService.getAllPointGroups());
        return "content";
    }



    @RequestMapping("")
    public String home(Model model) {
        return "admin";
    }
}
