package com.lmu.gasbygas.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "temp_client")
public class TempClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "INT")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "en_password")
    private String encryptedPassword;

    @Column(name = "email_otp")
    private String emailOtp;

    @Column(name = "contact_otp")
    private String contactOtp;

    @Column(name = "client_data")
    private String clientDataJson;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Date created_at;
    
}
