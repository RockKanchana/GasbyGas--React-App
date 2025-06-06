package com.lmu.gasbygas.service;

import java.time.LocalDate;

import com.lmu.gasbygas.dto.request.ScheduleReqDTO;
import com.lmu.gasbygas.util.ResponseUtil;

public interface ScheduleService {

    ResponseUtil scheduleDelivery(ScheduleReqDTO scheduleReqDTO);
    ResponseUtil getAllSchedules();
    ResponseUtil getAllSchedulesbyOutlet(int outletId);
    ResponseUtil getAllSchedulesByStatus(String status);
    ResponseUtil updateScheduleStatus(int id, LocalDate deliveryDate, String status);
    ResponseUtil updateSchedule(int id, ScheduleReqDTO scheduleReqDTO);
    ResponseUtil deleteSchedule(int id);

}
