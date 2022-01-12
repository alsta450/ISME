package com.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.converter.JsonToClassConverter;
import com.entities.Branch;
import com.entities.BranchID;
import com.entities.Employee;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.entities.Visit;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.main.AController;
import com.operations.BranchOperations;
import com.operations.EmployeeOperations;
import com.operations.FitnessEquipmentOperations;
import com.operations.MemberOperations;
import com.operations.PersonOperations;
import com.operations.RoomOperations;
import com.operations.TrainingSessionOperations;
import com.operations.VisitOperations;
import com.report.BestTrainer;
import com.report.LoyalMember;
import com.sql.DatabaseHelper;

@Service
public class SQLController extends AController {
	private static Logger logger = LoggerFactory.getLogger(SQLController.class);
	private JsonToClassConverter jsonConverter = new JsonToClassConverter();
	private DatabaseHelper dbhelper = new DatabaseHelper();

	@Autowired
	PersonOperations personOperations;

	@Autowired
	BranchOperations branchOperations;

	@Autowired
	RoomOperations roomOperations;

	@Autowired
	FitnessEquipmentOperations fitnessEquipmentOperations;

	@Autowired
	EmployeeOperations employeeOperations;

	@Autowired
	MemberOperations memberOperations;
	/*
	 * @Autowired TutorOperations tutorOperations;
	 */
	@Autowired
	VisitOperations visitOperations;

	@Autowired
	TrainingSessionOperations trainingSessionOperations;

