package com.lmu.gasbygas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OutletManagerReqDTO {

    private String name;
    private String email;
    private String password;
    private String nic;
    
}
