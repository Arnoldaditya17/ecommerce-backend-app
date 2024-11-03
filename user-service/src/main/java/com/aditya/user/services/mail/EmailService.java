package com.aditya.user.services.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendForgotPasswordMail(String from, String to, String otp) {
        String subject = "Reset Your Password";
        String content = String.format(
                "Hello,\n\n"
                        + "We received a request to reset your password. Use the following One-Time Password (OTP) to reset your password. "
                        + "This OTP is valid for 10 minutes.\n\n"
                        + "Your OTP: %s\n\n"
                        + "If you did not request a password reset, please ignore this email.\n\n"
                        + "Best regards,\n"
                        + "Your Application Team"
                ,otp
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
