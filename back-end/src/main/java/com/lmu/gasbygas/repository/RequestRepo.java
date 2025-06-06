package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.GasRequestEntity;
import com.lmu.gasbygas.entity.GasRequestEntity.RequestStatus;

public interface RequestRepo extends JpaRepository<GasRequestEntity, Integer> {

    String FIND_BY_CLIENT_ID = "SELECT * FROM request WHERE client_id = ?1";
    String FIND_BY_OUTLET_ID = "SELECT * FROM request WHERE outlet_id = ?1";
    String CLIENT_EXIST = "SELECT EXISTS ( SELECT 1 FROM request WHERE client_id = ? AND status <> 'COMPLETED') AS request_exists";


    @Query(value = FIND_BY_CLIENT_ID, nativeQuery = true)
    List<GasRequestEntity> findAllByClientId(int clientId);

    @Query(value = CLIENT_EXIST, nativeQuery = true)
    boolean existsByClientIdAndStatusNot(int clientId, RequestStatus completed);

    @Query(value = FIND_BY_OUTLET_ID, nativeQuery = true)
    List<GasRequestEntity> findAllByOutletId(int id);

    long countByClient_ClientIdAndStatusNot(int clientId, GasRequestEntity.RequestStatus status);
    
}
