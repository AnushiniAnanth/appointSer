package com.meditrack.appointment.dto;

import lombok.Data;
import java.time.LocalDateTime;
import com.meditrack.appointment.enums.AppointmentStatus;

@Data
public class AppointmentResponse {
    private Long appointmentId;
    private String doctorName;
    private String patientName;
    private LocalDateTime appointmentDateTime;
    private String department;
    private String appointmentType;
    private AppointmentStatus status;
}
