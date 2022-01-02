package com.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.converter.JsonToClassConverter;
import com.db.DatabaseHelper;
import com.entities.Branch;
import com.entities.BranchID;
import com.entities.FitnessEquipment;
import com.entities.Member;
import com.entities.TrainingSession;
import com.entities.Visit;
import com.operations.BranchOperations;
import com.operations.EmployeeOperations;
import com.operations.FitnessEquipmentOperations;
import com.operations.MemberOperations;
import com.operations.PersonOperations;
import com.operations.RoomOperations;
import com.operations.TrainingSessionOperations;
import com.operations.TutorOperations;
import com.operations.VisitOperations;
import com.report.BestTrainer;

@RestController
@RequestMapping(value = "/fitness")
public class FitnessCenterController {
	private static Logger logger = LoggerFactory.getLogger(FitnessCenterController.class);
	private JsonToClassConverter jsonConverter = new JsonToClassConverter();
	DatabaseHelper dbhelper = new DatabaseHelper();

	
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

	@Autowired
	TutorOperations tutorOperations;

	@Autowired
	VisitOperations visitOperations;

	@Autowired
	TrainingSessionOperations trainingSessionOperations;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		dbhelper.createBranchTable();
		dbhelper.createPersonTable();
		dbhelper.createRoomTable();
		dbhelper.createFitnessEquipmentTable();
		dbhelper.createEmployeeTable();
		dbhelper.createTutorTable();
		dbhelper.createMemberTable();
		dbhelper.createVisitTable();
		dbhelper.createTrainingSessionTable();
		
		logger.info("Creating Person...");
		try {
			// Person
			JSONArray personList = jsonConverter.readJson("src/main/resources/person.json");
			Iterator it = personList.iterator();
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
			JSONArray branchList = jsonConverter.readJson("src/main/resources/branch.json");
			Iterator it = branchList.iterator();
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
			JSONArray roomList = jsonConverter.readJson("src/main/resources/room.json");
			Iterator it = roomList.iterator();
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
			JSONArray fitnessEquipmentList = jsonConverter.readJson("src/main/resources/fitness_equipment.json");
			Iterator it = fitnessEquipmentList.iterator();
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
			JSONArray employeeList = jsonConverter.readJson("src/main/resources/employee.json");
			Iterator it = employeeList.iterator();
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
			JSONArray memberList = jsonConverter.readJson("src/main/resources/member.json");
			Iterator it = memberList.iterator();
			while (it.hasNext()) {
				memberOperations.save(jsonConverter.jsonToMember(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating Tutor...");
		try {
			// Tutor
			JSONArray tutorList = jsonConverter.readJson("src/main/resources/tutor.json");
			Iterator it = tutorList.iterator();
			while (it.hasNext()) {
				tutorOperations.save(jsonConverter.jsonToTutor(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Creating Visit...");
		try {
			// Visit
			JSONArray visitList = jsonConverter.readJson("src/main/resources/visit.json");
			Iterator it = visitList.iterator();
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
			JSONArray trainingSessionList = jsonConverter.readJson("src/main/resources/trainingsession.json");
			Iterator it = trainingSessionList.iterator();
			while (it.hasNext()) {
				trainingSessionOperations.save(jsonConverter.jsonToTrainingSession(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> booking(@RequestBody TrainingSession trainingSession) {
		try {
			logger.info("Received post request on /booking");
			trainingSessionOperations.save(trainingSession);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "branch/{city}/{zip}/{street}/equipment", method = RequestMethod.GET)
	public @ResponseBody FitnessEquipment getAllEquipment(@PathVariable String city, @PathVariable String zip,
			@PathVariable String street) {
		logger.info("Received get request on branch/{city}/{zip}/{street}/equipment");
		FitnessEquipment fitnessEquipment = fitnessEquipmentOperations.findAllByCityAndZipAndStreet(city, zip, street);
		System.out.println(fitnessEquipment.toString());
		return fitnessEquipment;

	}

	@RequestMapping(value = "branch/{city}/{zip}/{street}/register", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> registerForBranch(@PathVariable String city, @PathVariable String zip,
			@PathVariable String street, @RequestBody Member member) {
		logger.info("Received post request on branch/{city}/{zip}/{street}/register");
		visitOperations.save(new Visit(member.getSvnr(), street, city, zip));
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@RequestMapping(value = "branch/{member}", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Branch> getMemberRegistrations(@PathVariable long member) {
		logger.info("Received get request on branch/{}", member);
		List<Visit> visitList = visitOperations.findAllByMemberSvnr(member);
		ArrayList<Branch> branchList = new ArrayList<Branch>();
		for (Visit visit : visitList) {
			Branch b = branchOperations.getById(new BranchID(visit.getStreet(), visit.getCity(), visit.getZip()));
			branchList.add(new Branch(b.getStreet(), b.getCity(), b.getZip(), b.getName(), b.getArea()));
		}

		return branchList;

	}

	@RequestMapping(value = "trainingsession/{member}", method = RequestMethod.GET)
	public @ResponseBody List<TrainingSession> getMemberTrainingSessions(@PathVariable long member) {

		logger.info("Received get request on trainingsession/{}", member);
		List<TrainingSession> allMemberSession = trainingSessionOperations.findAllByMemberSvnr(member);

		return allMemberSession;

	}

	//TODO
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> login(@RequestBody String username, @RequestBody String password) {
		logger.info("Received post request on login");
		// TODO Schauen ob existiert, wenn ja dann return employee/member wenn nein
		// HttpStatus.NOT_ACCEPTABLE

		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	//TODO Report 1 Endpoint
	
	@RequestMapping(value = "top-trainers", method = RequestMethod.GET)
	public List<BestTrainer> getTopTrainers() {
		
		ResultSet rs = dbhelper.getBestTrainers();
	      try {
			while (rs.next()) {
			      long employee_svnr = rs.getLong(1);
			      long member_svnr = rs.getLong(2);
			      String zip = rs.getString(3);
			      String street= rs.getString(4);
			      String city= rs.getString(5);
			      String member_name= rs.getString(6);
			      String employee_name= rs.getString(7);
			      String name= rs.getString(8);
			      int total_sessions= rs.getInt(9);
			      System.out.println(employee_svnr + ", " + member_svnr + ", " + zip +
			                         ", " + street + ", " + city+ ", " + member_name+ ", " + employee_name+ ", " + name+ ", " + total_sessions);
			    }
		} catch (SQLException e) {
			// TODO Class List<best_trainer> list; list[0]  best_trainer.getMember_SVNR -> returns List<String>
			e.printStackTrace();
		}
		
		return new ArrayList<BestTrainer> ();

	}
	

}
