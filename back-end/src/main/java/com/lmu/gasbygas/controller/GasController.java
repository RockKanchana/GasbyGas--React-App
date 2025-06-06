package com.lmu.gasbygas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lmu.gasbygas.dto.request.GasReqDTO;
import com.lmu.gasbygas.service.GasService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("gas")
public class GasController {

    @Autowired
    private GasService gasService;

    @GetMapping(path = "/search/{gId}")
    public ResponseUtil getGasById(@PathVariable Integer gId, Authentication authentication) {
        try {
            return gasService.getGasById(gId);

        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/update-price")
    public ResponseUtil updateGas(@RequestBody GasReqDTO dto ,Authentication authentication) {
        try {
            return gasService.updateGas(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/all")
    public ResponseUtil getAllGas(Authentication authentication) {
        try {
            return gasService.getAllGas();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }
    
}
