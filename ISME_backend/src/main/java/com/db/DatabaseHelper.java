package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper implements CreateTable {

	private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/fitness_center";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	private static Statement statement;
	private static Connection connection;

	public DatabaseHelper() {
		try {

			connection = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createBranchTable() {
		String createTable = "CREATE TABLE Branch(" + "street VARCHAR(30)," + "city VARCHAR(30)," + "zip CHAR(4),"
				+ "name VARCHAR(40)," + "area VARCHAR(10)," + "PRIMARY KEY(street,city,zip)" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createPersonTable() {
		String createTable = "CREATE TABLE Person(" + "svnr BIGINT PRIMARY KEY," + "lastname VARCHAR(20) NOT NULL,"
				+ "firstname VARCHAR(20) NOT NULL," + "birthday DATE NOT NULL," +"username VARCHAR(20),"+"password VARCHAR(20)," + "iban VARCHAR(50) UNIQUE NOT NULL"
				+ ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createRoomTable() {
		String createTable = "CREATE TABLE Room(" + "room_number INTEGER," + "street VARCHAR(30)," + "city VARCHAR(30),"
				+ "zip CHAR(4)," + "name VARCHAR(15)," + "interior VARCHAR(20),"
				+ "PRIMARY KEY(room_number,street,city,zip),"
				+ "FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createFitnessEquipmentTable() {
		String createTable = "CREATE TABLE Fitness_equipment(\r\n" + "description VARCHAR(30) PRIMARY KEY,\r\n"
				+ "muscle_group VARCHAR(25),\r\n" + "fitness_type VARCHAR(10),\r\n" + "street VARCHAR(30),\r\n"
				+ "city VARCHAR(30),\r\n" + "zip CHAR(4),\r\n"
				+ "FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createTutorTable() {
		String createTable = "CREATE TABLE Tutor(\r\n" + "employee_svnr BIGINT,\r\n" + "svnr BIGINT,\r\n"
				+ "FOREIGN KEY(employee_svnr) REFERENCES Employee(svnr) ON DELETE CASCADE,\r\n"
				+ "FOREIGN KEY(svnr) REFERENCES Employee(svnr) ON DELETE CASCADE\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createMemberTable() {
		String createTable = "CREATE TABLE Member(\r\n" + "svnr BIGINT PRIMARY KEY, \r\n" + "abo VARCHAR(30),\r\n"
				+ "fee Integer,\r\n" +"role CHAR(6)," + "FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createEmployeeTable() {
		String createTable = "CREATE TABLE Employee(\r\n" + "svnr BIGINT PRIMARY KEY, \r\n" + "hours INTEGER,\r\n"
				+ "wage INTEGER CHECK(wage >900),\r\n" + "qualification VARCHAR(30),\r\n" + "street VARCHAR(30),\r\n"
				+ "city VARCHAR(30),\r\n" + "zip CHAR(4),\r\n" +"role CHAR(6)," 
				+ "FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE,\r\n"
				+ "FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createTrainingSessionTable() {
		String createTable = "CREATE TABLE Training_session(\r\n" + "trainings_id INTEGER PRIMARY KEY,\r\n"
				+ "price INTEGER NOT NULL,\r\n" + "duration INTEGER NOT NULL,\r\n" + "employee_svnr BIGINT, \r\n"
				+ "member_svnr BIGINT, \r\n"
				+ "FOREIGN KEY(employee_svnr) REFERENCES Employee(svnr) ON DELETE CASCADE,\r\n"
				+ "FOREIGN KEY(member_svnr) REFERENCES Member(svnr) ON DELETE CASCADE\r\n" + "\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void createVisitTable() {
		String createTable = "CREATE TABLE visit(\r\n" + "member_svnr BIGINT, \r\n" + "street VARCHAR(30),\r\n"
				+ "city VARCHAR(30),\r\n" + "zip CHAR(7),\r\n" + "PRIMARY KEY(member_svnr,street, city, zip),\r\n"
				+ "FOREIGN KEY(member_svnr) REFERENCES Member(svnr) ON DELETE CASCADE,\r\n"
				+ "FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE\r\n" + ");";

		try {
			statement.execute(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public ResultSet getBestTrainers() {
		String getBestTrainers = "SELECT \r\n"
				+ "    employee_svnr,\r\n"
				+ "    member_svnr,\r\n"
				+ "    zip,\r\n"
				+ "    street,\r\n"
				+ "    city,\r\n"
				+ "    member_name,\r\n"
				+ "    employee_name,\r\n"
				+ "    name,\r\n"
				+ "    Total_sessions\r\n"
				+ "FROM\r\n"
				+ "    (SELECT \r\n"
				+ "        *\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        *\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        employee_svnr,\r\n"
				+ "            member_svnr,\r\n"
				+ "            street AS 'Street_employee',\r\n"
				+ "            city AS 'City_employee',\r\n"
				+ "            zip AS 'ZIP_employee',\r\n"
				+ "            Total_sessions\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        popular_trainer.employee_svnr, member_svnr, Total_sessions\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        employee_svnr, Total_sessions\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        * , COUNT(employee_svnr) AS 'Total_sessions'\r\n"
				+ "    FROM\r\n"
				+ "        training_session\r\n"
				+ "    INNER JOIN employee ON training_session.employee_svnr = employee.svnr\r\n"
				+ "    GROUP BY employee_svnr\r\n"
				+ "    ORDER BY COUNT(*) DESC) AS popular_trainer\r\n"
				+ "    GROUP BY street , city , zip) AS popular_trainer\r\n"
				+ "    INNER JOIN training_session ON training_session.employee_svnr = popular_trainer.employee_svnr) AS popular_trainer\r\n"
				+ "    INNER JOIN Employee ON Employee.svnr = popular_trainer.employee_svnr) AS popular_trainer\r\n"
				+ "    INNER JOIN (SELECT \r\n"
				+ "        zip, street, city, name\r\n"
				+ "    FROM\r\n"
				+ "        Branch) AS Branch_alias ON Branch_alias.zip = popular_trainer.ZIP_employee\r\n"
				+ "        AND Branch_alias.street = popular_trainer.Street_employee\r\n"
				+ "        AND Branch_alias.city = popular_trainer.City_employee) AS popular_trainer\r\n"
				+ "    INNER JOIN (SELECT \r\n"
				+ "        Person.firstname AS 'member_name', SVNR\r\n"
				+ "    FROM\r\n"
				+ "        Person) AS Person_alias ON popular_trainer.member_svnr = Person_alias.Svnr) AS popular_trainer\r\n"
				+ "        INNER JOIN\r\n"
				+ "    (SELECT \r\n"
				+ "        Person.firstname AS 'employee_name', SVNR\r\n"
				+ "    FROM\r\n"
				+ "        Person) AS Person_alias2 ON popular_trainer.employee_svnr = Person_alias2.Svnr ORDER BY zip,street,city;";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(getBestTrainers);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public ResultSet getLoyalMember() {
		String loyalMembers = "SELECT \r\n"
				+ "    t.employee_svnr,\r\n"
				+ "    t.member_svnr,\r\n"
				+ "    t.zip,\r\n"
				+ "    t.city,\r\n"
				+ "    t.street,\r\n"
				+ "    t.total_price,\r\n"
				+ "    t.total_sessions,\r\n"
				+ "    t.member_firstname,\r\n"
				+ "    name,\r\n"
				+ "    Person.firstname AS trainer_firstname\r\n"
				+ "FROM\r\n"
				+ "    (SELECT \r\n"
				+ "        training_session.employee_svnr,\r\n"
				+ "            t.member_svnr,\r\n"
				+ "            t.zip,\r\n"
				+ "            t.city,\r\n"
				+ "            t.street,\r\n"
				+ "            t.total_price,\r\n"
				+ "            t.total_sessions,\r\n"
				+ "            t.member_firstname,\r\n"
				+ "            name\r\n"
				+ "    FROM\r\n"
				+ "        Training_session\r\n"
				+ "    INNER JOIN (SELECT \r\n"
				+ "        employee_svnr,\r\n"
				+ "            member_svnr,\r\n"
				+ "            t.zip,\r\n"
				+ "            t.city,\r\n"
				+ "            t.street,\r\n"
				+ "            name,\r\n"
				+ "            member_firstname,\r\n"
				+ "            total_sessions,\r\n"
				+ "            total_price\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        *\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        *\r\n"
				+ "    FROM\r\n"
				+ "        (SELECT \r\n"
				+ "        employee_svnr,\r\n"
				+ "            member_svnr,\r\n"
				+ "            ZIP,\r\n"
				+ "            street,\r\n"
				+ "            city,\r\n"
				+ "            COUNT(member_svnr) AS total_sessions,\r\n"
				+ "            SUM(price) AS total_price\r\n"
				+ "    FROM\r\n"
				+ "        training_session\r\n"
				+ "    INNER JOIN employee ON training_session.employee_svnr = employee.svnr\r\n"
				+ "    GROUP BY zip , street , city , member_svnr\r\n"
				+ "    ORDER BY COUNT(member_svnr) DESC) AS t\r\n"
				+ "    GROUP BY ZIP , street , city\r\n"
				+ "    ORDER BY total_price DESC) AS t\r\n"
				+ "    INNER JOIN (SELECT \r\n"
				+ "        firstname AS member_firstname, svnr\r\n"
				+ "    FROM\r\n"
				+ "        Person) AS person_alias ON member_svnr = person_alias.svnr) AS t\r\n"
				+ "    INNER JOIN Branch ON Branch.zip = t.zip\r\n"
				+ "        AND Branch.street = t.street\r\n"
				+ "        AND Branch.city = t.city) AS t ON t.member_svnr = training_session.member_svnr\r\n"
				+ "    INNER JOIN employee ON Employee.svnr = training_session.employee_svnr\r\n"
				+ "        AND t.zip = employee.zip) AS t\r\n"
				+ "        INNER JOIN\r\n"
				+ "    Person ON Person.svnr = t.employee_svnr ORDER BY Total_Price DESC;";
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(loyalMembers);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
