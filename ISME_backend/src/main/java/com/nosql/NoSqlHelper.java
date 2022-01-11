package com.nosql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entities.Branch;
import com.entities.Person;
import com.entities.PersonNameSvnr;
import com.entities.TrainingSession;
import com.entities.TrainingSessionsForMember;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.main.FitnessCenterController;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Indexes;
import com.report.BestTrainer;
import com.report.LoyalMember;

public class NoSqlHelper {
	private MongoClient mongoClient;
	private MongoDatabase database;
	private static Logger logger = LoggerFactory.getLogger(NoSqlHelper.class);

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

	public void indexing(String collection, String key) {
		database.getCollection(collection).createIndex(Indexes.ascending(key));
	}

	public Iterable<Document> getCollection(String name) {
		MongoCollection<Document> collection = database.getCollection(name);
		return collection.find();
	}

	public void bookSession(TrainingSession trainingSession) {
		MongoCollection<Document> collectionTraining = database.getCollection("training_session");
		MongoCollection<Document> collectionEmployee = database.getCollection("employee");
		MongoCollection<Document> collectionMember = database.getCollection("member");
		Document query = new Document("_id", trainingSession.getEmployeeSvnr());
		Document name = collectionEmployee.find(query).first();

		ObjectId id = new ObjectId();

		Document d = new Document("_id", id).append("duration", trainingSession.getDuration())
				.append("price", trainingSession.getPrice()).append("employee", new Document()
						.append("svnr", trainingSession.getEmployeeSvnr()).append("firstname", name.get("firstname")));

		collectionTraining.insertOne(d);

		collectionMember.findOneAndUpdate(new Document("_id", trainingSession.getMemberSvnr()),
				new Document("$push", new Document("trainings_id", id)));

	}

