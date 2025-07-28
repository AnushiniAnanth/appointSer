package com.meditrack.appointment.entity;

import com.meditrack.appointment.enums.AppointmentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "appointments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Appointment {

    // --- Primary MongoDB ID ---
    @Id
    private String id;

    // --- Custom Appointment Tracking ID ---
    private String appointmentId; // e.g., APT-20250728-001

    // --- Foreign Keys ---
    private String patientId;
    private String doctorId;

    // --- Patient Info ---
    private String patientName;
    private int age;
    private String phoneNumber;
    private String email;

    // --- Appointment Info ---
    private String department;
    private String doctor;
    private LocalDateTime appointmentDateTime;
    private boolean isEmergency;

    // --- Medical Info ---
    private String reason;
    private String notes;

    // --- Status ---
    private AppointmentStatus status = AppointmentStatus.PENDING;

    // --- Audit Fields ---
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // --- Lifecycle Hooks (call manually in service layer) ---
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        if (this.status == null) {
            this.status = AppointmentStatus.PENDING;
        }
    }

    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
