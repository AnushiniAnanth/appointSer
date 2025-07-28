package com.meditrack.appointment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    private String doctorId;
    private String patientName;
    private int age;
    private String phoneNumber;
    private String email;
    private String department;
    private String doctorName;
    private LocalDateTime appointmentDateTime;
    private boolean isEmergency;
    private String reason;
    private String notes;
}
