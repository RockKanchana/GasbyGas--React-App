package com.lmu.gasbygas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.GasEntity;

public interface GasRepo extends JpaRepository<GasEntity,Integer> {

    String FIND_BY_GASID = "SELECT * FROM gas WHERE id = ?1";

    @Query(value = FIND_BY_GASID, nativeQuery = true)
    GasEntity findGasByGasId(int id);
    
}
