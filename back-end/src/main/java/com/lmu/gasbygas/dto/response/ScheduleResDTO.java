package com.lmu.gasbygas.dto.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResDTO {

    private int scheduleId;
    private int outletId;
    private String outletName;
    private LocalDate deliveryDate;
    private String status;
    private List<StockResDTO> stockList;
}


