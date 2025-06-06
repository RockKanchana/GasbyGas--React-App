package com.lmu.gasbygas.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmu.gasbygas.constant.AppConstant;
import com.lmu.gasbygas.dto.request.OutletManagerReqDTO;
import com.lmu.gasbygas.dto.request.OutletReqDTO;
import com.lmu.gasbygas.dto.response.ManagerNameResDTO;
import com.lmu.gasbygas.dto.response.OutletNameResDTO;
import com.lmu.gasbygas.dto.response.OutletResDTO;
import com.lmu.gasbygas.entity.OutletEntity;
import com.lmu.gasbygas.entity.OutletManagerEntity;
import com.lmu.gasbygas.entity.RoleEntity;
import com.lmu.gasbygas.entity.UserEntity;
import com.lmu.gasbygas.repository.OutletManagerRepo;
import com.lmu.gasbygas.repository.OutletRepo;
import com.lmu.gasbygas.repository.UserRepo;
import com.lmu.gasbygas.service.OutletService;
import com.lmu.gasbygas.util.ResponseUtil;

@Service
@Transactional
public class OutletServiceImpl implements OutletService {

    @Autowired
    OutletRepo outletRepo;

    @Autowired
    OutletManagerRepo outletManagerRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public ResponseUtil addNewManager(OutletManagerReqDTO addManagerReqDTO) {
        boolean existManager = outletManagerRepo.existsByEmail(addManagerReqDTO.getEmail());
        if (existManager) {
            throw new RuntimeException("Manager allready exist");
        }
        UserEntity userEntity = setNewuser(addManagerReqDTO, new UserEntity());
        UserEntity saveUser = userRepo.save(userEntity);
        if (saveUser != null) {
            OutletManagerEntity managerEntity = setNewManager(addManagerReqDTO, new OutletManagerEntity(), saveUser);
            OutletManagerEntity saveManager = outletManagerRepo.save(managerEntity);
            if (saveManager != null) {
                return new ResponseUtil(200, "Success", null);
            } else {
                throw new RuntimeException("Failed to register manager");
            }
        } else {
            throw new RuntimeException("Failed to register user");
        }
    }

