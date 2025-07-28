package com.meditrack.appointment.service.impl;

import com.meditrack.appointment.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Load sender email from properties
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderEmail);  // use configured email
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Override
    public void sendAppointmentConfirmation(String to, String patientName, String doctorName, String date, String time) {
        String subject = "Appointment Confirmation – MediTrack";
        String body = "Dear " + patientName + ",\n\n"
                + "Your appointment is confirmed.\n\n"
                + "Date: " + date + "\n"
                + "Time: " + time + "\n"
                + "Doctor: Dr. " + doctorName + "\n"
                + "Location: MediTrack Health Center\n\n"
                + "Please arrive 10–15 minutes early.\n\n"
                + "Regards,\nMediTrack Team";
        sendEmail(to, subject, body);
    }

    @Override
    public void sendAppointmentCancellation(String to, String patientName, String doctorName, String date, String time) {
        String subject = "Appointment Cancelled – MediTrack";
        String body = "Dear " + patientName + ",\n\n"
                + "Your appointment on " + date + " at " + time + " with Dr. " + doctorName + " has been cancelled.\n\n"
                + "To reschedule, contact us or use the MediTrack portal.\n\n"
                + "Regards,\nMediTrack Team";
        sendEmail(to, subject, body);
    }

    @Override
    public void sendAppointmentReschedule(String to, String patientName, String doctorName, String newDate, String newTime) {
        String subject = "Appointment Rescheduled – MediTrack";
        String body = "Dear " + patientName + ",\n\n"
                + "Your appointment has been rescheduled:\n"
                + "Date: " + newDate + "\n"
                + "Time: " + newTime + "\n"
                + "Doctor: Dr. " + doctorName + "\n\n"
                + "Regards,\nMediTrack Team";
        sendEmail(to, subject, body);
    }
}
