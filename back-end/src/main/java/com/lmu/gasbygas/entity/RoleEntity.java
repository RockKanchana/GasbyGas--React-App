package com.lmu.gasbygas.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",columnDefinition = "INT")
    private int roleId;

    @Column(name = "role_name")
    private String name;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<UserEntity> users;

    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<ClientEntity> client;
    
}
