package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Branch;
import com.entities.BranchID;

public interface BranchOperations extends JpaRepository<Branch, BranchID>{

}
