package com.lmu.gasbygas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmu.gasbygas.config.PwdEncoder;
import com.lmu.gasbygas.dto.request.LoginReqDTO;
import com.lmu.gasbygas.entity.UserEntity;
import com.lmu.gasbygas.repository.UserRepo;
import com.lmu.gasbygas.service.UserService;
import com.lmu.gasbygas.util.JWTUtil;
import com.lmu.gasbygas.util.ResponseUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PwdEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findFirstByEmail(username);
        if (userEntity == null) {
            return null;
        }
        return new User(userEntity.getEmail(), "", new ArrayList<>());
    }

    @Override
    public ResponseUtil authenticationLogin(LoginReqDTO loginReqDTO) {

        UserEntity userEntity = userRepo.findFirstByEmail(loginReqDTO.getEmail());
        if (userEntity != null) {
            Boolean isMatch = encoder.matches(loginReqDTO.getPassword(),
                    userEntity.getPassword());
            if (isMatch) {
                String accessToken = jwtUtil.generateToken(userEntity.getEmail(),
                        Integer.toString(userEntity.getUserId()));
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("token", accessToken);
                data.put("role",  userEntity.getRole().getRoleId());
                return new ResponseUtil(200, "Login Successfully", data);
            } else {
                return new ResponseUtil(401, "Invalid Credentials", null);
            }
        } else {
            return new ResponseUtil(404, "User Not Found", null);
        }
    }

}
