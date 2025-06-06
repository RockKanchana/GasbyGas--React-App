package com.lmu.gasbygas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "request_details")
public class GasRequestDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "INT")
    private int detailId;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private GasRequestEntity request;

    @ManyToOne
    @JoinColumn(name = "gas_id")
    private GasEntity gas;

    @Column(name = "quantity")
    private int quantity;
    
}
