package com.lmu.gasbygas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmu.gasbygas.dto.request.OutletManagerReqDTO;
import com.lmu.gasbygas.dto.request.OutletReqDTO;
import com.lmu.gasbygas.service.OutletService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("outlet")
public class OutletController {

    @Autowired
    OutletService outletService;

    @PostMapping(path = "/add-manager")
    public ResponseUtil saveManager(@RequestBody OutletManagerReqDTO dto ,Authentication authentication) {
        try {
            return outletService.addNewManager(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PostMapping(path = "/add-outlet")
    public ResponseUtil saveOutlet(@RequestBody OutletReqDTO dto ,Authentication authentication) {
        try {
            return outletService.addNewOutlet(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/update-outlet/{id}")
    public ResponseUtil updateOutlet(@PathVariable int id, @RequestBody OutletReqDTO dto ,Authentication authentication) {
        try {
            return outletService.updateOutlet(id, dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/all")
    public ResponseUtil getAllOutlets(Authentication authentication) {
        try {
            return outletService.getAllOutletList();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping(path = "/all/outlet-names")
    public ResponseUtil getAllOutletNames(Authentication authentication) {
        try {
            return outletService.getOutletNameList();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping(path = "/find/{email}")
    public ResponseUtil getOutlet(@PathVariable String email, Authentication authentication) {
        try {
            return outletService.getOutletByManager(email);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping(path = "/all/manager-names")
    public ResponseUtil getAllManagerNames(Authentication authentication) {
        try {
            return outletService.getManagerNameList();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }
    
}
