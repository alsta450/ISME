package com.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entities.Room;

public class SqlToNosqlConverter {
	private static Logger logger = LoggerFactory.getLogger(SqlToNosqlConverter.class);
	private Map<Integer,ObjectId> trainingsIds = new HashMap<Integer,ObjectId>();

	public List<Document> migrateBranch(ResultSet branchAndEmployee, ResultSet branchAndEquipment,
			List<Room> roomList) {
		logger.info("creating branch for nosql");
		List<Document> branch = new ArrayList<Document>();
		boolean skip = false;
		try {
			while (branchAndEmployee.next()) {
				skip = false;

				for (Document d : branch) {
					if (d.get("_id",Document.class).get("city").equals(branchAndEmployee.getString("city"))
							&& d.get("_id",Document.class).get("street").equals(branchAndEmployee.getString("street"))
							&& d.get("_id",Document.class).get("zip").equals(branchAndEmployee.getString("zip"))) {
						((ArrayList<Long>) d.getList("employee_svnr", Long.class))
								.add(branchAndEmployee.getLong("svnr"));
						skip = true;
						continue;
					}

				}
				if (!skip) {
					branch.add(new Document("_id",new Document().append("city", branchAndEmployee.getString("city"))
							.append("zip", branchAndEmployee.getString("zip"))
							.append("street", branchAndEmployee.getString("street")))
							.append("area", branchAndEmployee.getInt("area"))
							.append("name", branchAndEmployee.getString("name")).append("employee_svnr",
									new ArrayList<>(Arrays.asList(branchAndEmployee.getLong("svnr")))));
				}
			}
			while (branchAndEquipment.next()) {
				for (Document d : branch) {
					if (d.get("_id",Document.class).get("city").equals(branchAndEquipment.getString("city"))
							&& d.get("_id",Document.class).get("street").equals(branchAndEquipment.getString("street"))
							&& d.get("_id",Document.class).get("zip").equals(branchAndEquipment.getString("zip"))) {
						if (d.containsKey("fitness_equipments")) {
							((ArrayList<String>) d.getList("fitness_equipments", String.class))
									.add(branchAndEquipment.getString("description"));
						} else {
							d.append("fitness_equipments",
									new ArrayList<>(Arrays.asList(branchAndEquipment.getString("description"))));
						}
						continue;
					}
				}
			}

			for (Room room : roomList) {
				for (Document d : branch) {
					if (d.get("_id",Document.class).get("city").equals(room.getCity()) && d.get("_id",Document.class).get("street").equals(room.getStreet())
							&& d.get("_id",Document.class).get("zip").equals(room.getZip())) {
						if (d.containsKey("rooms")) {
							((ArrayList<Document>) d.getList("rooms", Document.class))
									.add(new Document().append("room_number", room.getRoomNumber())
											.append("interior", room.getInterior()).append("name", room.getName()));
						} else {
							d.append("rooms",
									new ArrayList<>(Arrays.asList(new Document()
											.append("room_number", room.getRoomNumber())
											.append("interior", room.getInterior()).append("name", room.getName()))));
						}
					}
					continue;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branch;
	}

	public List<Document> migrateTrainingsession(ResultSet trainingSessionAndName) {
		List<Document> trainingSessionsList = new ArrayList<Document>();
		try {
			while (trainingSessionAndName.next()) {
				ObjectId id = new ObjectId();
				trainingsIds.put(trainingSessionAndName.getInt("trainings_id"), id);
				trainingSessionsList
						.add(new Document("_id",id)
								.append("duration", trainingSessionAndName.getInt("duration"))
								.append("price", trainingSessionAndName.getInt("price")).append("employee",
										new Document().append("svnr", trainingSessionAndName.getLong("employee_svnr"))
												.append("firstname", trainingSessionAndName.getString("firstname"))));
			}
			return trainingSessionsList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<Document> migrateMember(ResultSet member) {
		List<Document> memberList = new ArrayList<Document>();
		boolean skipOuter = false;
		try {
			while (member.next()) {
				skipOuter = false;
				for (Document d : memberList) {
					if (d.get("_id").toString().equals(member.getString("svnr"))) {
						if(member.getInt("trainings_id")!=0) {
						if (!d.getList("trainings_id", ObjectId.class).contains( trainingsIds.get(member.getInt("trainings_id")))) {
							d.getList("trainings_id", ObjectId.class).add(trainingsIds.get(member.getInt("trainings_id")));
						}
						}
						boolean skip = false;
						for (Document branch : d.getList("branch_id", Document.class)) {
							if (branch.get("city").equals(member.getString("city"))) {
								skip = true;
							}
						}
						if (!skip) {
							d.getList("branch_id", Document.class)
									.add(new Document().append("city", member.getString("city"))
											.append("zip", member.getString("zip"))
											.append("street", member.getString("street")));
						}
						skipOuter = true;
						continue;
					}
				}
				if (!skipOuter) {
					memberList.add(new Document("_id",member.getLong("svnr"))
							.append("abo", member.getString("abo")).append("fee", member.getInt("fee"))
							.append("birthday", member.getDate("birthday"))
							.append("firstname", member.getString("firstname"))
							.append("lastname", member.getString("lastname")).append("iban", member.getString("iban"))
							.append("password", member.getString("password"))
							.append("username", member.getString("username"))
							.append("trainings_id", new ArrayList<>(Arrays.asList( trainingsIds.get(member.getInt("trainings_id")))))
							.append("branch_id", new ArrayList<>(Arrays.asList(new Document()
									.append("city", member.getString("city")).append("zip", member.getString("zip"))
									.append("street", member.getString("street"))))));
				}
			}
			return memberList;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	public List<Document> migrateEmployeeAndTutor(ResultSet employee) {
		List<Document> employeeList = new ArrayList<Document>();
		try {
			while (employee.next()) {
				boolean skip = false;
				for (Document d : employeeList) {
					if (d.get("_id").toString().equals(employee.getString("svnr"))) {
						d.getList("tutor", Long.class).add(employee.getLong("tutor"));
						skip = true;
						continue;
					}
				}
				if (!skip) {
					employeeList.add(new Document("_id",employee.getLong("svnr"))
							.append("hours", employee.getInt("hours"))
							.append("qualification", employee.getString("qualification"))
							.append("wage", employee.getInt("wage")).append("birthday", employee.getDate("birthday"))
							.append("firstname", employee.getString("firstname"))
							.append("iban", employee.getString("iban"))
							.append("lastname", employee.getString("lastname"))
							.append("password", employee.getString("password"))
							.append("username", employee.getString("username"))
							.append("tutor", new ArrayList<>(Arrays.asList(employee.getLong("tutor")))));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employeeList;

	}
}
