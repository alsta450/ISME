package com.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.entities.Branch;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.main.AController;
import com.report.BestTrainer;
import com.report.LoyalMember;

public class NOSQLController extends AController {

	@Override
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> booking(TrainingSession trainingSession, String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Branch> getAllBranches(String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> registerForBranch(String city, String zip, String street, ObjectNode objectNode,
			String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonNameSvnr> getEmployeeForBranch(String city, String zip, String street, String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Branch> getMemberRegistrations(ObjectNode objectNode, String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TrainingSessionsForMember> getMemberTrainingSessions(ObjectNode objectNode, String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Person login(ObjectNode objectNode, String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<BestTrainer> getTopTrainers(String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<LoyalMember> getLoyalMembers(String db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> migrateToNosql() {
		// TODO Auto-generated method stub
		return null;
	}

}
