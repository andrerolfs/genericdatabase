package com.wartbar.genericdb.access;

import com.wartbar.genericdb.item.EntryItem;
import com.wartbar.genericdb.item.StringItem;
import com.wartbar.genericdb.item.DataItem;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntryExchange {

	private static Map<Long, String> mapFromEnum;

	public static <E extends Enum<E>> void initializeMapFromEnum(Class<E> enumClass) {
		mapFromEnum = EnumEvaluation.enumToMap(enumClass);
	}

	public static Map<Long, String> getMapFromEnum() {
		return mapFromEnum;
	}

	public static EntryItem createEntryItem(DBEntry dbEntry) {
		EntryItem entryItem = new EntryItem();
		entryItem.entryId = dbEntry.getEntryId();
		entryItem.timestamp = dbEntry.getTimestamp();

		for (DBString dbString : dbEntry.getStringList()) {
			StringItem stringItem = new StringItem();
			stringItem.entryId = dbString.getEntry().getEntryId();
			stringItem.dataType = dbString.getDataType();
			stringItem.dataTypeName = mapFromEnum.get(stringItem.dataType);
			stringItem.key = dbString.getKey();
			stringItem.value = dbString.getValue();
			entryItem.stringList.add(stringItem);
		}

		for (DBData dbData : dbEntry.getDataList()) {
			DataItem dataItem = new DataItem();
			dataItem.entryId = dbData.getEntry().getEntryId();
			dataItem.dataType = dbData.getDataType();
			dataItem.dataTypeName = mapFromEnum.get(dataItem.dataType);
			dataItem.key = dbData.getKey();
			dataItem.value = dbData.getValue();
			entryItem.dataList.add(dataItem);
		}

		return entryItem;
	}

	public static DBData getDBData(DataItem item) {
		DBData data = new DBData();
		data.setDataType(item.dataType);
		data.setKey(item.key);
		data.setValue(item.value);
		return data;
	}

	public static DBString getDBString(StringItem item) {
		DBString string = new DBString();
		string.setDataType(item.dataType);
		string.setKey(item.key);
		string.setValue(item.value);
		return string;
	}

	public static EntryItem createNew() {
		return new EntryItem();
	}

	public static void addData(long dataType, int key, int value, EntryItem item) {
		DataItem data = new DataItem();
		data.dataType = dataType;
		data.key = key;
		data.value = value;
		item.dataList.add(data);
	}

	public static void addString(long dataType, int key, String value, EntryItem item) {
		StringItem string = new StringItem();
		string.dataType = dataType;
		string.key = key;
		string.value = value;
		item.stringList.add(string);
	}

	public static DBEntry getDBEntry(EntryItem item) {
		DBEntry entry = new DBEntry();

		List<DBData> dbDataList = new ArrayList<>();
		for (DataItem data : item.dataList) {
			DBData dbData = getDBData(data);
			dbData.setEntry(entry);
			dbDataList.add(dbData);
		}
		entry.setDataList(dbDataList);

		List<DBString> dbStringList = new ArrayList<>();
		for (StringItem string : item.stringList) {
			DBString dbString = getDBString(string);
			dbString.setEntry(entry);
			dbStringList.add(dbString);
		}
		entry.setStringList(dbStringList);

		return entry;
	}
}