    private UserEntity setNewuser(OutletManagerReqDTO addManagerReqDTO, UserEntity userEntity) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(2);
        String encriptedPassword = pwdEncoder.encode(addManagerReqDTO.getPassword());
        userEntity.setRole(roleEntity);
        userEntity.setEmail(addManagerReqDTO.getEmail());
        userEntity.setPassword(encriptedPassword);
        return userEntity;
    }

    private OutletManagerEntity setNewManager(OutletManagerReqDTO dto,
            OutletManagerEntity outletManagerEntity, UserEntity userEntity) {
        outletManagerEntity.setUser(userEntity);
        outletManagerEntity.setName(dto.getName());
        outletManagerEntity.setEmail(dto.getEmail());
        outletManagerEntity.setNic(dto.getNic());
        return outletManagerEntity;
    }

    @Override
    public ResponseUtil addNewOutlet(OutletReqDTO outletReqDTO) {

        boolean existOutlet = outletRepo.existsByDistrict(outletReqDTO.getDistrict());
        if (existOutlet) {
            throw new RuntimeException("Outlet allready exist");
        }
        OutletManagerEntity outletManagerEntity = outletManagerRepo.findByName(outletReqDTO.getManagerName());
        if(outletManagerEntity != null){
            outletManagerEntity.setStatus(AppConstant.ACTIVE);
        }else{
            throw new RuntimeException("Outlet Mnager not found");
        }
        OutletManagerEntity saveManager = outletManagerRepo.save(outletManagerEntity);
        OutletEntity outletEntity = setOutlet(outletReqDTO, new OutletEntity(), outletManagerEntity);
        OutletEntity saveOutlet = outletRepo.save(outletEntity);
        if (saveOutlet != null && saveManager != null) {
            return new ResponseUtil(200, "Success", null);
        } else {
            throw new RuntimeException("Failed to add outlet");
        }
    }

    @Override
    public ResponseUtil getManagerNameList() {
        List<OutletManagerEntity> managerEntities = outletManagerRepo.findAllNamesByStatus(AppConstant.INACTIVE);
        List<ManagerNameResDTO> managerNameResDTOs = new ArrayList<>();
        if (managerEntities != null) {
            for (OutletManagerEntity ome : managerEntities) {
                ManagerNameResDTO mdto = new ManagerNameResDTO();
                mdto.setName(ome.getName());
                managerNameResDTOs.add(mdto);
            }
            return new ResponseUtil(200, "Success", managerNameResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil getAllOutletList() {
        List<OutletEntity> outletEntities = outletRepo.findAll();
        List<OutletResDTO> outletResDTOs = new ArrayList<>();
        if (outletEntities != null) {
            for (OutletEntity oe : outletEntities) {
                OutletResDTO odto = new OutletResDTO();
                odto.setOutletId(oe.getOutletId());
                odto.setName(oe.getName());
                odto.setManagerName(oe.getManager().getName());
                odto.setDistrict(oe.getDistrict());
                odto.setAddress(oe.getAddress());
                odto.setContact(oe.getContact());
                odto.setStatus(oe.getStatus());
                outletResDTOs.add(odto);
            }
            return new ResponseUtil(200, "Success", outletResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil updateOutlet(int outletId, OutletReqDTO outletReqDTO) {
        OutletManagerEntity outletManagerEntity = outletManagerRepo.findByName(outletReqDTO.getManagerName());
        OutletEntity outletEntity = outletRepo.findByOutletId(outletId);
        if (outletEntity != null) {
            outletEntity = setOutlet(outletReqDTO, outletEntity, outletManagerEntity);
            OutletEntity saveOutlet = outletRepo.save(outletEntity);
            if (saveOutlet != null) {
                return new ResponseUtil(200, "Updated Successfully", null);
            } else {
                throw new RuntimeException("Failed to update outlet");
            }
        } else {
            throw new RuntimeException("Outlet Not found");
        }
    }

    private OutletEntity setOutlet(OutletReqDTO outletReqDTO, OutletEntity outletEntity,
            OutletManagerEntity outletManagerEntity) {
        outletEntity.setName(outletReqDTO.getName());
        outletEntity.setManager(outletManagerEntity);
        outletEntity.setDistrict(outletReqDTO.getDistrict());
        outletEntity.setAddress(outletReqDTO.getAddress());
        outletEntity.setContact(outletReqDTO.getContact());
        outletEntity.setStatus(outletReqDTO.getStatus());
        return outletEntity;
    }

    @Override
    public ResponseUtil getOutletNameList() {
        List<OutletEntity> outletEntities = outletRepo.findAllNames();
        List<OutletNameResDTO> outletNameResDTOs = new ArrayList<>();
        if (outletEntities != null) {
            for (OutletEntity oe : outletEntities) {
                OutletNameResDTO mdto = new OutletNameResDTO();
                mdto.setOutletId(oe.getOutletId());
                mdto.setOutletName(oe.getName());
                mdto.setDistrict(oe.getDistrict());
                outletNameResDTOs.add(mdto);
            }
            return new ResponseUtil(200, "Success", outletNameResDTOs);
        } else {
            throw new RuntimeException("Unsuccess");
        }
    }

    @Override
    public ResponseUtil getOutletByManager(String email) {
        OutletManagerEntity managerEntity = outletManagerRepo.findByemail(email);
        if(managerEntity!=null){
            OutletEntity outletEntity = outletRepo.findByManager(managerEntity.getManagerId());
            if (outletEntity != null) {
                OutletNameResDTO mdto = new OutletNameResDTO();
                mdto.setOutletId(outletEntity.getOutletId());
                mdto.setOutletName(outletEntity.getName());
                mdto.setDistrict(outletEntity.getDistrict());
                return new ResponseUtil(200, "Success", mdto);
            } else {
                throw new RuntimeException("Unsuccess");
            }
        }else{
            throw new RuntimeException("Outlet Manager Not Found");
        }
       
    }
}
