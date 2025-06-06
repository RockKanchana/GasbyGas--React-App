package com.lmu.gasbygas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.DeliveryScheduleEntity;
import com.lmu.gasbygas.entity.DeliveryScheduleEntity.ScheduleStatus;
import com.lmu.gasbygas.entity.OutletEntity;

public interface ScheduleRepo extends JpaRepository<DeliveryScheduleEntity,Integer> {

    String FIND_BY_ID = "SELECT * FROM delivery_schedule WHERE id = ?1";
    String FIND_BY_OUTLET_AND_STATUS = "SELECT * FROM delivery_schedule WHERE outlet_id = ? ORDER BY delivery_date ASC LIMIT 1;";
    String FIND_BY_Outlet_ID = "SELECT * FROM delivery_schedule WHERE outlet_id = ?1";
    String FIND_BY_Status = "SELECT * FROM delivery_schedule WHERE status = ?1";

    @Query(value = FIND_BY_ID, nativeQuery = true)
    DeliveryScheduleEntity findByScheduleId(int id);

    @Query(value = FIND_BY_OUTLET_AND_STATUS, nativeQuery = true)
    DeliveryScheduleEntity findByOutletAndStatusOrderByDeliveryDateAsc(int outletId);

    Optional<DeliveryScheduleEntity> findTopByOutletOrderByDeliveryDateDesc(OutletEntity outlet);

    @Query(value = FIND_BY_Outlet_ID, nativeQuery = true)
    List<DeliveryScheduleEntity> findByOutletId(int outletId);

    @Query(value = FIND_BY_Status, nativeQuery = true)
    List<DeliveryScheduleEntity> findByStatus(String status);
    
}
