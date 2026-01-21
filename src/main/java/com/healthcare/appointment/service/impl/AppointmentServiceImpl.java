package com.healthcare.appointment.service.impl;

import com.healthcare.appointment.model.Appointment;
import com.healthcare.appointment.repository.AppointmentRepository;
import com.healthcare.appointment.repository.DoctorRepository;
import com.healthcare.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Appointment book(Appointment appointment) throws Exception {
        // Basic conflict check: same doctor, overlapping time slot (assuming 30-minute gap)
        LocalDateTime time = appointment.getAppointmentTime();
        if (time == null) {
            throw new Exception("Appointment time required");
        }

        LocalDateTime start = time.minusMinutes(29);
        LocalDateTime end = time.plusMinutes(29);
        Long docId = appointment.getDoctor().getId();

        List<Appointment> conflicts = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(docId, start, end);
        if (!conflicts.isEmpty()) {
            throw new Exception("Doctor not available at chosen time");
        }

        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment cancel(Long id) throws Exception {
        Optional<Appointment> opt = appointmentRepository.findById(id);
        if (opt.isEmpty()) {
            throw new Exception("Appointment not found");
        }
        Appointment app = opt.get();
        app.setStatus("CANCELLED");
        return appointmentRepository.save(app);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }
}
