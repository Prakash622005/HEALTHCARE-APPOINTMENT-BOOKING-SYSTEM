package com.healthcare.appointment.controller;

import com.healthcare.appointment.service.AdminService;
import com.healthcare.appointment.service.DoctorService;
import com.healthcare.appointment.service.PatientService;
import com.healthcare.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.healthcare.appointment.service.MaintenanceService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private MaintenanceService maintenanceService;

    private boolean maintenanceMode = false;

    // -------------------- LOGIN PAGE --------------------
    @GetMapping("/login")
    public String showLoginPage() {
        return "admin-login";  // templates/admin-login.html
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        if (adminService.validateAdmin(username, password)) {
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin-login";
        }
    }

    // -------------------- DASHBOARD --------------------
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("appointments", appointmentService.findAll());
        model.addAttribute("maintenanceMode", maintenanceMode);
        return "admin-dashboard";
    }

    // -------------------- MAINTENANCE TOGGLE --------------------
    @PostMapping("/toggle-maintenance")
    public String toggleMaintenance() {
        maintenanceMode = !maintenanceMode;
        return "redirect:/admin/dashboard";
    }

    // -------------------- MAINTENANCE PAGE --------------------
    @GetMapping("/maintenance")
    public String maintenancePage() {
        return "maintenance";  // templates/maintenance.html
    }

    // -------------------- DOCTOR LIST --------------------
    @GetMapping("/doctors")
    public String viewDoctors(Model model) {
        model.addAttribute("doctors", doctorService.findAll());
        model.addAttribute("maintenanceMode", maintenanceMode);
        return "doctor-list";
    }

    // -------------------- PATIENT LIST --------------------
    @GetMapping("/patients")
    public String viewPatients(Model model) {
        model.addAttribute("patients", patientService.findAll());
        model.addAttribute("maintenanceMode", maintenanceMode);
        return "patient-list";
    }

    // -------------------- APPOINTMENT LIST --------------------
    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.findAll());
        model.addAttribute("maintenanceMode", maintenanceMode);
        return "appointment";
    }
    @PostMapping("/enableMaintenance")
    public String enableMaintenance() {
        maintenanceService.enableMaintenance();
        System.out.println("Maintenance enabled");
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/disableMaintenance")
    public String disableMaintenance() {
        maintenanceService.disableMaintenance();
        System.out.println("Maintenance disabled");
        return "redirect:/admin/dashboard";
    }
}
