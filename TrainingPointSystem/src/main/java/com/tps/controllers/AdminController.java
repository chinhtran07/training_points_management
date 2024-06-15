package com.tps.controllers;


import com.tps.components.StatsConverter;
import com.tps.components.UserConverter;
import com.tps.dto.TotalPointsDTO;
import com.tps.dto.UserAssistantDTO;
import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.pojo.PointGroup;
import com.tps.pojo.User;
import com.tps.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private PointGroupService pointGroupService;

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private StatsService statsService;

    @Autowired
    private StatsConverter statsConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private PeriodService periodService;

    @Autowired
    private SemesterService semesterService;

    @GetMapping("")
    public String admin(Model model) {
        model.addAttribute("years", this.periodService.getYears());
        model.addAttribute("semesters", this.semesterService.getSemesters());
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
        model.addAttribute("pointGroup", new PointGroup());
        return "new-or-update-pointgroup";
    }

    @GetMapping("/pointgroups/{id}")
    public String updatePointGroupView(Model model, @PathVariable int id) {
        model.addAttribute("pointGroup", this.pointGroupService.getPointGroup(id));
        return "new-or-update-pointgroup";
    }

    @PostMapping("/pointgroups")
    public String addOrUpdatePointGroupProcess(Model model, @ModelAttribute(value = "pointgroup") @Valid PointGroup pointGroup, BindingResult bindingResult) {
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
    public String assistantView(Model model, @RequestParam(required = false) Map<String, String> params) {
        List<String> field = new ArrayList<>();

        field.add("id");
        field.add("Username");
        field.add("First name");
        field.add("Last name");
        field.add("Faculty");
        field.add("Active");

        List<UserAssistantDTO> userAssistants = this.assistantService.getUserAssistants(params).stream()
                .map(info -> userConverter.toUserAssistantDTO(info)).collect(Collectors.toList());
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
    public String addAssistantProcess(Model model, @ModelAttribute(value = "assistant") @Valid Assistant user, BindingResult rs) {

        if (!rs.hasErrors()) {
            try {
                this.assistantService.addAssistant(user);
                return "redirect:/admin/assistants";
            } catch (Exception e) {
                model.addAttribute("errMsg", e.toString());
            }
        }
        return "new-assistant";
    }

    @PostMapping("/assistants/update")
    public String updateAssistantProcess(Model model, @ModelAttribute(value = "assistant") @Valid Assistant user, BindingResult rs) {

        if (!rs.hasErrors()) {
            try {
                this.assistantService.updateAssistant(user);
                return "redirect:/admin/assistants";
            } catch (Exception e) {
                model.addAttribute("errMsg", e.toString());
                e.printStackTrace();
            }
        }
        return "update-assistant";
    }

    @PostMapping("/assistants/delete")
    public String delete(@RequestParam int id) {
        this.userService.deleteUser(this.userService.getUserById(id));
        return "redirect:/admin/assistants";
    }

    @RequestMapping("/stats")
    public String stats(Model model) {
        return "stats";
    }

    @GetMapping("/stats/all")
    @ResponseBody
    public List<Object> stats(Map<String, String> params) {
        return this.statsService.statsTrainingPoints(params)
                .stream().map(s -> statsConverter.toTotalPointsDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/stats/class")
    @ResponseBody
    public List<Object> statsByFaculty(@RequestParam Map<String, String> params) {
        return this.statsService.statsTrainingPointByFaculty(params)
                .stream().map(s -> statsConverter.toClassTotalPointDTO(s)).collect(Collectors.toList());
    }

    @GetMapping("/stats/rank")
    @ResponseBody
    public List<Object> statsByRank(@RequestParam Map<String, String> params) {
        return this.statsService.statsTrainingPointByRank(params)
                .stream().map(s -> statsConverter.toRankTotalPointsDTO(s)).collect(Collectors.toList());
    }
}
