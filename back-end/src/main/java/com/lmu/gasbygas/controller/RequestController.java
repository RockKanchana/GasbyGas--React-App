package com.lmu.gasbygas.controller;

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

import com.lmu.gasbygas.dto.request.GasRequestReqDTO;
import com.lmu.gasbygas.service.RequestService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("request")
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping(path = "/request-gas")
    public ResponseUtil addRequest(@RequestBody GasRequestReqDTO dto ,Authentication authentication) {
        try {
            return requestService.requestGas(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/all")
    public ResponseUtil getAllRequests(Authentication authentication) {
        try {
            return requestService.getAllRequests();
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @GetMapping(path = "/all/client/{id}")
    public ResponseUtil getAllRequestByClient( @PathVariable int id ,Authentication authentication) {
        try {
            return requestService.getAllRequestByClientId(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/token/{id}")
    public ResponseUtil getTokenbyRequest( @PathVariable int id ,Authentication authentication) {
        try {
            return requestService.getTokenByRequestId(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @GetMapping(path = "/all/outlet/{id}")
    public ResponseUtil getAllRequestByOutlet( @PathVariable int id, Authentication authentication) {
        try {
            return requestService.getAllRequestByOutletId(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/update-status/{id}", params = { "status" })
    public ResponseUtil updateRequestStatus( @PathVariable int id, @RequestParam String status ,Authentication authentication) {
        try {
            return requestService.updateRequestStatus(id, status);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @DeleteMapping(path = "/cancel", params = { "id" })
    public ResponseUtil deleteRequest( @RequestParam int id ,Authentication authentication) {
        try {
            return requestService.cancelRequest(id);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }
    
}
