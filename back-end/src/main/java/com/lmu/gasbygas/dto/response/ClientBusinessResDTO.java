package com.lmu.gasbygas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientBusinessResDTO {

    private int clientId;
    private String name;
    private String email;
    private String address;
    private String contact;
    private String registrationNumber;
    private String certification;
    
}
