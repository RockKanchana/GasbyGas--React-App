package com.lmu.gasbygas.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasRequestReqDTO {

    private int clientId;
    private int outletId;
    private String status;
    private List<GasRequestDetailsDTO> requestList;

}
