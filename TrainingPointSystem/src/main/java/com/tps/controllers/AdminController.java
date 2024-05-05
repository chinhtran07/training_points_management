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
import java.util.HashSet;
import java.util.Set;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PointGroupService pointGroupService;




    @GetMapping("/")
    public String admin(Model model) {
        return "admin";
    }

    @ModelAttribute
    public void commonAttr(Model model) {
        EntityScanner entityScanner = new EntityScanner();
        Set<String> names = entityScanner.scanEntities("com.tps.pojo");
        Set<String> modifiedNames = new HashSet<>();
        names.forEach(n -> modifiedNames.add(n.substring(13)));
        model.addAttribute("entityNames", modifiedNames);
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
