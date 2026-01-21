package com.healthcare.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage() {
        return "index"; // your main homepage template (if you have one)
    }

    @GetMapping("/maintenance")
    public String maintenancePage() {
        return "maintenance";
    }
}
