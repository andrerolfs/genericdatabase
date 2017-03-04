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
	}

}

