package com.operations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.Visit;
import com.entities.VisitID;

public interface VisitOperations extends JpaRepository<Visit,VisitID>{
	public List<Visit> findAllByMemberSvnr(Long member_svnr);
}
