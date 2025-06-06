package com.lmu.gasbygas.service;

import com.lmu.gasbygas.dto.request.GasReqDTO;
import com.lmu.gasbygas.util.ResponseUtil;

public interface GasService {

    ResponseUtil getGasById(int id);
    ResponseUtil updateGas(GasReqDTO gasReqDTO) throws Exception;
    ResponseUtil getAllGas();

}
