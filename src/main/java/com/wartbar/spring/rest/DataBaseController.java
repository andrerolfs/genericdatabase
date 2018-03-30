package com.wartbar.spring.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.wartbar.genericdb.access.IO;
import com.wartbar.genericdb.model.DBData;
import com.wartbar.genericdb.model.DBEntry;
import com.wartbar.genericdb.model.DBString;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataBaseController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/dbread")
    public Map<String, Map<String,String>> dbread(@RequestParam(value="maxResults", defaultValue="1") String maxResults) {
			HashMap<String, Map<String,String>> map = new HashMap<>();

			List<DBEntry> entries = IO.getLastEntriesDescending(Integer.parseInt(maxResults));

			for (DBEntry entry : entries) {
				HashMap<String, String> itemMap = new HashMap<>();

				itemMap.put("timestamp", entry.getTimestamp().getTime().toString());
				for (DBData dataItem : entry.getDataList()) {
					String key = dataItem.getDataType() + "_" + dataItem.getKey();
					itemMap.put(key, Integer.toString(dataItem.getValue()));
				}
				for (DBString stringItem : entry.getStringList()) {
					String key = stringItem.getDataType() + "_" + stringItem.getKey();
					itemMap.put(key, stringItem.getValue());
				}

				map.put(Long.toString(entry.getEntryId()), itemMap);
			}

			return map;
    }
}


