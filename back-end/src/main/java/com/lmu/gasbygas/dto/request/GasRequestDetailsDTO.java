package com.lmu.gasbygas.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasRequestDetailsDTO {

    private int reqestId;
    private int gasId;
    private int quantity;
    
}
