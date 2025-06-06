package com.lmu.gasbygas.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmu.gasbygas.dto.request.ScheduleReqDTO;
import com.lmu.gasbygas.service.ScheduleService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping(path = "/schedule-delivery")
    public ResponseUtil addSchedule(@RequestBody ScheduleReqDTO dto ,Authentication authentication) {
        try {
            return scheduleService.scheduleDelivery(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/update-shedule/{id}")
    public ResponseUtil updateSchedule( @PathVariable int id, @RequestBody ScheduleReqDTO dto ,Authentication authentication) {
        try {
            return scheduleService.updateSchedule(id, dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/all")
    public ResponseUtil getAllSchedules(Authentication authentication) {
        try {
            return scheduleService.getAllSchedules();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping(path = "/all/outlet/{id}")
    public ResponseUtil getAllSchedulesbyOutlet(@PathVariable int id, Authentication authentication) {
        try {
            return scheduleService.getAllSchedulesbyOutlet(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    
    @GetMapping(path = "/all/{status}")
    public ResponseUtil getAllSchedulesbyStatus(@PathVariable String status, Authentication authentication) {
        try {
            return scheduleService.getAllSchedulesByStatus(status);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @PutMapping(path = "/update-status/{id}", params = { "date", "status" })
    public ResponseUtil updateScheduleStaus( @PathVariable int id,@RequestParam LocalDate date, @RequestParam String status ,Authentication authentication) {
        try {
            return scheduleService.updateScheduleStatus(id, date, status);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @DeleteMapping(path = "/delete", params = { "id" })
    public ResponseUtil updateScheduleStaus( @RequestParam int id ,Authentication authentication) {
        try {
            return scheduleService.deleteSchedule(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }
    
}
