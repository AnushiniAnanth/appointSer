package com.meditrack.appointment.controller;

import com.meditrack.appointment.dto.AppointmentRequest;
import com.meditrack.appointment.dto.RescheduleRequest;
import com.meditrack.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/medi-track")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/patient/{patientId}/appointment/book")
    public ResponseEntity<?> bookAppointment(
            @PathVariable String patientId,
            @RequestBody @Valid AppointmentRequest request) {
        return ResponseEntity.ok(appointmentService.bookAppointment(patientId, request));
    }

    @DeleteMapping("/doctor/{doctorId}/appointment/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointmentByDoctor(
            @PathVariable String doctorId,
            @PathVariable String appointmentId) {
        appointmentService.cancelByDoctor(appointmentId, doctorId);
        return ResponseEntity.ok("✅ Appointment cancelled by doctor successfully.");
    }



    @DeleteMapping("/patient/{patientId}/appointment/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointmentByPatient(
            @PathVariable String patientId,
            @PathVariable String appointmentId) {
        appointmentService.cancelByPatient(appointmentId, patientId);
        return ResponseEntity.ok("✅ Appointment cancelled by patient successfully.");
    }


    @PutMapping("/appointment/{appointmentId}/confirm")
    public ResponseEntity<?> confirmAppointment(@PathVariable String appointmentId) {
        return ResponseEntity.ok(appointmentService.confirmAppointment(appointmentId));
    }

    @GetMapping("/doctor/{doctorId}/appointments/pending")
    public ResponseEntity<?> getPendingAppointmentsForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(appointmentService.getPendingAppointmentsByDoctor(doctorId));
    }

    @GetMapping("/patient/{patientId}/appointments/pending")
    public ResponseEntity<?> getPendingAppointmentsForPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(appointmentService.getPendingAppointmentsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}/appointments/upcoming")
    public ResponseEntity<?> getUpcomingAppointmentsForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByDoctor(doctorId));
    }

    @GetMapping("/patient/{patientId}/appointments/upcoming")
    public ResponseEntity<?> getUpcomingAppointmentsForPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(appointmentService.getUpcomingAppointmentsByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}/appointments/history")
    public ResponseEntity<?> getAppointmentHistoryForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(appointmentService.getHistoryByDoctor(doctorId));
    }

    @GetMapping("/patient/{patientId}/appointments/history")
    public ResponseEntity<?> getAppointmentHistoryForPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(appointmentService.getHistoryByPatient(patientId));
    }

    @GetMapping("/doctor/{doctorId}/appointments/completed")
    public ResponseEntity<?> getCompletedAppointmentsForDoctor(@PathVariable String doctorId) {
        return ResponseEntity.ok(appointmentService.getCompletedAppointmentsByDoctor(doctorId));
    }

    @GetMapping("/patient/{patientId}/appointments/completed")
    public ResponseEntity<?> getCompletedAppointmentsForPatient(@PathVariable String patientId) {
        return ResponseEntity.ok(appointmentService.getCompletedAppointmentsByPatient(patientId));
    }
}
