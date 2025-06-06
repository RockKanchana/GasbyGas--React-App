package com.lmu.gasbygas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmu.gasbygas.entity.ClientEntity;

public interface ClientRepo extends JpaRepository<ClientEntity,Integer> {

    String FIND_BY_EMAIL = "SELECT * FROM client WHERE email = ?1";
    String FIND_BY_USERNAME_AND_STATUS = "SELECT * FROM client WHERE email = ?1 AND status = ?2";
    String FIND_BY_USERID = "SELECT * FROM client WHERE user_id = ?1";
    String FIND_BY_CLIENTID = "SELECT * FROM client WHERE id = ?1";
    String FIND_BY_STATUS = "SELECT * FROM client WHERE status = ?1";

    @Query(value = FIND_BY_EMAIL, nativeQuery = true)
    ClientEntity findClientByEmail(String email);

    @Query(value = "SELECT * FROM client WHERE email = ?1 OR contact = ?2 OR nic = ?3 OR reg_number = ?4", nativeQuery = true)
    ClientEntity findClientByEmailOrContactOrNicOrRegNumber(String email, String contact, String nic, String registrationNumber);

    @Query(value = FIND_BY_USERNAME_AND_STATUS, nativeQuery = true)
    ClientEntity findClientByUsernameAndStatus(String username, int i);

    @Query(value = FIND_BY_USERID, nativeQuery = true)
    ClientEntity findClientByUserId(int id);

    @Query(value = FIND_BY_STATUS, nativeQuery = true)
    List<ClientEntity> findAllByStatus(int status);

    @Query(value = FIND_BY_CLIENTID, nativeQuery = true)
    ClientEntity findClientByClientId(int clientId);
    
}
