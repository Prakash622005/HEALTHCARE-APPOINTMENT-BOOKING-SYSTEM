package com.healthcare.appointment.service;

import com.healthcare.appointment.model.Admin;
import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByUsername(String username);
    Admin save(Admin admin);
    boolean validateAdmin(String username, String password);
}
