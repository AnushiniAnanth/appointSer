package com.meditrack.appointment.service;

import com.meditrack.appointment.dto.AppointmentRequest;
import com.meditrack.appointment.dto.RescheduleRequest;
import com.meditrack.appointment.entity.Appointment;

import java.util.List;

public interface AppointmentService {

    Appointment bookAppointment(String patientId, AppointmentRequest request);

    Appointment rescheduleAppointment(String appointmentId, RescheduleRequest request);

    Appointment confirmAppointment(String appointmentId);

    void cancelByPatient(String appointmentId, String patientId);

    void cancelByDoctor(String appointmentId, String doctorId);

    List<Appointment> getPendingAppointmentsByPatient(String patientId);

    List<Appointment> getUpcomingAppointmentsByPatient(String patientId);

    List<Appointment> getCompletedAppointmentsByPatient(String patientId);

    List<Appointment> getHistoryByPatient(String patientId);

    List<Appointment> getPendingAppointmentsByDoctor(String doctorId);

    List<Appointment> getUpcomingAppointmentsByDoctor(String doctorId);

    List<Appointment> getCompletedAppointmentsByDoctor(String doctorId);

    List<Appointment> getHistoryByDoctor(String doctorId);
}
