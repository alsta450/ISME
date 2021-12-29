package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Employee;

public interface EmployeeOperations extends JpaRepository<Employee, Long> {

}
