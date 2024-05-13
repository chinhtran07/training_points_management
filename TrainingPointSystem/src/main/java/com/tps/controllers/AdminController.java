package com.tps.controllers;


import com.tps.pojo.Assistant;
import com.tps.pojo.User;
import com.tps.services.AssistantService;
import com.tps.services.PointGroupService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        return "assistant";
    }


    @GetMapping("/assistants")
    public String addAssistantView(Model model) {
        Assistant assistant = new Assistant();
        model.addAttribute("assistant", new Assistant());
        return "new-assistant";
    }

    @GetMapping("/assistants/{id}")
    public String updateAssistantView(Model model, @PathVariable int id) {
        Assistant assistant = this.assistantService.getAssistantById(id);
        model.addAttribute("assistant", assistant);
        return "update-assistant";
    }

    @PostMapping("/assistants/{id}")
    public String updateAssistantProcess(Model model, @ModelAttribute(value = "assistant") @Valid Assistant assistant, BindingResult rs) {
        if (!rs.hasErrors()) {
            try {
                this.assistantService.updateAssistant(assistant);
                model.addAttribute("alert", "Cập nhật thành công");
                return "update-assistant";
            } catch (Exception e) {
                model.addAttribute("errMsg", e.toString());
            }
        }

        return "update-assistant";
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

        return "new-assistant";
    }


    @RequestMapping("")
    public String home(Model model) {
        return "admin";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?logout";
    }
}
