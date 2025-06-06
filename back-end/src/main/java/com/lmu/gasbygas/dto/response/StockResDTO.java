package com.lmu.gasbygas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResDTO {
    
    private int gasId;
    private String gasType;
    private int quantity;
}