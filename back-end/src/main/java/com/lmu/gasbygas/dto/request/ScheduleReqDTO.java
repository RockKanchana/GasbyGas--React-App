package com.lmu.gasbygas.dto.request;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleReqDTO {
    private int outletId;
    private LocalDate deliveryDate;
    private String status;
    private List<StockReqDTO> stockList;
}
