    package com.healthcare.appointment.controller;

import com.healthcare.appointment.model.Doctor;
import com.healthcare.appointment.repository.DoctorRepository;
import com.healthcare.appointment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorController(DoctorService doctorService, DoctorRepository doctorRepository) {
        this.doctorService = doctorService;
        this.doctorRepository = doctorRepository;
    }

    // ----------------------------
    // Register a new doctor
    // ----------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Doctor doctor) {
        Optional<Doctor> existing = doctorRepository.findByEmail(doctor.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        Doctor saved = doctorService.save(doctor);
        return ResponseEntity.ok(saved);
    }

    // ----------------------------
    // Doctor login endpoint
    // ----------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        System.out.println("Doctor login attempt for email: " + email);

        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        if (doctor.isPresent()) {
            System.out.println("Doctor found: " + doctor.get().getName());
            try {
                boolean passwordMatches = BCrypt.checkpw(password, doctor.get().getPassword());
                System.out.println("Password check result: " + passwordMatches);
                if (passwordMatches) {
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", "doctorToken123"); // placeholder for now
                    response.put("doctorId", doctor.get().getId());
                    response.put("name", doctor.get().getName());
                    return ResponseEntity.ok(response);
                } else if (password.equals(doctor.get().getPassword())) {
                    // Plain password match, hash it for future
                    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                    doctor.get().setPassword(hashed);
                    doctorRepository.save(doctor.get());
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", "doctorToken123");
                    response.put("doctorId", doctor.get().getId());
                    response.put("name", doctor.get().getName());
                    return ResponseEntity.ok(response);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid hash for doctor: " + e.getMessage());
                // Check if plain password matches
                if (password.equals(doctor.get().getPassword())) {
                    String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                    doctor.get().setPassword(hashed);
                    doctorRepository.save(doctor.get());
                    Map<String, Object> response = new HashMap<>();
                    response.put("token", "doctorToken123");
                    response.put("doctorId", doctor.get().getId());
                    response.put("name", doctor.get().getName());
                    return ResponseEntity.ok(response);
                }
            }
        } else {
            System.out.println("Doctor not found for email: " + email);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid email or password"));
    }

    // ----------------------------
    // Search doctors by specialization
    // ----------------------------
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "q", required = false, defaultValue = "") String q) {
        List<Doctor> list = doctorService.searchBySpecialization(q);
        return ResponseEntity.ok(list);
    }
    @PostMapping("/add")
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        Doctor saved = doctorService.save(doctor);
        return ResponseEntity.ok(saved);
    }

    // ----------------------------
    // Get all doctors
    // ----------------------------
    @GetMapping("/all")
    public ResponseEntity<?> getAllDoctors() {
        return ResponseEntity.ok(doctorService.findAll());
    }

    // ----------------------------
    // Get doctor by ID
    // ----------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable Long id) {
        return doctorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
