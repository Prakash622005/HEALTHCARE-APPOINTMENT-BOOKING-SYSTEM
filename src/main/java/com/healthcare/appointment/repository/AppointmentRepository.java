package com.healthcare.appointment.repository;

import com.healthcare.appointment.model.Appointment;
import com.healthcare.appointment.model.Doctor;
import com.healthcare.appointment.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Fetch appointments by doctor entity
    List<Appointment> findByDoctor(Doctor doctor);

    // Fetch appointments by patient entity
    List<Appointment> findByPatient(Patient patient);

    // Fetch appointments by doctor within a specific time range
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // Fetch appointments by doctor ID
    List<Appointment> findByDoctorId(Long doctorId);

    // Fetch appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);
}
