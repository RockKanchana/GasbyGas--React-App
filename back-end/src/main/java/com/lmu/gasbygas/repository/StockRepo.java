package com.lmu.gasbygas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.DeliveryStockEntity;

public interface StockRepo extends JpaRepository<DeliveryStockEntity, Integer> {

    String FIND_BY_SCHEDULE_ID_AND_GASID = "SELECT * FROM delivery_stock WHERE schedule_id = ?1 AND gas_id = ?2";
    String FIND_BY_SCHEDULE_ID = "SELECT * FROM delivery_stock WHERE schedule_id = ?1";

    @Query(value = FIND_BY_SCHEDULE_ID_AND_GASID, nativeQuery = true)
    DeliveryStockEntity findByScheduleIdAndGasId(int id, int gasId);

    @Query(value = FIND_BY_SCHEDULE_ID, nativeQuery = true)
    List<DeliveryStockEntity> findByScheduleId(int id);
   
    
}
