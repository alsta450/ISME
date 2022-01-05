package com.nosql;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class NoSqlHelper {
	private MongoClient mongoClient;
	private DB database;
	private DBCollection collection;
	public NoSqlHelper() {
		try {
			this.mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
			this.database = mongoClient.getDB("local");
			this.collection = database.getCollection("TheCollectionName");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
