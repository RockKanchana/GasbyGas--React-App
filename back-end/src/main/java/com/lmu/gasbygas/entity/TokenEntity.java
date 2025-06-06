package com.lmu.gasbygas.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "INT")
    private int tokenId;

    @OneToOne
    @JoinColumn(name = "request_id")
    private GasRequestEntity request;

    @CreationTimestamp
    @Column(name = "issued_date", columnDefinition = "TIMESTAMP")
    private Date issuedDate;

    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TokenStatus status = TokenStatus.ACTIVE;

    public enum TokenStatus {
        ACTIVE, COMPLETED, EXPIRED
    }

    
}
