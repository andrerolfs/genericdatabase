package com.wartbar.genericdb.model;

import javax.persistence.*;

/**
 * Created by amos on 22.01.17.
 */

@Entity
@Table(name = "DBString")
public class DBString {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STRINGID")
	private long stringId;

	@ManyToOne
	@JoinColumn(name = "DBEntry")
	private DBEntry entry;

	@Column(name = "DATATYPE")
	private long dataType;

	@Column(name = "KEY")
	private int key;

	@Column(name = "VALUE")
	private String value;

	public DBString() {}

	public DBString(long dataType, int key, String value) {
		this.dataType = dataType;
		this.key = key;
		this.value = value;
	}

	public long getStringId() {
		return stringId;
	}

	public void setStringId(long stringId) {
		this.stringId = stringId;
	}

	public DBEntry getEntry() {
		return entry;
	}

	public void setEntry(DBEntry entry) {
		this.entry = entry;
	}

	public long getDataType() {
		return dataType;
	}

	public void setDataType(long dataType) {
		this.dataType = dataType;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}