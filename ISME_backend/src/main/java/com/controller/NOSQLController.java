package com.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entities.Branch;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.main.AController;
import com.nosql.NoSqlHelper;
import com.report.BestTrainer;
import com.report.LoyalMember;


@Service
public class NOSQLController extends AController {
	private NoSqlHelper noSQLHelper = new NoSqlHelper();
	
	@Override
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		//Not used
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> booking(TrainingSession trainingSession, String db) {
		try {
			noSQLHelper.bookSession(trainingSession);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<Branch> getAllBranches(String db) {
		Iterable<Document> branches = noSQLHelper.getCollection("branch");
		List<Branch> branchList = new ArrayList<Branch>();
		for(Document d : branches) {
			branchList.add(new Branch(d.getString("street"),d.getString("city"),d.getString("zip"),d.getString("name"),String.valueOf(d.getInteger("area"))));
		}
		return branchList;
	}

	@Override
	public ResponseEntity<HttpStatus> registerForBranch(String city, String zip, String street, ObjectNode objectNode,
			String db) {
		try {
			noSQLHelper.registerForBranch(city, zip, street, objectNode);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public List<PersonNameSvnr> getEmployeeForBranch(String city, String zip, String street, String db) {
		return noSQLHelper.getEmployeeForBranch(city, zip, street, db);
	}
 
	@Override
	public List<Branch> getMemberRegistrations(ObjectNode objectNode, String db) {
		return noSQLHelper.getMemberRegistrations(objectNode, db);
	}

	@Override
	public List<TrainingSessionsForMember> getMemberTrainingSessions(ObjectNode objectNode, String db) {
		return noSQLHelper.getMemberTrainingSessions(objectNode, db);
	}

	@Override
	public Person login(ObjectNode objectNode, String db) {
		return noSQLHelper.login(objectNode, db);
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
		// Not used
		return null;
	}

}
