package main;

import db.DatabaseHelper;
import entities.Person;

public class Main {

	public static void main(String[] args) {

		
		DatabaseHelper dbh = new DatabaseHelper();
		dbh.insertBranch();
	}

}
