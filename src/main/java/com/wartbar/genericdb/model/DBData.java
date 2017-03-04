package com.wartbar.genericdb.model;

import javax.persistence.*;

/**
 * Created by amos on 22.01.17.
 */

@Entity
@Table(name = "DBData")
public class DBData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DATAID")
	private long dataId;

	@ManyToOne
	@JoinColumn(name="DBEntry")
	private DBEntry entry;

	@Column(name = "DATATYPE")
	private long dataType;

	@Column(name = "KEY")
	private int key;

	@Column(name = "VALUE")
	private int value;

	public DBData(int key, int value) {
		this.key = key;
		this.value = value;
	}

	public DBData() {}

	public DBData(long dataType, int key, int value) {
		this.dataType = dataType;
		this.key = key;
		this.value = value;
	}

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long dataId) {
		this.dataId = dataId;
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
