package com.main;



import java.util.Iterator;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.converter.JsonToClassConverter;
import com.db.DatabaseHelper;
import com.entities.Person;
import com.operations.BranchOperations;
import com.operations.PersonOperations;






@RestController
@RequestMapping(value ="/fitness")
public class FitnessCenterController {
	private static Logger logger = LoggerFactory.getLogger(FitnessCenterController.class);
	private DatabaseHelper dbhelper = new DatabaseHelper();
	private JsonToClassConverter jsonConverter = new JsonToClassConverter();
	
	@Autowired(required=true)
	PersonOperations personOperations;

	@Autowired
	BranchOperations branchOperations;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ResponseEntity<HttpStatus> ceateAllEntries() {
		logger.info("Creating Person...");
		try {
			//Person
			JSONArray personList = jsonConverter.readJson("src/main/resources/person.json");
			Iterator it = personList.iterator();
			while(it.hasNext()) {
				personOperations.save(jsonConverter.jsonToPerson(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {
			//Branch
			JSONArray branchList = jsonConverter.readJson("src/main/resources/branch.json");
			Iterator it = branchList.iterator();
			while(it.hasNext()) {
				branchOperations.save(jsonConverter.jsonToBranch(it.next()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

}
