package com.lmu.gasbygas.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResDTO {

    private int tokenId;
    private LocalDateTime pickupDate;
    private LocalDateTime expiryDate;
    private String status;
    
}
