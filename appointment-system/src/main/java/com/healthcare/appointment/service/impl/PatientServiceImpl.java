package com.healthcare.appointment.service.impl;

import com.healthcare.appointment.model.Patient;
import com.healthcare.appointment.repository.PatientRepository;
import com.healthcare.appointment.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient register(Patient patient) {
        // hash password
        String hashed = BCrypt.hashpw(patient.getPassword(), BCrypt.gensalt());
        patient.setPassword(hashed);
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

}
