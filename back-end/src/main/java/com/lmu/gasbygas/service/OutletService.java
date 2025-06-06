package com.lmu.gasbygas.service;

import com.lmu.gasbygas.dto.request.OutletManagerReqDTO;
import com.lmu.gasbygas.dto.request.OutletReqDTO;
import com.lmu.gasbygas.util.ResponseUtil;

public interface OutletService {

    ResponseUtil addNewManager(OutletManagerReqDTO addManagerReqDTO);
    ResponseUtil addNewOutlet(OutletReqDTO outletReqDTO);
    ResponseUtil getManagerNameList();
    ResponseUtil getAllOutletList();
    ResponseUtil getOutletByManager(String email);
    ResponseUtil updateOutlet(int outletId, OutletReqDTO outletReqDTO);
    ResponseUtil getOutletNameList();
    
}
