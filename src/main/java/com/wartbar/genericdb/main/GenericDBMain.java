package com.wartbar.genericdb.main;

import javax.persistence.TypedQuery;

import com.wartbar.genericdb.access.Entry;
import com.wartbar.genericdb.access.IO;
import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBString;
import com.wartbar.genericdb.util.DB;
import com.wartbar.genericdb.util.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* DB Scheme :
	1 DBEntry has n DBData and m DBString
 */

/**
 * Created by amos on 20.01.17.
 */
public class GenericDBMain {

	public static void testDirect() {
		System.out.println("Hello world1");

		ArrayList<DBData> dataList = new ArrayList<>();
		dataList.add(new DBData(1,1,2));
		dataList.add(new DBData(2,4,3));
		dataList.add(new DBData(3,2,7));

		ArrayList<DBString> stringList = new ArrayList<>();
		stringList.add(new DBString(4,5,"Hello"));
		stringList.add(new DBString(5,6,"world"));

		DBEntry entry = new DBEntry();
		entry.setDataList(dataList);
		entry.setStringList(stringList);
		entry.setTimestamp(Calendar.getInstance());

		for (DBData dataObject : dataList) {
			dataObject.setEntry(entry);
		}

		for (DBString stringObject : stringList) {
			stringObject.setEntry(entry);
		}

		try {
			DB.beginTransaction();
			DB.persist(entry);
			System.out.println("Hello world 2");

			for (DBData data : dataList) {
				DB.persist(data);
			}
			System.out.println("Hello world 3");

			for (DBString string : stringList) {
				DB.persist(string);
			}
			System.out.println("Hello world 4");

			DB.commitTransaction();
		} catch (Exception e) {
			System.out.println("An exception occured while adding");
			System.out.println(e.getMessage());
			DB.rollbackTransaction();
		}

		List<DBEntry> entries = null;

		try {
			DB.beginTransaction();
			TypedQuery<DBEntry> query = DB.getEntityManager().createQuery("SELECT e from DBEntry e", DBEntry.class);
			entries = query.getResultList();
			DB.commitTransaction();
		} catch (Exception e) {
			System.out.println("An exception occured while query");
			System.out.println(e.getMessage());
			DB.rollbackTransaction();
		}

		for (DBEntry e : entries) {
			System.out.println(e.getTimestamp().getTime());
		}
	}

	public static Entry createTestEntry() {
		Entry entry = Entry.createNew();
		entry.addData(1,1,1);
		entry.addData(1,1,2);
		entry.addData(1,1,3);
		entry.addData(1,2,1);
		entry.addData(2,2,1);
		entry.addData(3,1,3);
		entry.addString(1,1,"Hello");
		entry.addString(1,2,"World");
		entry.addString(2,1,"Hi");
		entry.addString(2,2,"User");
		return entry;
	}

	public static void testViaIO() {
		IO.add(createTestEntry());
		IO.add(createTestEntry());
		IO.add(createTestEntry());

		List<DBEntry> entries = IO.getAllEntries();

		/*
		System.out.println("---> print all");
		for (DBEntry entry : entries) {
			Entry.createFromExisting(entry).print();
		}
		System.out.println("<--- print all");
*/
		Long firstEntryID = entries.get(0).getEntryId();
		DBEntry entry = IO.getEntry(firstEntryID);
		System.out.println("---> First entry in DB is :");
		Entry.createFromExisting(entry).print();
		System.out.println("<--- end of first entry in DB");


		entries = IO.getEntriesOfToday();

		System.out.println("####---> today");
		for (DBEntry e2 : entries) {
			Entry.createFromExisting(e2).print();
		}
		System.out.println("<---#### today");

		System.out.println("today was " + Time.getYesterday().getTime());


	}

	public static void main(String[] args) {
		testViaIO();
		//testDirect();
	}

}

