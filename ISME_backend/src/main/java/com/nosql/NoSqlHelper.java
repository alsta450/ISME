package com.nosql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;

public class NoSqlHelper {
	private MongoClient mongoClient;
	private MongoDatabase database;

	public NoSqlHelper() {
		this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		this.database = mongoClient.getDatabase("fitness_center");
	}

	public void test() {
		database.getCollection("branch");
	}

	public void createCollection(List<Document> inserts, String name) {
		MongoCollection<Document> collection = database.getCollection(name);
		collection.insertMany(inserts);
	}

	public Iterable<Document> getCollection(String name) {
		MongoCollection<Document> collection = database.getCollection(name);
		return collection.find();
	}

	public void bookSession(TrainingSession trainingSession) {
		MongoCollection<Document> collectionTraining = database.getCollection("training_session");
		MongoCollection<Document> collectionEmployee = database.getCollection("employee");
		Document query = new Document("_id", trainingSession.getEmployeeSvnr());
		Document name = collectionEmployee.find(query).first();

		Document d = new Document().append("duration", trainingSession.getDuration())
				.append("price", trainingSession.getPrice()).append("employee", new Document()
						.append("svnr", trainingSession.getEmployeeSvnr()).append("firstname", name.get("firstname")));

		collectionTraining.insertOne(d);
	}

	public void registerForBranch(String city, String zip, String street, ObjectNode objectNode) {
		MongoCollection<Document> memberCollection = database.getCollection("member");
		Document query = new Document("_id", objectNode.get("svnr").asLong());
		Document member = memberCollection.find(query).first();
		// Bson setUpdate = set("session.ps.$[elem].apn", "newValue");
		memberCollection.findOneAndUpdate(query, new Document("$push", new Document("branch_id",
				new Document().append("city", city).append("zip", zip).append("street", street))));
		// member.getList("branch_id",Document.class).add(new
		// Document().append("city",city).append("zip",zip).append("street",street));
	}

	public Person login(ObjectNode objectNode, String db) {
		MongoCollection<Document> employeeCollection = database.getCollection("employee");
		MongoCollection<Document> memberCollection = database.getCollection("member");

		Document member = memberCollection.aggregate(
				Arrays.asList(Aggregates.match(new Document().append("username", objectNode.get("username").asText())
						.append("password", objectNode.get("password").asText()))))
				.first();
		Document employee = employeeCollection.aggregate(
				Arrays.asList(Aggregates.match(new Document().append("username", objectNode.get("username").asText())
						.append("password", objectNode.get("password").asText()))))
				.first();

		if (member != null) {
			java.util.Date date = member.getDate("birthday");
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			Person memberReturn = new Person(member.getLong("_id"), member.getString("firstname"),
					member.getString("lastname"), member.getString("iban"), sqlDate);
			memberReturn.setRole("member");
			return memberReturn;
		} else {
			java.util.Date date = employee.getDate("birthday");
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			Person employeeReturn = new Person(employee.getLong("_id"), employee.getString("firstname"),
					employee.getString("lastname"), employee.getString("iban"), sqlDate);
			employeeReturn.setRole("employee");
			return employeeReturn;
		}
	}

	public List<PersonNameSvnr> getEmployeeForBranch(String city, String zip, String street, String db) {
		String s = "db.branch.aggregate([\r\n"
				+ "  {$match: {_id:{city:\"Beicang\",zip:\"4721\",street:\"Manufacturers\"}}},\r\n" + "  {\r\n"
				+ "  $unwind: \"$employee_svnr\"\r\n" + "},\r\n" + "        {\r\n" + "            $lookup:\r\n"
				+ "            {\r\n"
				+ "                from: \"employee\",\r\n"
				+ "                localField: \"employee_svnr\",\r\n" + "                foreignField: \"_id\",\r\n"
				+ "                as: \"employee_data\"\r\n" + "            }\r\n"
				+ "        },{  $unwind: \"$employee_data\"}\r\n" + "])";
		
		List<PersonNameSvnr> returnList = new ArrayList<PersonNameSvnr>();
		MongoCollection<Document> branchCollection = database.getCollection("branch");
		List<Document> d = new ArrayList<Document>();
		branchCollection.aggregate(Arrays.asList(Aggregates.match(
				new Document("_id", new Document().append("city", city).append("zip", zip).append("street", street))),
				Aggregates.unwind("$employee_svnr"),  new Document("$lookup",new Document().append("from", "employee").append("localField", "employee_svnr").append("foreignField", "_id").append("as", "employee_data")), Aggregates.unwind("$employee_data"))).into(d);
		for(Document employee : d) {
			System.out.println(employee.toJson());
			returnList.add(new PersonNameSvnr(employee.get("employee_data", Document.class).get("_id",Long.class),employee.get("employee_data", Document.class).get("firstname",String.class))  );
		}

		return returnList;

	}

}
