package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Visit;
import com.entities.VisitID;

public interface VisitOperations extends JpaRepository<Visit,VisitID>{

}
