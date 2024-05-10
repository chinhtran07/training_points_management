package com.tps.controllers;


import com.tps.pojo.User;
import com.tps.services.PointGroupService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void commonAttribute(Model model) {
        HashMap<String, String> views = new HashMap<>();
        views.put("users", "Quan ly nguoi dung");
        views.put("pointGroups", "Quan ly dieu");
        model.addAttribute("views", views);
    }


    @GetMapping("/")
    public String admin(Model model) {
        Set<String> pojos = new HashSet<>();
        pojos.add("Users");
        pojos.add("Pointgroups");
        model.addAttribute("pojos", pojos);
        return "admin";
    }

    @GetMapping("/pointgroups")
    public String pointgroup(Model model) {
        model.addAttribute("pointGroups", this.pointGroupService.getAllPointGroups());
        return "content";
    }

    @GetMapping("/users")
    public String user(Model model, @RequestParam(required = false) Map<String, String> params) {
        List<String> field = new ArrayList<>();

        field.add("id");
        field.add("Username");
        field.add("First name");
        field.add("Last name");
        field.add("Active");

        model.addAttribute("field", field);
        model.addAttribute("users", this.userService.getAllUsers(params));

        return "user";
    }


    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @RequestMapping("")
    public String home(Model model) {
        return "admin";
    }
}
