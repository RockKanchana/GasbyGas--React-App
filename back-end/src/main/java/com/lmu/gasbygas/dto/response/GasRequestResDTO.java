package com.lmu.gasbygas.dto.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasRequestResDTO {

    private int requestId;
    private int clientId;
    private String outletName;
    private String status;
    private Date requestDate;
    private List<GasReqDetailsResDTO> resDetails;

}
