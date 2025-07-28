package com.meditrack.appointment.config;

import com.meditrack.appointment.entity.Appointment;
import com.meditrack.appointment.enums.AppointmentStatus;
import com.meditrack.appointment.repository.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;

@Configuration
public class DummyDataLoader {

    @Bean
    public CommandLineRunner loadDummyAppointment(AppointmentRepository repository) {
        return args -> {
            String patientId = "PAT-" + getRandomNumber();
            String doctorId = "DOC-" + getRandomNumber();
            String appointmentId = "APT-" + getRandomNumber();

            Appointment appointment = new Appointment();
            appointment.setAppointmentId(appointmentId);

            appointment.setPatientId(patientId);
            appointment.setDoctorId(doctorId);
            appointment.setAppointmentDateTime(LocalDateTime.now().plusDays(3));
            appointment.setStatus(AppointmentStatus.PENDING);
            appointment.setReason("Dummy appointment data for testing");

            repository.save(appointment);

            System.out.println("✅ Dummy Appointment Created");
            System.out.println("Patient ID    : " + patientId);
            System.out.println("Doctor ID     : " + doctorId);
            System.out.println("Appointment ID: " + appointmentId);
        };
    }

    private String getRandomNumber() {
        return String.valueOf(10000 + new Random().nextInt(90000)); // 5-digit number
    }
}
