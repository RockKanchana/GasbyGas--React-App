package com.lmu.gasbygas.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "outlet")
public class OutletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "INT")
    private int outletId;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private OutletManagerEntity manager;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @Column(name = "contact")
    private String contact;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "outlet", cascade = CascadeType.ALL)
    private List<DeliveryScheduleEntity> schedules;
    
}
