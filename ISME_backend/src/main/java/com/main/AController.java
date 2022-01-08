package com.main;

import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.entities.Branch;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.report.BestTrainer;
import com.report.LoyalMember;

public abstract class AController {
	abstract public ResponseEntity<HttpStatus> ceateAllEntries();

	abstract public ResponseEntity<HttpStatus> booking(@RequestBody TrainingSession trainingSession, @RequestParam String db);

	abstract public @ResponseBody List<Branch> getAllBranches(@RequestParam String db);

	abstract public ResponseEntity<HttpStatus> registerForBranch(@PathVariable String city, @PathVariable String zip,
			@PathVariable String street, @RequestBody ObjectNode objectNode,@RequestParam String db);

	abstract public @ResponseBody List<PersonNameSvnr> getEmployeeForBranch(@PathVariable String city,
			@PathVariable String zip, @PathVariable String street, @RequestParam String db);

	abstract public @ResponseBody List<Branch> getMemberRegistrations(@RequestBody ObjectNode objectNode, @RequestParam String db);

	abstract public List<TrainingSessionsForMember> getMemberTrainingSessions(@RequestBody ObjectNode objectNode, @RequestParam String db);

	abstract public Person login(@RequestBody ObjectNode objectNode, @RequestParam String db);

	abstract public Collection<BestTrainer> getTopTrainers(@RequestParam String db);

	abstract public Collection<LoyalMember> getLoyalMembers(@RequestParam String db);

	abstract public ResponseEntity<HttpStatus> migrateToNosql();

}
