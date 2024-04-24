package com.tps.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Map;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(Model model) {
        return "index";
    }
}
