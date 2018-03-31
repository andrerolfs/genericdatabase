package com.wartbar.spring.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.wartbar.genericdb.access.EntryExchange;
import com.wartbar.genericdb.access.IO;
import com.wartbar.genericdb.item.EntryItem;
import com.wartbar.genericdb.model.DBEntry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataBaseController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    enum DataType { t1, t2, t3, t4, t5, t6, t7, t8};

    @RequestMapping("/dbread")
    public List<EntryItem> dbread(@RequestParam(value="maxResults", defaultValue="1") String maxResults) {
			EntryExchange.initializeMapFromEnum(DataType.class);
    	List<DBEntry> entries = IO.getLastEntriesDescending(Integer.parseInt(maxResults));
			List<EntryItem> itemList = new ArrayList<>();
			for (DBEntry dbEntry : entries) {
				itemList.add(EntryExchange.createEntryItem(dbEntry));
			}
			return itemList;
    }
}


