package com.wartbar.genericdb.access;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBString;
import com.wartbar.genericdb.util.DB;
import com.wartbar.genericdb.util.Time;

import javax.persistence.TypedQuery;

/**
 * Created by amos on 24.02.17.
 */
public class IO {

	static Logger logger = Logger.getLogger(IO.class.getName());

	private static void logInfo(String message) {
		logger.log(Level.INFO,message);
	}

	public static void add(Entry entry) {
		try {

			DB.beginTransaction();

			DBEntry e = entry.getDBEntry();

			DB.persist(e);

			for (DBData data : e.getDataList()) {
				DB.persist(data);
			}

			for (DBString string : e.getStringList()) {
				DB.persist(string);
			}

			DB.commitTransaction();
		} catch (Exception e) {
			System.out.println("An exception occured in IO.add");
			System.out.println(e.getMessage());
			DB.rollbackTransaction();
		}
	}

	public static List<DBEntry> getAllEntries() {
		List<DBEntry> entries = null;
		try {
			DB.beginTransaction();
			TypedQuery<DBEntry> query = DB.getEntityManager().createQuery("SELECT e from DBEntry e", DBEntry.class);
			entries = query.getResultList();
			DB.commitTransaction();
		} catch (Exception e) {
			System.out.println("An exception occured in IO.getAllEntries");
			System.out.println(e.getMessage());
			DB.rollbackTransaction();
		}
		return entries;
	}

	public static DBEntry getEntry(long id) {
		TypedQuery<DBEntry> queryData = DB.getEntityManager().createQuery("SELECT e from DBEntry e where e.entryId = :entryId", DBEntry.class);
		queryData.setParameter("entryId", id);
		DBEntry entry = null;
		try {
			entry = queryData.getSingleResult();
		} catch (Exception e) {
			System.out.println("IO.getEntry : " + e.getMessage());
		}
		return entry;
	}

	public static List<DBEntry> getEntriesOfToday() {
		TypedQuery<DBEntry> queryData = DB.getEntityManager().createQuery("SELECT e from DBEntry e where e.timestamp > :yesterday", DBEntry.class);
		queryData.setParameter("yesterday", Time.getYesterday());
		List<DBEntry> entries = null;
		try {
			entries = queryData.getResultList();
		} catch (Exception e) {
			System.out.println("IO.getEntriesOfToday : " + e.getMessage());
		}
		return entries;
	}



/*

		TypedQuery<DBData> queryData = DB.getEntityManager().createQuery("SELECT e.dataList from DBEntry e where e.entryId = :entryId", DBData.class);
		queryData.setParameter("entryId", (long)51);
		List<DBData> dataResultList = queryData.getResultList();

 */


}
