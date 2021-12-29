package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.FitnessEquipment;


public interface FitnessEquipmentOperations extends JpaRepository<FitnessEquipment, String>{

}
