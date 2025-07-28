package com.meditrack.appointment.service.impl;

import com.meditrack.appointment.dto.AppointmentRequest;
import com.meditrack.appointment.dto.RescheduleRequest;
import com.meditrack.appointment.entity.Appointment;
import com.meditrack.appointment.enums.AppointmentStatus;
import com.meditrack.appointment.repository.AppointmentRepository;
import com.meditrack.appointment.service.AppointmentService;
import com.meditrack.appointment.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public Appointment bookAppointment(String patientId, AppointmentRequest request) {
        String appointmentId = generateAppointmentId();

        Appointment appointment = Appointment.builder()
                .appointmentId(appointmentId)
                .patientId(patientId)
                .doctorId(request.getDoctorId())
                .patientName(request.getPatientName())
                .age(request.getAge())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .department(request.getDepartment())
                .doctor(request.getDoctorName())
                .appointmentDateTime(request.getAppointmentDateTime())
                .isEmergency(request.isEmergency())
                .reason(request.getReason())
                .notes(request.getNotes())
                .status(AppointmentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Appointment saved = appointmentRepository.save(appointment);

        emailService.sendAppointmentConfirmation(
                saved.getEmail(),
                saved.getPatientName(),
                saved.getDoctor(),
                saved.getAppointmentDateTime().toLocalDate().toString(),
                saved.getAppointmentDateTime().toLocalTime().toString()
        );

        return saved;
    }

    @Override
    public Appointment rescheduleAppointment(String appointmentId, RescheduleRequest request) {
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appointmentId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Appointment not found");
        }

        Appointment appointment = optional.get();

        appointment.setAppointmentDateTime(request.getNewAppointmentDateTime());
        appointment.setStatus(AppointmentStatus.RESCHEDULED); // ✅ Important for tracking
        appointment.onUpdate(); // ✅ Best to keep audit handling consistent

        return appointmentRepository.save(appointment);
    }


    @Override
    public Appointment confirmAppointment(String appointmentId) {
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appointmentId);
        if (optional.isEmpty()) throw new RuntimeException("Appointment not found");

        Appointment appointment = optional.get();
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancelByPatient(String appointmentId, String patientId) {
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appointmentId);
        if (optional.isPresent() && optional.get().getPatientId().equals(patientId)) {
            Appointment appointment = optional.get();
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointment.setUpdatedAt(LocalDateTime.now());
            appointmentRepository.save(appointment);
        }
    }

    @Override
    public void cancelByDoctor(String appointmentId, String doctorId) {
        Optional<Appointment> optional = appointmentRepository.findByAppointmentId(appointmentId);
        if (optional.isPresent() && optional.get().getDoctorId().equals(doctorId)) {
            Appointment appointment = optional.get();
            appointment.setStatus(AppointmentStatus.CANCELLED);
            appointment.setUpdatedAt(LocalDateTime.now());
            appointmentRepository.save(appointment);
        }
    }

    @Override
    public List<Appointment> getPendingAppointmentsByPatient(String patientId) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, AppointmentStatus.PENDING);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByPatient(String patientId) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public List<Appointment> getCompletedAppointmentsByPatient(String patientId) {
        return appointmentRepository.findByPatientIdAndStatus(patientId, AppointmentStatus.COMPLETED);
    }

    @Override
    public List<Appointment> getHistoryByPatient(String patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    @Override
    public List<Appointment> getPendingAppointmentsByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.PENDING);
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.CONFIRMED);
    }

    @Override
    public List<Appointment> getCompletedAppointmentsByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorIdAndStatus(doctorId, AppointmentStatus.COMPLETED);
    }

    @Override
    public List<Appointment> getHistoryByDoctor(String doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    private String generateAppointmentId() {
        int randomNum = new Random().nextInt(9000) + 1000;
        return "APT-" + randomNum;
    }
}
