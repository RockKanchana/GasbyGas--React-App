package com.lmu.gasbygas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutletResDTO {

    private int outletId;
    private String name;
    private String managerName;
    private String district;
    private String address;
    private String contact;
    private String status;

}
