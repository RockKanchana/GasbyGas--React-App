package com.lmu.gasbygas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientSearchResDTO {

    private int clientId;
    private String name;
    private String email;
    private String address;
    private String contact;
    private int roleId;
    private String nic;
    private String registrationNumber;
    private String certification;
    
}
