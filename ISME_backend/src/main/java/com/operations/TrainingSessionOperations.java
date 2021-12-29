package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.TrainingSession;

public interface TrainingSessionOperations extends JpaRepository<TrainingSession,Integer>{

}
