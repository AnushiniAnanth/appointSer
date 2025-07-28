package com.meditrack.appointment.repository;

import com.meditrack.appointment.entity.Appointment;
import com.meditrack.appointment.enums.AppointmentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    Optional<Appointment> findByAppointmentId(String appointmentId);

    List<Appointment> findByPatientId(String patientId);
    List<Appointment> findByPatientIdAndStatus(String patientId, AppointmentStatus status);

    List<Appointment> findByDoctorId(String doctorId);
    List<Appointment> findByDoctorIdAndStatus(String doctorId, AppointmentStatus status);
}
