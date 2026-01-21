package com.healthcare.appointment.service;

import com.healthcare.appointment.model.Patient;
import java.util.Optional;
import java.util.List;


public interface PatientService {
    Patient register(Patient patient);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findById(Long id);
    List<Patient> findAll();
}
