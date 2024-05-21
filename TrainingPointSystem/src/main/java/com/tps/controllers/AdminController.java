package com.tps.controllers;


import com.tps.dto.UserAssistantDTO;
import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.pojo.Pointgroup;
import com.tps.pojo.User;
import com.tps.services.AssistantService;
import com.tps.services.FacultyService;
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

    @Autowired
    private FacultyService facultyService;


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
        List<String> fields = new ArrayList<>();
        fields.add("id");
        fields.add("Name");
        fields.add("Content");
        fields.add("Max point");
        model.addAttribute("fields", fields);
        model.addAttribute("pointGroups", this.pointGroupService.getAllPointGroups());
        return "pointgroup";
    }

    @GetMapping("/pointgroups/new")
    public String addPointGroupView(Model model) {
        model.addAttribute("pointGroup", new Pointgroup());
        return "new-or-update-pointgroup";
    }

    @GetMapping("/pointgroups/{id}")
    public String updatePointGroupView(Model model, @PathVariable int id) {
        model.addAttribute("pointGroup", this.pointGroupService.getPointgroup(id));
        return "new-or-update-pointgroup";
    }


    @PostMapping("/pointgroups")
    public String addOrUpdatePointGroupProcess(Model model, @ModelAttribute(value = "pointgroup") @Valid Pointgroup pointGroup, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            try {
                this.pointGroupService.addOrUpdate(pointGroup);
                return "redirect: /admin/pointgroups";
            } catch (Exception ex) {
                model.addAttribute("errMsg", ex.toString());
            }
        }

        return "new-or-update-pointgroup";
    }


    @GetMapping("/assistants")
    public String user(Model model, @RequestParam(required = false) Map<String, String> params) {
        List<String> field = new ArrayList<>();

        field.add("id");
        field.add("Username");
        field.add("First name");
        field.add("Last name");
        field.add("Email");
        field.add("Phone number");
        field.add("Gender");
        field.add("DOB");
        field.add("Faculty");
        field.add("Active");


        params.put("role", User.ASSISTANT);
        List<Object[]> infos = this.assistantService.getUserAssistants(params);
        List<UserAssistantDTO> userAssistants = UserAssistantDTO.toDtoList(infos);
        model.addAttribute("field", field);
        model.addAttribute("users", userAssistants);
        return "assistant";
    }


    @GetMapping("/assistants/new")
    public String addAssistantView(Model model) {
        model.addAttribute("assistant", new Assistant());
        return "new-assistant";
    }

    @GetMapping("/assistants/{id}")
    public String updateAssistantView(Model model, @PathVariable int id) {
        Assistant assistant = this.assistantService.getAssistantById(id);
        model.addAttribute("assistant", assistant);
        return "update-assistant";
    }


    @PostMapping("/assistants")
    public String addOrUpdateAssistantProcess(Model model, @ModelAttribute(value = "assistant") @Valid Assistant user, BindingResult rs) {

        if (!rs.hasErrors()) {
            try {
                if (user.getId() != null)
                    this.assistantService.updateAssistant(user);
                else {
                    this.assistantService.addAssistant(user);
                }
                return "redirect:/admin/assistants";
            } catch (Exception e) {
                model.addAttribute("errMsg", e.toString());
            }
        }

        if (user.getId() != null) {
            return "update-assistant";
        }
        return "new-assistant";
    }


    @RequestMapping("")
    public String home(Model model) {
        return "admin";
    }

    @GetMapping("/stats")
    public String stats(Model model, @RequestParam Map<String, String> params) {
//        List<Faculty> faculties = this.facultyService.getAllFaculty(params);
//        for(Faculty f : faculties) {
//            System.out.println(f.getId());
//        }
        model.addAttribute("faculties", this.facultyService.getAllFaculty(params));
        return "stats";
    }

    @PostMapping("/assistants/delete")
    public String delete(@RequestParam int id) {
        this.assistantService.deleteAssistant(this.assistantService.getAssistantById(id));
        return "redirect:/admin/assistants";
    }

//    @GetMapping("/admin/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//
//        return "redirect:/login?logout";
//    }
}
