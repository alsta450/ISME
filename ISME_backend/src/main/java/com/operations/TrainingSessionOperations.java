package com.operations;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entities.TrainingSession;

public interface TrainingSessionOperations extends JpaRepository<TrainingSession,Integer>{
	public List<TrainingSession> findAllByMemberSvnr(Long member_svnr);
}
