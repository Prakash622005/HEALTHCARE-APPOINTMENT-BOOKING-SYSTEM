package com.healthcare.appointment.service;

import com.healthcare.appointment.model.Doctor;
import java.util.List;
import java.util.Optional;

public interface DoctorService {

    // Save or update a doctor
    Doctor save(Doctor doctor);

    // Search doctors by specialization
    List<Doctor> searchBySpecialization(String spec);

    // Find doctor by ID
    Optional<Doctor> findById(Long id);

    // Get all doctors
    List<Doctor> findAll();

    // Authenticate doctor by email and password
    Optional<Doctor> findByEmailAndPassword(String email, String password);
}
