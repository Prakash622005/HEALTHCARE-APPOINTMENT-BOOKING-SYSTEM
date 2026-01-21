package com.healthcare.appointment.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientId; // custom random patient ID
    private String name;
    private String email;
    private String password;
    private String role = "PATIENT";

    public Patient() {
        // Generate a random patient ID like P12345
        this.patientId = "P" + (int)(Math.random() * 90000 + 10000);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
