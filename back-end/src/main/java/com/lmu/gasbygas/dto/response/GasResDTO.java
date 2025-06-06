package com.lmu.gasbygas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasResDTO {

    private int gasId;
    private String type;
    private double price;
    
}