	@Override
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		logger.info("Creating Person...");
		try {
			JSONArray personArray = jsonConverter.stringToJsonArray("/person.json");
			Iterator<?> it = personArray.iterator();
			while (it.hasNext()) {
				personOperations.save(jsonConverter.jsonToPerson(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Creating Branch...");
		try {
			// Branch
			JSONArray branchArray = jsonConverter.stringToJsonArray("/branch.json");
			Iterator<?> it = branchArray.iterator();
			while (it.hasNext()) {
				branchOperations.save(jsonConverter.jsonToBranch(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Creating Room...");
		try {
			// Room
			JSONArray roomArray = jsonConverter.stringToJsonArray("/room.json");
			Iterator<?> it = roomArray.iterator();
			while (it.hasNext()) {
				roomOperations.save(jsonConverter.jsonToRoom(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating FitnessEquipment...");
		try {
			// FitnessEquipment
			JSONArray fitnessArray = jsonConverter.stringToJsonArray("/fitness_equipment.json");
			Iterator<?> it = fitnessArray.iterator();
			while (it.hasNext()) {
				fitnessEquipmentOperations.save(jsonConverter.jsonToFitnessEquipment(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Creating Employee...");
		try {
			// Employee
			JSONArray employeeArray = jsonConverter.stringToJsonArray("/employee.json");
			Iterator<?> it = employeeArray.iterator();
			while (it.hasNext()) {
				employeeOperations.save(jsonConverter.jsonToEmployee(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating Member...");
		try {
			// Member
			JSONArray memberArray = jsonConverter.stringToJsonArray("/member.json");
			Iterator<?> it = memberArray.iterator();
			while (it.hasNext()) {
				memberOperations.save(jsonConverter.jsonToMember(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating Visit...");
		try {
			// Visit
			JSONArray visitArray = jsonConverter.stringToJsonArray("/visit.json");
			Iterator<?> it = visitArray.iterator();
			while (it.hasNext()) {
				visitOperations.save(jsonConverter.jsonToVisit(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating TrainingSession...");
		try {
			// Visit
			JSONArray trainingSessionArray = jsonConverter.stringToJsonArray("/trainingsession.json");
			Iterator<?> it = trainingSessionArray.iterator();
			while (it.hasNext()) {
				trainingSessionOperations.save(jsonConverter.jsonToTrainingSession(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("Finished data inserts...");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@Override
	public List<Branch> getAllBranches(@RequestParam String db) {
		List<Branch> branch = branchOperations.findAll();
		return branch;
	}

	@Override
	public ResponseEntity<HttpStatus> registerForBranch(String city, String zip, String street, ObjectNode objectNode,
			@RequestParam String db) {
		try {
			visitOperations.save(new Visit(objectNode.get("svnr").asLong(), street, city, zip));
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.toString());
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@Override
	public List<PersonNameSvnr> getEmployeeForBranch(String city, String zip, String street, @RequestParam String db) {
		List<Employee> employeeList = employeeOperations.findAllByCity(city);
		List<PersonNameSvnr> returnList = new ArrayList<PersonNameSvnr>();
		for (Employee e : employeeList) {

			Person p = personOperations.getById(e.getSvnr());

			returnList.add(new PersonNameSvnr(p.getSvnr(), p.getFirstName()));
		}
		return returnList;
	}

	@Override
	public List<Branch> getMemberRegistrations(ObjectNode objectNode, @RequestParam String db) {
		List<Visit> visitList = visitOperations.findAllByMemberSvnr(objectNode.get("svnr").asLong());
		ArrayList<Branch> branchList = new ArrayList<Branch>();
		for (Visit visit : visitList) {
			Branch b = branchOperations.getById(new BranchID(visit.getStreet(), visit.getCity(), visit.getZip()));
			branchList.add(new Branch(b.getStreet(), b.getCity(), b.getZip(), b.getName(), b.getArea()));
		}

		return branchList;
	}

	@Override
	public List<TrainingSessionsForMember> getMemberTrainingSessions(ObjectNode objectNode, @RequestParam String db) {
		ResultSet rs = dbhelper.getTrainingSessions(objectNode.get("svnr").asText());

		return handleTrainingRequest(rs);
	} 

	@Override
	public Person login(ObjectNode objectNode, @RequestParam String db) {
		Optional<Person> person = personOperations.findOneByPasswordAndUsername(objectNode.get("password").asText(),
				objectNode.get("username").asText());
		if (person.isPresent()) {

			if (memberOperations.existsById(person.get().getSvnr())) {
				person.get().setRole("member");
				return person.get();
			} else if (employeeOperations.existsById(person.get().getSvnr())) {
				person.get().setRole("employee");
				return person.get();
			}
		} 
		return null;
	}

	@Override
	public Collection<BestTrainer> getTopTrainers(@RequestParam String db) {
		ResultSet rs = dbhelper.getBestTrainers();
		Collection<BestTrainer> trainerList = handleTrainerRequest(rs);
		return trainerList;
	}

	@Override
	public Collection<LoyalMember> getLoyalMembers(@RequestParam String db) {
		ResultSet rs = dbhelper.getLoyalMember();
		Collection<LoyalMember> memberList = handleLoyalMemberRequest(rs);
		return memberList;
	}

	@Override
	public ResponseEntity<HttpStatus> migrateToNosql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HttpStatus> booking(TrainingSession trainingSession, String db) {
		try {
			trainingSessionOperations.save(trainingSession);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

	}

	private List<TrainingSessionsForMember> handleTrainingRequest(ResultSet rs) {
		List<TrainingSessionsForMember> returnList = new ArrayList<TrainingSessionsForMember>();
		try {
			while (rs.next()) {
				int duration = rs.getInt(1);
				int price = rs.getInt(2);
				String memberName = rs.getString(3);
				String trainerName = rs.getString(4);
				returnList.add(new TrainingSessionsForMember(memberName, trainerName, price, duration));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;

	}

	private Collection<BestTrainer> handleTrainerRequest(ResultSet rs) {
		Map<Long, BestTrainer> trainerMap = new HashMap<Long, BestTrainer>();

		try {
			while (rs.next()) {
				long employee_svnr = rs.getLong(1);

				if (trainerMap.containsKey(employee_svnr)) {
					trainerMap.get(employee_svnr).addMemberName(rs.getString(6));
				} else {
					trainerMap.put(employee_svnr, new BestTrainer(rs.getString(7), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(8), rs.getInt(9)));
					trainerMap.get(employee_svnr).addMemberName(rs.getString(6));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return trainerMap.values();

	}

	private Collection<LoyalMember> handleLoyalMemberRequest(ResultSet rs) {
		Map<Long, LoyalMember> memberMap = new HashMap<Long, LoyalMember>();

		try {
			while (rs.next()) {
				long member_svnr = rs.getLong(2);

				if (memberMap.containsKey(member_svnr)) {
					memberMap.get(member_svnr).addTrainerName(rs.getString(10));
				} else {
					memberMap.put(member_svnr, new LoyalMember(rs.getString(8), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(9), rs.getInt(6), rs.getInt(7)));
					memberMap.get(member_svnr).addTrainerName(rs.getString(10));
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return memberMap.values();

	}
}
