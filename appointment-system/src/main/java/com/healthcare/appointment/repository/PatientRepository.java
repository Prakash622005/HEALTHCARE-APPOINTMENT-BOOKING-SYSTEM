package com.healthcare.appointment.repository;

import com.healthcare.appointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // For registration checks
    Optional<Patient> findByEmail(String email);

    // Add this missing method ↓
    Optional<Patient> findByEmailAndPassword(String email, String password);
}
