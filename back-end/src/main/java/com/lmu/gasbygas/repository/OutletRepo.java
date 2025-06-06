package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.OutletEntity;

public interface OutletRepo extends JpaRepository<OutletEntity,Integer> {

    
    String FIND_BY_LOCATION = "SELECT * FROM outlet WHERE district = ?1";
    String FIND_BY_ID = "SELECT * FROM outlet WHERE id = ?1";
    String FIND_ALL_NAME = "SELECT * FROM outlet";
    String FIND_ALL_Manager = "SELECT * FROM outlet WHERE manager_id = ?1";

    @Query(value = FIND_BY_LOCATION, nativeQuery = true)
    boolean findByDistrict(String location);

    @Query(value = FIND_BY_ID, nativeQuery = true)
    OutletEntity findByOutletId(int id);

    @Query(value = FIND_ALL_NAME, nativeQuery = true)
    List<OutletEntity> findAllNames();

    boolean existsByDistrict(String location);

    @Query(value = FIND_ALL_Manager, nativeQuery = true)
    OutletEntity findByManager(int id);
    
}
