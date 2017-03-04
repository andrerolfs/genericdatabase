package com.wartbar.genericdb.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by amos on 22.01.17.
 */

@Entity
@Table(name = "DBEntry")
public class DBEntry implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ENTRYID")
	private long entryId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	java.util.Calendar timestamp;

	@OneToMany(mappedBy = "entry")
	List<DBData> dataList;

	@OneToMany(mappedBy = "entry")
	List<DBString> stringList;

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public long getEntryId() {
		return entryId;
	}

	public java.util.Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.util.Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public List<DBData> getDataList() {
		return dataList;
	}

	public void setDataList(List<DBData> dataList) {
		this.dataList = dataList;
	}

	public List<DBString> getStringList() {
		return stringList;
	}

	public void setStringList(List<DBString> stringList) {
		this.stringList = stringList;
	}

}