	public void registerForBranch(String city, String zip, String street, ObjectNode objectNode) {
		MongoCollection<Document> memberCollection = database.getCollection("member");
		Document query = new Document("_id", objectNode.get("svnr").asLong());

		Document update = new Document("$addToSet", new Document("branch_id",
				new Document().append("city", city).append("zip", zip).append("street", street)));
		memberCollection.updateOne(query, update);

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
		} else if (employee != null) {
			java.util.Date date = employee.getDate("birthday");
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			Person employeeReturn = new Person(employee.getLong("_id"), employee.getString("firstname"),
					employee.getString("lastname"), employee.getString("iban"), sqlDate);
			employeeReturn.setRole("employee");
			return employeeReturn;
		}
		return null;
	}

	public List<PersonNameSvnr> getEmployeeForBranch(String city, String zip, String street, String db) {
		String s = "db.branch.aggregate([\r\n"
				+ "  {$match: {_id:{city:\"Beicang\",zip:\"4721\",street:\"Manufacturers\"}}},\r\n" + "  {\r\n"
				+ "  $unwind: \"$employee_svnr\"\r\n" + "},\r\n" + "        {\r\n" + "            $lookup:\r\n"
				+ "            {\r\n" + "                from: \"employee\",\r\n"
				+ "                localField: \"employee_svnr\",\r\n" + "                foreignField: \"_id\",\r\n"
				+ "                as: \"employee_data\"\r\n" + "            }\r\n"
				+ "        },{  $unwind: \"$employee_data\"}\r\n" + "])";

		List<PersonNameSvnr> returnList = new ArrayList<PersonNameSvnr>();
		MongoCollection<Document> branchCollection = database.getCollection("branch");
		List<Document> d = new ArrayList<Document>();
		branchCollection
				.aggregate(
						Arrays.asList(
								Aggregates.match(new Document("_id",
										new Document()
												.append("city", city).append("zip", zip).append("street", street))),
								Aggregates.unwind("$employee_svnr"),
								new Document("$lookup",
										new Document().append("from", "employee").append("localField", "employee_svnr")
												.append("foreignField", "_id").append("as", "employee_data")),
								Aggregates.unwind("$employee_data")))
				.into(d);
		for (Document employee : d) {
			returnList.add(new PersonNameSvnr(employee.get("employee_data", Document.class).get("_id", Long.class),
					employee.get("employee_data", Document.class).get("firstname", String.class)));
		}

		return returnList;

	}

	public List<Branch> getMemberRegistrations(ObjectNode objectNode, String db) {
		List<Branch> returnList = new ArrayList<Branch>();
		MongoCollection<Document> memberCollection = database.getCollection("member");
		List<Document> d = new ArrayList<Document>();
		memberCollection.aggregate(Arrays.asList(Aggregates.match(new Document("_id", objectNode.get("svnr").asLong())),
				Aggregates.unwind("$branch_id"),
				new Document("$lookup",
						new Document().append("from", "branch").append("localField", "branch_id")
								.append("foreignField", "_id").append("as", "branch_data")),
				Aggregates.unwind("$branch_data"))).into(d);
		for (Document member : d) {
			returnList.add(new Branch(member.get("branch_id", Document.class).get("street", String.class),
					member.get("branch_id", Document.class).get("city", String.class),
					member.get("branch_id", Document.class).get("zip", String.class),
					member.get("branch_data", Document.class).get("name", String.class),
					String.valueOf(member.get("branch_data", Document.class).get("area", Integer.class))));
		}

		return returnList;
	}

	public List<TrainingSessionsForMember> getMemberTrainingSessions(ObjectNode objectNode, String db) {
		List<TrainingSessionsForMember> returnList = new ArrayList<TrainingSessionsForMember>();

		MongoCollection<Document> memberCollection = database.getCollection("member");
		List<Document> d = new ArrayList<Document>();

		memberCollection.aggregate(Arrays.asList(Aggregates.match(new Document("_id", objectNode.get("svnr").asLong())),
				Aggregates.unwind("$trainings_id"),
				new Document("$lookup",
						new Document().append("from", "training_session").append("localField", "trainings_id")
								.append("foreignField", "_id").append("as", "training_data")),
				Aggregates.unwind("$training_data"))).into(d);
		for (Document member : d) {

			returnList.add(new TrainingSessionsForMember(member.get("firstname", String.class),
					member.get("training_data", Document.class).get("employee", Document.class).get("firstname",
							String.class),
					member.get("training_data", Document.class).get("price", Integer.class),
					member.get("training_data", Document.class).get("duration", Integer.class)));
		}

		return returnList;
	}

	public Collection<BestTrainer> getTopTrainers() {
		Map<Long, BestTrainer> returnList = new HashMap<Long, BestTrainer>();
		MongoCollection<Document> trainingCollection = database.getCollection("training_session");
		List<Document> d = new ArrayList<Document>();
		trainingCollection
				.aggregate(Arrays.asList(new Document("$group",
						new Document("_id", new Document("employee", "$employee"))
								.append("count", new Document("$sum", 1))),
						new Document("$sort", new Document("count", -1)),
						new Document("$lookup", new Document("from", "branch")
								.append("let", new Document("employeeId", "$_id.employee.svnr"))
								.append("pipeline", Arrays.asList(new Document("$match",
										new Document("$expr",
												new Document("$in", Arrays.asList("$$employeeId", "$employee_svnr"))))))
								.append("as", "best_employees")),
						new Document("$unwind", "$best_employees"),

						new Document("$group",
								new Document("_id",
										new Document("city", "$best_employees._id.city")
												.append("zip", "$best_employees._id.zip")
												.append("street", "$best_employees._id.street"))
														.append("session_count", new Document("$first", "$count"))
														.append("employee_info", new Document("$first", "$_id"))
														.append("branch_name",
																new Document("$first", "$best_employees.name"))),

						new Document("$lookup",
								new Document("from", "training_session").append("localField", "employee_info.employee")
										.append("foreignField", "employee").append("as", "training")),
						new Document("$addFields", new Document("training_sessions", "$training._id")),
						new Document("$project",
								new Document("_id", 1).append("session_count", 1).append("employee_info", 1)
										.append("training_sessions", 1).append("branch_name", 1)),
						new Document("$unwind", "$training_sessions"),
						new Document("$lookup",
								new Document("from", "member")
										.append("let", new Document("training_sessions", "$training_sessions"))
										.append("pipeline", Arrays.asList(new Document("$match", new Document("$expr",
												new Document("$in",
														Arrays.asList("$$training_sessions", "$trainings_id"))))))
										.append("as", "member_info")),
						new Document("$unwind", "$member_info"),
						new Document("$project",
								new Document("_id", 1).append("session_count", 1).append("employee_info", 1)
										.append("training_sessions", 1).append("branch_name", 1)
										.append("member_info.firstname", 1))))
				.into(d);

		for (Document info : d) {
			if (!returnList.containsKey(
					info.get("employee_info", Document.class).get("employee", Document.class).getLong("svnr"))) {
				returnList.put(
						info.get("employee_info", Document.class).get("employee", Document.class).getLong("svnr"),
						new BestTrainer(
								info.get("employee_info", Document.class).get("employee", Document.class)
										.getString("firstname"),
								info.get("_id", Document.class).getString("zip"),
								info.get("_id", Document.class).getString("street"),
								info.get("_id", Document.class).getString("city"), info.getString("branch_name"),
								info.getInteger("session_count")));
				returnList
						.get(info.get("employee_info", Document.class).get("employee", Document.class).getLong("svnr"))
						.addMemberName(info.get("member_info", Document.class).getString("firstname"));
			} else {
				returnList
						.get(info.get("employee_info", Document.class).get("employee", Document.class).getLong("svnr"))
						.addMemberName(info.get("member_info", Document.class).getString("firstname"));
			}
		}
		return returnList.values();
	}

	public Collection<LoyalMember> getLoyalMembers() {
		List<LoyalMember> returnList = new ArrayList<LoyalMember>();
		MongoCollection<Document> trainingCollection = database.getCollection("training_session");
		List<Document> d = new ArrayList<Document>();
		trainingCollection
				.aggregate(
						Arrays.asList(
								new Document("$lookup", new Document("from", "member")
										.append("let", new Document("training_id", "$_id"))
										.append("pipeline",
												Arrays.asList(new Document("$match", new Document("$expr",
														new Document("$in",
																Arrays.asList("$$training_id", "$trainings_id"))))))
										.append("as", "training_join")),
								new Document("$unwind", "$training_join"),
								new Document("$lookup", new Document("from", "branch")
										.append("let", new Document("employee_svnr", "$employee.svnr"))
										.append("pipeline",
												Arrays.asList(new Document("$match", new Document("$expr",
														new Document("$in",
																Arrays.asList("$$employee_svnr", "$employee_svnr"))))))
										.append("as", "branch_join")),
								new Document("$unwind", "$branch_join"),
								new Document("$project",
										new Document("_id", 1).append("employee", 1).append("training_join._id", 1)
												.append("training_join.firstname", 1).append("branch_join._id", 1)
												.append("branch_join.name", 1).append("price", 1)),

								new Document("$group", new Document("_id", new Document("city", "$branch_join._id.city")
										.append("zip", "$branch_join._id.zip")
										.append("street", "$branch_join._id.street").append("member", "$training_join"))
												.append("count", new Document("$sum", 1))
												.append("branch_name",
														new Document("$first", "$branch_join.name"))
												.append("trainer_info", new Document("$addToSet", "$employee"))
												.append("price", new Document("$sum", "$price"))),
								new Document("$sort", new Document("count", -1)),

								new Document("$group",
										new Document("_id",
												new Document("city", "$_id.city").append("zip", "$_id.zip")
														.append("street", "$_id.street"))
																.append("count", new Document("$first", "$count"))
																.append("branch_name",
																		new Document("$first", "$branch_name"))
																.append("trainer_info",
																		new Document("$first", "$trainer_info"))
																.append("member_info",
																		new Document("$first", "$_id.member"))
																.append("price", new Document("$first", "$price"))),
								new Document("$sort", new Document("price", -1))

						))

				.into(d);

		for (Document doc : d) {

			LoyalMember m = new LoyalMember(doc.get("member_info", Document.class).getString("firstname"),
					doc.get("_id", Document.class).getString("zip"), doc.get("_id", Document.class).getString("street"),
					doc.get("_id", Document.class).getString("city"), doc.getString("branch_name"),
					doc.getInteger("price"), doc.getInteger("count"));

			for (Document trainer : doc.getList("trainer_info", Document.class)) {
				m.addTrainerName(trainer.getString("firstname"));
			}

			returnList.add(m);

		}
		return returnList;
	}

}
