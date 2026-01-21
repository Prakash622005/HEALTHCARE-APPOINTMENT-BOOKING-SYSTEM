package com.healthcare.appointment.controller;

import com.healthcare.appointment.model.*;
import com.healthcare.appointment.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // 🔹 Get all doctors (for patient dashboard)
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        if (doctors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(doctors);
    }

    // 🔹 Book an appointment
    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestParam Long doctorId, @RequestParam Long patientId) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<Patient> patientOpt = patientRepository.findById(patientId);

        if (doctorOpt.isEmpty() || patientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Doctor or Patient not found"));
        }

        Doctor doctor = doctorOpt.get();
        Patient patient = patientOpt.get();

        String token = "T" + (new Random().nextInt(900) + 100);

        Appointment appointment = new Appointment(doctor, patient, token);
        appointmentRepository.save(appointment);

        return ResponseEntity.ok(Map.of(
                "message", "Appointment booked successfully",
                "token", token,
                "doctorName", doctor.getName(),
                "patientName", patient.getName()
        ));
    }


    // 🔹 Get all appointments for a doctor
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentsForDoctor(@PathVariable Long doctorId) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        if (doctorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Doctor not found"));
        }

        List<Appointment> appointments = appointmentRepository.findByDoctor(doctorOpt.get());
        return ResponseEntity.ok(appointments);
    }

    // 🔹 Get all appointments for a patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getAppointmentsForPatient(@PathVariable Long patientId) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Patient not found"));
        }

        List<Appointment> appointments = appointmentRepository.findByPatient(patientOpt.get());
        return ResponseEntity.ok(appointments);
    }
}
