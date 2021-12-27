package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import entities.Person;
import operations.PersonOperations;


@RestController
@RequestMapping("/fitness")
public class FitnessCenterController {

	 @Autowired
	 PersonOperations personOpterations;
	
	  @GetMapping("/tutorials")
	  public ResponseEntity<Person> getPersonByID(@RequestParam(required = false) String title) {
		return null;
	  }

	 
	 
}
