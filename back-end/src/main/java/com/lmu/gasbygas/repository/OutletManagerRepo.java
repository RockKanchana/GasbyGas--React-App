package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.OutletManagerEntity;

public interface OutletManagerRepo extends JpaRepository<OutletManagerEntity, Integer> {

    String FIND_BY_NAME = "SELECT * FROM outlet_manager WHERE name = ?1";
    String FIND_BY_EMAIL = "SELECT * FROM outlet_manager WHERE email = ?1";
    String FIND_BY_STATUS = "SELECT * FROM outlet_manager WHERE status = ?1";

    @Query(value = FIND_BY_NAME, nativeQuery = true)
    OutletManagerEntity findByName(String managerName);

    @Query(value = FIND_BY_STATUS, nativeQuery = true)
    List<OutletManagerEntity> findAllNamesByStatus(Integer inactive);

    boolean existsByEmail(String email);

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    OutletManagerEntity findByemail(String email);
    
}