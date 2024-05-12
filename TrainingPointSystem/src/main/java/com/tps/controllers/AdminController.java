package com.tps.controllers;


import com.tps.pojo.Assistant;
import com.tps.pojo.User;
import com.tps.services.AssistantService;
import com.tps.services.PointGroupService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
@ControllerAdvice
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private AssistantService assistantService;


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
        field.add("Faculty");

        params.put("role", User.ASSISTANT);
        model.addAttribute("field", field);
        model.addAttribute("users", this.assistantService.getUserAssistants(params));
        return "user";
    }


    @GetMapping("/assistants")
    public String addAssistantView(Model model) {
        Assistant assistant = new Assistant();
        model.addAttribute("assistant", new Assistant());
        return "new-user";
    }

    @PostMapping("/assistants")
    public String addAssistantProcess(Model model, @ModelAttribute(value = "assistant") @Valid Assistant user, BindingResult rs) {

        if (!rs.hasErrors()) {
            try {
                this.assistantService.addAssistant(user);
                return "redirect:/admin/users";
            } catch (Exception e) {
                model.addAttribute("errMsg", e.toString());
            }
        }

        return "new-user";
    }


    @RequestMapping("")
    public String home(Model model) {
        return "admin";
    }
}
