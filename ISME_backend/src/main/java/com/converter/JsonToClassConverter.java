package com.converter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.entities.Branch;
import com.entities.Person;
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
			o.toString();
			Person person = mapper.readValue(o.toString(), Person.class);
			return person;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Branch jsonToBranch(Object o) {
		try {
			o.toString();
			Branch branch= mapper.readValue(o.toString(), Branch.class);
			return branch;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
