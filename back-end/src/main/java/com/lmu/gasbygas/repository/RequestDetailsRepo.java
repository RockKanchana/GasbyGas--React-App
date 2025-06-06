package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.GasRequestDetailsEntity;
import com.lmu.gasbygas.entity.GasRequestEntity;

public interface RequestDetailsRepo extends JpaRepository<GasRequestDetailsEntity, Integer> {

    String FIND_BY_REQUEST_ID = "SELECT * FROM request_details WHERE request_id = ?1";

    @Query(value = FIND_BY_REQUEST_ID, nativeQuery = true)
    List<GasRequestDetailsEntity> findByGasRequestId(int id);

    void deleteByRequest(GasRequestEntity gasRequest);
    
}
