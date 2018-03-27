package com.wartbar.genericdb.access;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBString;
import com.wartbar.genericdb.util.DB;
import com.wartbar.genericdb.util.QueryDBEntryStrategy;
import com.wartbar.genericdb.util.Time;

import javax.persistence.TypedQuery;

/**
 * Created by amos on 24.02.17.
 */
public class IO {

	static Logger logger = Logger.getLogger(IO.class.getName());

	private static void logError(String message) {
		logger.log(Level.SEVERE,message);
	}

	public static long add(Entry entry) {
		DBEntry e = null;

		try {

			DB.beginTransaction();

			e = entry.getDBEntry();

			DB.persist(e);

			for (DBData data : e.getDataList()) {
				DB.persist(data);
			}

			for (DBString string : e.getStringList()) {
				DB.persist(string);
			}

			DB.commitTransaction();
		} catch (Exception exception) {
			logError("An exception occured in IO.add");
			logError(exception.getMessage());
			DB.rollbackTransaction();
		}

		return e.getEntryId();
	}

	public static List<DBEntry> getEntries(String queryString, QueryDBEntryStrategy strategy) {
		List<DBEntry> entries = null;
		try {
			DB.beginTransaction();
			TypedQuery<DBEntry> query = DB.getEntityManager().createQuery(queryString, DBEntry.class);
			strategy.execute(query);
			entries = query.getResultList();
			DB.commitTransaction();
		} catch (Exception e) {
			logError("An exception occured in IO.getEntries with \n" +
							"query      : " + queryString + "\n" +
							"strategies : " + strategy.getDescription());
			logError(e.getMessage());
			DB.rollbackTransaction();
		}
		return entries;
	}

	public static List<DBEntry> getAllEntriesAscending() {
		return getEntries("SELECT e from DBEntry e ORDER BY e.entryId ASC",
						QueryDBEntryStrategy.createAllResults());
	}

	public static List<DBEntry> getAllEntriesDescending() {
		return getEntries("SELECT e from DBEntry e ORDER BY e.entryId DESC",
						QueryDBEntryStrategy.createAllResults());
	}

	public static List<DBEntry> getLastEntries(int maxResults) {
		return getEntries("SELECT e from DBEntry e ORDER BY e.entryId DESC",
						QueryDBEntryStrategy.createMaxResults(maxResults));
	}

	public static List<DBEntry> getFirstEntries(int maxResults) {
		return getEntries("SELECT e from DBEntry e ORDER BY e.entryId ASC",
						QueryDBEntryStrategy.createMaxResults(maxResults));
	}

	public static List<DBEntry> getEntriesOfToday() {
		return getEntries("SELECT e from DBEntry e where e.timestamp > :definedDay",
						QueryDBEntryStrategy.createNewerThanCalendarParameter(Time.getYesterday()));
	}

	public static DBEntry getEntry(long id) {
		TypedQuery<DBEntry> queryData = DB.getEntityManager().createQuery("SELECT e from DBEntry e where e.entryId = :entryId", DBEntry.class);
		queryData.setParameter("entryId", id);
		DBEntry entry = null;
		try {
			entry = queryData.getSingleResult();
		} catch (Exception e) {
			logError("IO.getEntry : " + e.getMessage());
		}
		return entry;
	}


}
