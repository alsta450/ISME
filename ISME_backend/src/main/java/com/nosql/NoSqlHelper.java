package com.nosql;

import java.net.UnknownHostException;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class NoSqlHelper {
	private MongoClient mongoClient;
	private MongoDatabase database;
	public NoSqlHelper() {
		this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		this.database = mongoClient.getDatabase("fitness_center");
	}
	
	public void createCollection(List<Document> inserts,String name) {
		MongoCollection<Document> collection = database.getCollection(name);
		collection.insertMany(inserts);
	}
	

}
