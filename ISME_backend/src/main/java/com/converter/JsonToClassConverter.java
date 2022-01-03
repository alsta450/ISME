package com.converter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.entities.Branch;
import com.entities.Employee;
import com.entities.FitnessEquipment;
import com.entities.Member;
import com.entities.Person;
import com.entities.Room;
import com.entities.TrainingSession;
import com.entities.Tutor;
import com.entities.Visit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonToClassConverter {
	private ObjectMapper mapper = new ObjectMapper();

	public JSONArray readJson(String source) {
		Object obj;
		JSONArray ja = new JSONArray();
		try {
			obj = new JSONParser().parse(new FileReader(source));
			 ja = (JSONArray) obj;
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		} catch (IOException e1) {

			e1.printStackTrace();
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
		return ja;

	}

	public Person jsonToPerson(Object o) {
		try {
			Person person = mapper.readValue(o.toString(), Person.class);
			return person;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Branch jsonToBranch(Object o) {
		try {
			Branch branch= mapper.readValue(o.toString(), Branch.class);
			return branch;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Room jsonToRoom(Object o) {
		try {
			Room room= mapper.readValue(o.toString(), Room.class);
			return room;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FitnessEquipment jsonToFitnessEquipment(Object o) {
		try {
			FitnessEquipment fitnessEquipment= mapper.readValue(o.toString(), FitnessEquipment.class);
			return fitnessEquipment;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Employee jsonToEmployee(Object o) {
		try {
			Employee employee= mapper.readValue(o.toString(), Employee.class);
			return employee;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Member jsonToMember(Object o) {
		try {
			Member member= mapper.readValue(o.toString(), Member.class);
			return member;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Tutor jsonToTutor(Object o) {
		try {
			Tutor tutor= mapper.readValue(o.toString(), Tutor.class);
			return tutor;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Visit jsonToVisit(Object o) {
		try {
			Visit visit= mapper.readValue(o.toString(), Visit.class);
			return visit;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TrainingSession jsonToTrainingSession(Object o) {
		try {
			TrainingSession trainingSession= mapper.readValue(o.toString(), TrainingSession.class);
			return trainingSession;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Member jsonToNewMember(Object o) {
		try {
			Member member= mapper.readValue(o.toString(), Member.class);
			return member;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
