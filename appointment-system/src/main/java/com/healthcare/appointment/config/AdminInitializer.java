package com.healthcare.appointment.config;

import com.healthcare.appointment.model.Admin;
import com.healthcare.appointment.service.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer {

    @Autowired
    private AdminService adminService;

    @PostConstruct
    public void initAdmin() {
        String defaultUsername = "admin";
        String defaultPassword = "Admin@123";

        if (adminService.findByUsername(defaultUsername).isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername(defaultUsername);
            admin.setPassword(defaultPassword);
            adminService.save(admin);
            System.out.println("✅ Default admin account created: username='admin', password='Admin@123'");
        } else {
            System.out.println("✅ Admin already exists, skipping initialization.");
        }
    }
}
