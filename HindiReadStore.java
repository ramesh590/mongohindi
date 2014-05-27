package com.lingualspeech.mongodb;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class HindiReadStore {
	public static void main(String args[]) throws UnknownHostException, MongoException, FileNotFoundException
	{
		//Mongo mg=new Mongo("localhost",27017);
		Mongo mg=new Mongo("localhost",27017);
		DB db=mg.getDB("mydb");
		Scanner scanner = new Scanner(new File("D:\\JSON.txt"), "UTF-8");

		DBCollection colName=db.getCollection("hindiTrans");
		String line;
		int i=1;
		while(scanner.hasNext())
		{
			line=scanner.nextLine();
			String[] words = line.split("\\|");
			//System.out.println(words[0] + "    " + words[1]);
			
			// sanity check
			if (words.length != 2) {
				System.err.println("WARNING: words length is NOT equal to 2.");
				continue;
			}
			
			BasicDBObject doc= new BasicDBObject("_id",i)
										.append("word",words[0].trim())
										.append("sound", words[1].trim())
										.append("isverfied",false)
										.append("keystroke", "");
			colName.insert(doc);
			i++;
		}
		scanner.close();
		DBCursor cursor = colName.find();
		//int i=1;
		while (cursor.hasNext()) {
			System.out.println("Inserted Document: ");
			System.out.println(cursor.next());
			//i++;
		}

	}
}


