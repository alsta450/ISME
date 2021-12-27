package db;

import java.sql.*;

import org.springframework.data.jpa.repository.JpaRepository;
public class DatabaseHelper {

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

	public void insertBranch() {
		try {
			String street = "teststreet";
			String city = "testcity";
			String zip = "1234";
			String name = "testname";
			String area = "50";

			String sql = "INSERT INTO BRANCH (STREET, CITY, ZIP, NAME, AREA) VALUES ('" + street + "','" + city + "','"
					+ zip + "','" + name + "','" + area + "')";
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createBranchTable() {
		String createTable = "CREATE TABLE Branch(" + "street VARCHAR(30)," + "city VARCHAR(30)," + "zip CHAR(4),"
				+ "name VARCHAR(15)," + "area VARCHAR(10)," + "PRIMARY KEY(street,city,zip)" + ");";

		try {
			statement.execute(createTable);
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
