package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.entities.Person;

@Service
public interface PersonOperations extends JpaRepository<Person, Long>{
}
