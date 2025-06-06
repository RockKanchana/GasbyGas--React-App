package com.lmu.gasbygas.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lmu.gasbygas.dto.request.LoginReqDTO;
import com.lmu.gasbygas.util.ResponseUtil;

public interface UserService extends UserDetailsService {
    public ResponseUtil authenticationLogin(LoginReqDTO loginReqDTO);
 
}