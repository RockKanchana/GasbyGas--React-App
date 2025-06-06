package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lmu.gasbygas.entity.TokenEntity;

public interface TokenRepo extends JpaRepository<TokenEntity, Integer> {

    String FIND_BY_REQUESTID = "SELECT * FROM token WHERE request_id = ?1";

    @Query("SELECT t FROM TokenEntity t WHERE t.request.outlet IN " +
            "(SELECT d.outlet FROM DeliveryScheduleEntity d WHERE d.scheduleId = :scheduleId)")
    List<TokenEntity> findByScheduleId(@Param("scheduleId") int scheduleId);

    @Query(value = FIND_BY_REQUESTID, nativeQuery = true)
    TokenEntity findByRequestId(int id);

}
