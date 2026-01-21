package com.healthcare.appointment.controller;

import com.healthcare.appointment.model.Doctor;
import com.healthcare.appointment.repository.DoctorRepository;
import com.healthcare.appointment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        Optional<Doctor> doctor = doctorRepository.findByEmailAndPassword(email, password);

        if (doctor.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("token", "doctorToken123"); // placeholder for now
            response.put("doctorId", doctor.get().getId());
            response.put("name", doctor.get().getName());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }
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
