package com.example.ngoma;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Ngoma - Password Reset Request");
        message.setText(
                "You requested a password reset.\n\n" +
                        "Click the link below to set a new password (this link expires in 15 minutes):\n" +
                        baseUrl + "/reset-password.html?token=" + token + "\n\n" +
                        "If you didn't request this, you can safely ignore this email"
        );
        mailSender.send(message);
    }

    public void sendVerificationEmail(String toEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Ngoma - Verify Your Account");
        message.setText(
                "Welcome to Ngoma!\n\n" +
                        "Click the link below to verify your account (this link expires in 24 hours):\n" +
                baseUrl + "/api/verify?token=" + token + "\n\n" +
                        "If you didn't sign up, you can safely ignore this email"
        );
        mailSender.send(message);
    }
}
