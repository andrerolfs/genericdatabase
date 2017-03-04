package com.wartbar.genericdb.access;

import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by amos on 24.02.17.
 */
public class Entry {

	private DBEntry entry = new DBEntry();
	private List<DBString> stringList = new ArrayList<>();
	private List<DBData> dataList = new ArrayList<>();

	private void initialize() {
		entry = new DBEntry();
		stringList = new ArrayList<>();
		dataList = new ArrayList<>();

		entry.setTimestamp(Calendar.getInstance());
		entry.setStringList(stringList);
		entry.setDataList(dataList);
	}

	private void initialize(DBEntry input) {
		entry = input;
		stringList = entry.getStringList();
		dataList = entry.getDataList();
	}

	private Entry() {}

	public static Entry createNew() {
		Entry e = new Entry();
		e.initialize();
		return e;
	}

	public static Entry createFromExisting(DBEntry input) {
		Entry e = new Entry();
		e.initialize(input);
		return e;
	}

	public void addData(long datatype, int key, int value) {
		DBData data = new DBData();
		data.setDataType(datatype);
		data.setKey(key);
		data.setValue(value);
		data.setEntry(entry);
		dataList.add(data);
	}

	public void addString(long dataType, int key, String value) {
		DBString string = new DBString();
		string.setDataType(dataType);
		string.setKey(key);
		string.setValue(value);
		string.setEntry(entry);
		stringList.add(string);
	}

	public DBEntry getDBEntry() {
		return entry;
	}

	public void print() {

		System.out.println("----> entry printout");
		System.out.println("Entry ID        : " + entry.getEntryId());
		System.out.println("Entry Timestamp : " + entry.getTimestamp().getTime());

		for (DBData data : dataList) {
			System.out.println("Data ID       : " + data.getDataId());
			System.out.println("Data Datatype : " + data.getDataType());
			System.out.println("Data Key      : " + data.getKey());
			System.out.println("Data Value    : " + data.getValue());
		}

		for (DBString string : stringList) {
			System.out.println("String ID       : " + string.getStringId());
			System.out.println("String Datatype : " + string.getDataType());
			System.out.println("String Key      : " + string.getKey());
			System.out.println("String Value    : " + string.getValue());
		}
	}
}
