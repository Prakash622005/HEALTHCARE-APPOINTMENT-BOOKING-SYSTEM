package com.healthcare.appointment.service.impl;

import com.healthcare.appointment.model.Doctor;
import com.healthcare.appointment.repository.DoctorRepository;
import com.healthcare.appointment.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repo) {
        this.doctorRepository = repo;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public List<Doctor> searchBySpecialization(String spec) {
        return doctorRepository.findBySpecializationContainingIgnoreCase(spec);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorRepository.findById(id);
    }


    @Override
    public Optional<Doctor> findByEmailAndPassword(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password);
    }


    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
}
