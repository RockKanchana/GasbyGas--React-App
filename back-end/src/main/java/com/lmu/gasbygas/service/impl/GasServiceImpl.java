package com.lmu.gasbygas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmu.gasbygas.dto.request.GasReqDTO;
import com.lmu.gasbygas.dto.response.GasResDTO;
import com.lmu.gasbygas.entity.GasEntity;
import com.lmu.gasbygas.repository.GasRepo;
import com.lmu.gasbygas.service.GasService;
import com.lmu.gasbygas.util.ResponseUtil;

@Service
@Transactional
public class GasServiceImpl implements GasService {

    @Autowired
    private GasRepo gasRepo;

    @Override
    public ResponseUtil getGasById(int id) {
        GasEntity gasExist = gasRepo.findGasByGasId(id);
        if (gasExist != null) {
            GasResDTO gasResDTO = setGasDTO(gasExist, new GasResDTO());
            if (gasResDTO != null) {
                return new ResponseUtil(200, "Success", gasResDTO);
            } else {
                throw new RuntimeException("Unsuccess");
            }
        } else {
            throw new RuntimeException("Gas not found");
        }
    }

    private GasResDTO setGasDTO(GasEntity gasEntity, GasResDTO gasResDTO) {
        gasResDTO.setGasId(gasEntity.getGasId());
        gasResDTO.setType(gasEntity.getType());
        gasResDTO.setPrice(gasEntity.getPrice());
        return gasResDTO;
    }

    @Override
    public ResponseUtil updateGas(GasReqDTO gasReqDTO) throws Exception {
        GasEntity gasEntity = gasRepo.findGasByGasId(gasReqDTO.getGasId());
        if (gasEntity != null) {
            gasEntity.setPrice(gasReqDTO.getPrice());
            GasEntity gasSave = gasRepo.save(gasEntity);
            if (gasSave != null) {
                return new ResponseUtil(200, "Updated Successfully", null);
            } else {
                throw new RuntimeException("Failed to Update Gas");
            }
        } else {
            throw new RuntimeException("Gas not found");
        }
    }

    @Override
    public ResponseUtil getAllGas() {
        List<GasResDTO> responseDTO = new ArrayList<>();
        List<GasEntity> gasEntities = gasRepo.findAll();
        if (gasEntities != null) {
            for (GasEntity entity: gasEntities) {
                GasResDTO dto = new GasResDTO();
                dto.setGasId(entity.getGasId());
                dto.setType(entity.getType());
                dto.setPrice(entity.getPrice());
                responseDTO.add(dto);
            }
            return new ResponseUtil(200, "Success", responseDTO);
        } else {
            return new ResponseUtil(500, "Unsuccess", null);
        }
    }

}
