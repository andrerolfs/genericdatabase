package com.wartbar.genericdb.item;

import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBString;
import com.wartbar.genericdb.model.DBEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amos on 24.02.17.
 */
public class EntryItem {
	public long entryId = 0;
	public java.util.Calendar timestamp;
	public List<StringItem> stringList = new ArrayList<>();
	public List<DataItem> dataList = new ArrayList<>();
}
