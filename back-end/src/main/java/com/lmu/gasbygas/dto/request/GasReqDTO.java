package com.lmu.gasbygas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasReqDTO {

    private int gasId;
    private String type;
    private double price;
    
}
