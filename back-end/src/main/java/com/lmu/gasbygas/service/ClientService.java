package com.lmu.gasbygas.service;

import com.lmu.gasbygas.dto.request.ClientRegisterReqDTO;
import com.lmu.gasbygas.dto.request.ClientUpdateReqDTO;
import com.lmu.gasbygas.util.ResponseUtil;

public interface ClientService {

  ResponseUtil registerNewClient(ClientRegisterReqDTO dto) throws Exception;
  ResponseUtil searchClientByUsername(String username);
  ResponseUtil getAllPendingBusiness();
  ResponseUtil updateClientProfile(ClientUpdateReqDTO clientUpdateReqDTO) throws Exception;
  ResponseUtil verifyOrganization(int id, int status);
  ResponseUtil verifiOTP(String email, String emailOtp, String contactOtp) throws Exception;
}
