package com.healthcare.appointment.service;

import com.healthcare.appointment.model.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    Appointment book(Appointment appointment) throws Exception;
    List<Appointment> getByPatient(Long patientId);
    Optional<Appointment> findById(Long id);  // keep this one
    Appointment cancel(Long id) throws Exception;
    Appointment save(Appointment appointment);
    List<Appointment> findAll();
    void deleteById(Long id);
}
