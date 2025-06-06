package com.lmu.gasbygas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmu.gasbygas.dto.request.ClientUpdateReqDTO;
import com.lmu.gasbygas.service.ClientService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping(path = "/search",params = { "username" })
    public ResponseUtil getClientByUsername(@RequestParam String username, Authentication authentication) {
        try {
            return clientService.searchClientByUsername(username);

        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/update-profile")
    public ResponseUtil updateClient(@RequestBody ClientUpdateReqDTO dto ,Authentication authentication) {
        try {
            return clientService.updateClientProfile(dto);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }


    @GetMapping(path = "/business/pending")
    public ResponseUtil getBusinessClientByStatus( Authentication authentication) {
        try {
            return clientService.getAllPendingBusiness();

        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    @PutMapping(path = "/business/verify",params = { "id","status" })
    public ResponseUtil verifyOrganization(@RequestParam int id,@RequestParam  int status ,Authentication authentication) {
        try {
            return clientService.verifyOrganization(id, status);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getLocalizedMessage(), null);
        }
    }

    
}
