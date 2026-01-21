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

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/doctor-register")
    public String doctorRegisterPage() {
        return "doctor-register";
    }

    @GetMapping("/doctors")
    public String doctorsPage() {
        return "doctors";
    }

    @GetMapping("/dashboard")
    public String patientDashboard() {
        System.out.println("Patient dashboard accessed");
        return "dashboard";
    }

    @GetMapping("/doctor-dashboard")
    public String doctorDashboard() {
        System.out.println("Doctor dashboard accessed");
        return "doctor-dashboard";
    }
}
