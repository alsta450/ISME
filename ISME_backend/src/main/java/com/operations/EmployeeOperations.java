package com.operations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Employee;

public interface EmployeeOperations extends JpaRepository<Employee, Long> {
	public List<Employee> findAllByCity(String city);
}