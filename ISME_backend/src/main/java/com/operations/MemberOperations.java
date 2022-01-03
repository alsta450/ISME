package com.operations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Member;

public interface MemberOperations extends JpaRepository<Member,Long>{

}