package com.main;

import java.util.Collection;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.controller.NOSQLController;
import com.controller.SQLController;
import com.converter.SqlToNosqlConverter;
import com.entities.Branch;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nosql.NoSqlHelper;
import com.operations.RoomOperations;
import com.report.BestTrainer;
import com.report.LoyalMember;
import com.sql.DatabaseHelper;

@RestController
@CrossOrigin(origins = "http://localhost:8080/")
@RequestMapping(value = "/fitness")
public class FitnessCenterController extends AController {
	// private SQLController sqlController = new SQLController();
	private static Logger logger = LoggerFactory.getLogger(FitnessCenterController.class);
	private SqlToNosqlConverter sqlToNosqlConverter = new SqlToNosqlConverter();
	private DatabaseHelper dbhelper = new DatabaseHelper();
	private NoSqlHelper nosqlHelper = new NoSqlHelper();

	private static final String SQL = "SQL";
	@Autowired
	private SQLController sqlController;
	@Autowired
	private NOSQLController nosqlController;
/*
	@Autowired
	private PersonOperations personOperations;

	@Autowired
	private BranchOperations branchOperations;
*/
	@Autowired
	private RoomOperations roomOperations;
	/*
	 * @Autowired private FitnessEquipmentOperations fitnessEquipmentOperations;
	 */
	/*
	@Autowired
	private EmployeeOperations employeeOperations;

	@Autowired
	private MemberOperations memberOperations;

	@Autowired
	private VisitOperations visitOperations;
*/
	/*
	 * @Autowired private TrainingSessionOperations trainingSessionOperations;
	 */
	@Override
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		sqlController.ceateAllEntries();

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@Override
	@RequestMapping(value = "/booking", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> booking(@RequestBody TrainingSession trainingSession, @RequestParam String db) {
		logger.info("Received post request on /booking");
		if (db.equals(SQL)) {
			return sqlController.booking(trainingSession, db);
		} else {
			nosqlController.booking(trainingSession, db);
		}

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	/*
	 * @RequestMapping(value = "branch/{city}/{zip}/{street}/equipment", method =
	 * RequestMethod.GET) public @ResponseBody FitnessEquipment
	 * getAllEquipment(@PathVariable String city, @PathVariable String zip,
	 * 
	 * @PathVariable String street) {
	 * logger.info("Received get request on branch/{city}/{zip}/{street}/equipment"
	 * ); FitnessEquipment fitnessEquipment =
	 * fitnessEquipmentOperations.findAllByCityAndZipAndStreet(city, zip, street);
	 * 
	 * return fitnessEquipment;
	 * 
	 * }
	 */

	@Override
	@RequestMapping(value = "branch", method = RequestMethod.GET)
	public @ResponseBody List<Branch> getAllBranches(@RequestParam String db) {
		logger.info("Received get request on branch");
		if (db.equals(SQL)) {
			return sqlController.getAllBranches(db);
		} else {
			return nosqlController.getAllBranches(db);
		}

	}

	@Override
	@RequestMapping(value = "branch/{city}/{zip}/{street}/register", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> registerForBranch(@PathVariable String city, @PathVariable String zip,
			@PathVariable String street, @RequestBody ObjectNode objectNode, @RequestParam String db) {
		logger.info("Received post request on branch/{city}/{zip}/{street}/register/{}",
				objectNode.get("svnr").asText());
		if (db.equals(SQL)) {
			return sqlController.registerForBranch(city, zip, street, objectNode, db);
		} else {
			return nosqlController.registerForBranch(city, zip, street, objectNode, db);
		}

	}

	@Override
	@RequestMapping(value = "branch/{city}/{zip}/{street}/employee", method = RequestMethod.GET)
	public @ResponseBody List<PersonNameSvnr> getEmployeeForBranch(@PathVariable String city, @PathVariable String zip,
			@PathVariable String street, @RequestParam String db) {

		logger.info("Received get request on branch/{}/{}/{}/employee", city, zip, street);
		if (db.equals(SQL)) {
			return sqlController.getEmployeeForBranch(city, zip, street, db);
		} else {
			return nosqlController.getEmployeeForBranch(city, zip, street, db);
		}

	}

	@Override
	@RequestMapping(value = "branch/member", method = RequestMethod.POST)
	public @ResponseBody List<Branch> getMemberRegistrations(@RequestBody ObjectNode objectNode,
			@RequestParam String db) {
		logger.info("Received post request on branch/{}", objectNode.get("svnr").asText());
		if (db.equals(SQL)) {
			return sqlController.getMemberRegistrations(objectNode, db);
		} else {
			return nosqlController.getMemberRegistrations(objectNode, db);
		}

	}

	@Override
	@RequestMapping(value = "trainingsession", method = RequestMethod.POST)
	public List<TrainingSessionsForMember> getMemberTrainingSessions(@RequestBody ObjectNode objectNode,
			@RequestParam String db) {

		logger.info("Received get request on trainingsession/{}", objectNode.get("svnr").asText());
		if (db.equals(SQL)) {
			return sqlController.getMemberTrainingSessions(objectNode, db);
		} else {
			
			return nosqlController.getMemberTrainingSessions(objectNode, db);
		}
 
	}

	@Override
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Person login(@RequestBody ObjectNode objectNode, @RequestParam String db) {
		logger.info("Received post request on login");

		if (db.equals(SQL)) {
			return sqlController.login(objectNode, db);
		} else {
			return nosqlController.login(objectNode, db);

		}

	}

	@Override
	@RequestMapping(value = "top-trainers", method = RequestMethod.GET)
	public Collection<BestTrainer> getTopTrainers(@RequestParam String db) {
		logger.info("Received request on /top-trainers");
		if (db.equals(SQL)) {
			return sqlController.getTopTrainers(db);
		} else {
			return nosqlController.getTopTrainers(db);
		}

	}

	@Override
	@RequestMapping(value = "loyal-members", method = RequestMethod.GET)
	public Collection<LoyalMember> getLoyalMembers(@RequestParam String db) {
		logger.info("Received request on /loyal-members");
		if (db.equals(SQL)) {
			return sqlController.getLoyalMembers(db);
		} else {

			return nosqlController.getLoyalMembers(db);
		}

	}

	@Override
	@RequestMapping(value = "migrate", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> migrateToNosql() {
		List<Document> branchList = sqlToNosqlConverter.migrateBranch(dbhelper.getBranchAndEmployees(),
				dbhelper.getBranchAndEquipment(), roomOperations.findAll());
		nosqlHelper.createCollection(branchList, "branch");
		
		List<Document> trainingSessionList = sqlToNosqlConverter
				.migrateTrainingsession(dbhelper.getTrainingSessionAndName());
		nosqlHelper.createCollection(trainingSessionList, "training_session");
		nosqlHelper.indexing("training_session", "_id");
		List<Document> memberList = sqlToNosqlConverter.migrateMember(dbhelper.getMemberAndPersonAndTrainingAndVisit());
		nosqlHelper.createCollection(memberList, "member");
		nosqlHelper.indexing("member", "_id");
		List<Document> employeeList = sqlToNosqlConverter.migrateEmployeeAndTutor(dbhelper.getEmployeeAndTutor());
		nosqlHelper.createCollection(employeeList, "employee");
		nosqlHelper.indexing("employee", "_id");
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}