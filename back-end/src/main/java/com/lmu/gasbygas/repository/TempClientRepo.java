package com.lmu.gasbygas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmu.gasbygas.entity.TempClientEntity;

public interface TempClientRepo extends JpaRepository<TempClientEntity,Integer> {

    TempClientEntity findByEmail(String email);
    
}
