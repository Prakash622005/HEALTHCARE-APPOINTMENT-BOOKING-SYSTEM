package com.healthcare.appointment.controller;

import com.healthcare.appointment.model.Doctor;
import com.healthcare.appointment.model.Patient;
import com.healthcare.appointment.repository.DoctorRepository;
import com.healthcare.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent() && BCrypt.checkpw(password, doctor.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "role", "DOCTOR",
                    "name", doctor.get().getName(),
                    "id", doctor.get().getId()
            ));
        }

        Optional<Patient> patient = patientRepository.findByEmail(email);
        if (patient.isPresent() && BCrypt.checkpw(password, patient.get().getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "role", "PATIENT",
                    "name", patient.get().getName(),
                    "id", patient.get().getId()
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid credentials"));
    }
}
