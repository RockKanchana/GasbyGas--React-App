package com.lmu.gasbygas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lmu.gasbygas.entity.UserEntity;


@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {

    String FIND_BY_ID = "SELECT * FROM user WHERE id = ?1";

    UserEntity findFirstByEmail(String email);

    @Query(value = FIND_BY_ID, nativeQuery = true)
    UserEntity findUsertById(int id);

}