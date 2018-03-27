package com.wartbar.genericdb.util;

import com.wartbar.genericdb.model.DBEntry;

import javax.persistence.TypedQuery;
import java.util.Calendar;

public class QueryDBEntryStrategy {

	String description = "";
	Integer maxResults = null;
	Calendar calendar = null;

	public QueryDBEntryStrategy() {}

	public void setMaxResults(int maxResults) {
		this.maxResults = new Integer(maxResults);
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public static QueryDBEntryStrategy createMaxResults(int maxResults) {
		QueryDBEntryStrategy query = new QueryDBEntryStrategy();
		query.setMaxResults(maxResults);
		return query;
	}

	public static QueryDBEntryStrategy createNewerThanCalendarParameter(Calendar calendar) {
		QueryDBEntryStrategy query = new QueryDBEntryStrategy();
		query.setCalendar(calendar);
		return query;
	}

	public static QueryDBEntryStrategy createAllResults() {
		QueryDBEntryStrategy query = new QueryDBEntryStrategy();
		return query;
	}

	public void execute(TypedQuery<DBEntry> query) {
		if (maxResults != null) {
			description += "\n- max results is " + maxResults + "\n";
			query.setMaxResults(maxResults.intValue());
		}
		if (calendar != null) {
			description = "\n- entries newer than " + calendar.getTime().toString();
			query.setParameter("definedDay", calendar);
		}
		if (description.isEmpty()) {
			description = "\n- all results without restrictions";
		}
	}

	public String getDescription() {
		return description;
	}
}
