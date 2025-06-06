package com.lmu.gasbygas.service;

import java.time.LocalDateTime;

public interface MessagingService {

    boolean sendOtpToSMS(String contact, String otp) throws Exception;
    boolean sendOtpToEmail(String email, String otp) throws Exception;
    boolean sendEmailHandOverEmptyCylinder(String email, LocalDateTime pickupDate, LocalDateTime expiryDate, String outletLocation) throws Exception;
    boolean sendEmailScheduleDelayed(String email, LocalDateTime pickupDate, LocalDateTime expiryDate, String outletLocation) throws Exception;
    
}
