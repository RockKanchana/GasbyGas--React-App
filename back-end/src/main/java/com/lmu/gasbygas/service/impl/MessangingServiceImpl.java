package com.lmu.gasbygas.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lmu.gasbygas.service.MessagingService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MessangingServiceImpl implements MessagingService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Override
    public boolean sendOtpToSMS(String contact, String otp) throws Exception {
        try {
            Twilio.init(accountSid, authToken);

            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(contact),
                    new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                    "Your OTP is: " + otp).create();
            return message.getStatus() != Message.Status.FAILED;
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendOtpToEmail(String email, String otp) throws Exception {
        try {
            if (!isValidEmail(email)) {
                System.err.println("Invalid email: " + email);
                return false;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; font-size: 15px; color: #333;'>"
                    + "<h3>Verify your email address</h3>"
                    + "<p>You need to verify your email address to continue using your GasByGas account. Enter the following code to verify your email address:</p>"
                    + "<div style='font-size: 24px; font-weight: bold; color: #FF5722;'>" + otp + "</div>"
                    + "<p>Please use this code within the next 2 minutes to complete your email verification.</p>"
                    + "<p>Thank you,</p>"
                    + "<p><strong>GasByGas Team</strong></p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("Your Verification Code");
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            return true;

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + email);
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    @Override
    public boolean sendEmailHandOverEmptyCylinder(String email, LocalDateTime pickupDate, LocalDateTime expiryDate,
            String outletLocation) throws Exception {
        try {
            if (!isValidEmail(email)) {
                System.err.println("Invalid email: " + email);
                return false;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; font-size: 15px; color: #333;'>"
                    + "<h3>Handover Your Empty Gas Cylinder</h3>"
                    + "<p>Dear Customer,</p>"
                    + "<p>Your gas request has been processed successfully. To receive your new gas cylinder, please hand over your empty cylinder and payment at the designated outlet.</p>"
                    + "<p><strong>Pickup Date:</strong> " + pickupDate + "</p>"
                    + "<p><strong>Outlet Location:</strong> " + outletLocation + "</p>"
                    + "<p>Ensure you complete the handover process before the expiry date:</p>"
                    + "<div style='font-size: 18px; font-weight: bold; color: #FF5722;'>Expiry Date: " + expiryDate
                    + "</div>"
                    + "<p>Failure to hand over the empty cylinder on time may result in the allocation of your request to another customer.</p>"
                    + "<p>Thank you for choosing GasByGas!</p>"
                    + "<p><strong>GasByGas Team</strong></p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("Your Verification Code");
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            return true;

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + email);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendEmailScheduleDelayed(String email, LocalDateTime pickupDate, LocalDateTime expiryDate,
            String outletLocation) throws Exception {
        try {
            if (!isValidEmail(email)) {
                System.err.println("Invalid email: " + email);
                return false;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; font-size: 15px; color: #333;'>"
                    + "<h3>Gas Pickup Delay Notification</h3>"
                    + "<p>Dear Customer,</p>"
                    + "<p>We regret to inform you that your scheduled gas pickup has been delayed. Below are the updated details:</p>"
                    + "<p><strong>New Pickup Date:</strong> " + pickupDate + "</p>"
                    + "<p><strong>Outlet Location:</strong> " + outletLocation + "</p>"
                    + "<p>Please ensure to collect your gas before the updated expiry date:</p>"
                    + "<div style='font-size: 18px; font-weight: bold; color: #FF5722;'>Expiry Date: " + expiryDate
                    + "</div>"
                    + "<p>We apologize for any inconvenience caused and appreciate your understanding.</p>"
                    + "<p>Thank you for choosing GasByGas!</p>"
                    + "<p><strong>GasByGas Team</strong></p>"
                    + "</div>";

            helper.setTo(email);
            helper.setSubject("Your Verification Code");
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            return true;

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + email);
            e.printStackTrace();
            return false;
        }
    }

}
