package com.lmu.gasbygas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmu.gasbygas.dto.request.ClientRegisterReqDTO;
import com.lmu.gasbygas.dto.request.LoginReqDTO;
import com.lmu.gasbygas.service.ClientService;
import com.lmu.gasbygas.service.UserService;
import com.lmu.gasbygas.util.ResponseUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    public ResponseUtil validateAuthentication(@RequestBody LoginReqDTO loginReqDTO) {
        try {
            return userService.authenticationLogin(loginReqDTO);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @PostMapping("/register")
    public ResponseUtil registerNewClient(@RequestBody ClientRegisterReqDTO clientRegisterReqDTO) {
        try {
            return clientService.registerNewClient(clientRegisterReqDTO);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

    @PostMapping(path = "/verify-otps", params = { "email", "emailOtp", "contactOtp" })
    public ResponseUtil verifyOtps(@RequestParam String email,
            @RequestParam String emailOtp, @RequestParam String contactOtp) throws Exception {
        try {
            return clientService.verifiOTP(email, emailOtp, contactOtp);
        } catch (Exception e) {
            return new ResponseUtil(500, e.getMessage(), null);
        }
    }

}
