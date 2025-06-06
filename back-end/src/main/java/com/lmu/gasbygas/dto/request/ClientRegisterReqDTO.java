package com.lmu.gasbygas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRegisterReqDTO {

    private String name;
    private String email;
    private String password;
    private String address;
    private String contact;
    private int roleId;
    private String nic;
    private String registrationNumber;
    private String certification;
    
}
