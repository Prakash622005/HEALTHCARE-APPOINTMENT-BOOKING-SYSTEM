package com.healthcare.appointment.controller;

import com.healthcare.appointment.model.Patient;
import com.healthcare.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // ----------------------------
    // Register a new patient
    // ----------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Patient patient) {
        Optional<Patient> existing = patientRepository.findByEmail(patient.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        // ✅ Hash the password before saving
        String hashedPassword = BCrypt.hashpw(patient.getPassword(), BCrypt.gensalt());
        patient.setPassword(hashedPassword);

        Patient saved = patientRepository.save(patient);
        return ResponseEntity.ok(Map.of(
                "message", "Registered successfully",
                "patientId", saved.getId(),
                "name", saved.getName()
        ));
    }


    // ----------------------------
    // Login endpoint for patient
    // ----------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<Patient> patientOpt = patientRepository.findByEmail(email);
        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            try {
                if (BCrypt.checkpw(password, patient.getPassword())) {
                    return ResponseEntity.ok(Map.of(
                            "message", "Login successful",
                            "patientId", patient.getId(), // ✅ must be included
                            "name", patient.getName()
                    ));
                }
            } catch (IllegalArgumentException e) {
                // Invalid hash
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid email or password"));
    }


    // ----------------------------
    // Get patient by ID
    // ----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ----------------------------
    // Get all patients (admin use)
    // ----------------------------
    @GetMapping("/all")
    public ResponseEntity<?> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return ResponseEntity.ok(patients);
    }
}
