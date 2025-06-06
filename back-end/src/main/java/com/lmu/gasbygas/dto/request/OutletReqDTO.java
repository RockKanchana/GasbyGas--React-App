package com.lmu.gasbygas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutletReqDTO {

    private String name;
    private String managerName;
    private String district;
    private String address;
    private String contact;
    private String status;
    
}
