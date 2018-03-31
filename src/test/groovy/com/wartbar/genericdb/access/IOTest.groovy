package com.wartbar.genericdb.access

import com.wartbar.genericdb.item.EntryItem
import com.wartbar.genericdb.model.DBEntry
import com.wartbar.genericdb.util.Time


/**
 * Created by amos on 04.03.17.
 */
class IOTest extends spock.lang.Specification {

  static EntryItem createTestEntry() {
    EntryItem entry = EntryExchange.createNew();
    EntryExchange.addData(1, 2, 3, entry);
    EntryExchange.addData(4, 5, 6, entry);
    EntryExchange.addData(7, 8, 9, entry);
    EntryExchange.addString(1, 2, "Hello", entry);
    EntryExchange.addString(3, 4, "World", entry);
    EntryExchange.addString(5, 6, "Hi", entry);
    EntryExchange.addString(7, 8, "User", entry);
    return entry;
  }

  def "test IO.add()"() {

    setup:
    long id = IO.add(createTestEntry())
    DBEntry e = IO.getEntry(id)

    expect:
    id == e.getEntryId()
    1 == e.getDataList()[0].getDataType()
    5 == e.getDataList()[1].getKey()
    9 == e.getDataList()[2].getValue()
    "Hello" == e.getStringList()[0].getValue()
    "World" == e.getStringList()[1].getValue()
    "Hi" == e.getStringList()[2].getValue()
    "User" == e.getStringList()[3].getValue()
    5 == e.getStringList()[2].getDataType()
    6 == e.getStringList()[2].getKey()
  }

  def "test IO.getAllEntriesAscending() and getAllEntriesDecending()"() {

    setup:
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    List<DBEntry> ascendingList = IO.getAllEntriesAscending()
    List<DBEntry> descendingList = IO.getAllEntriesDescending()

    boolean ascEqualsInvertedDesc = true
    for (int i = 0; i < ascendingList.size(); i++) {
      if (ascendingList.get(i).getEntryId() !=
              descendingList.get(descendingList.size() - 1 - i).getEntryId()) {
        ascEqualsInvertedDesc = false
      }
    }

    expect:
    ascendingList.size() >= 3
    ascendingList.size() == descendingList.size()
    ascendingList.get(0).getEntryId() == descendingList.get(descendingList.size() - 1).getEntryId()
    ascEqualsInvertedDesc
  }

  def "test IO.getLastEntriesDescending() and getFirstEntriesAscending()"() {

    setup:
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    List<DBEntry> completeList = IO.getAllEntriesAscending()
    List<DBEntry> lastDescendingList = IO.getLastEntriesDescending(3)
    List<DBEntry> firstAscendingList = IO.getFirstEntriesAscending(3)


    boolean firstAscEqualsBeginOfCompleteList = true
    for (int i = 0; i < firstAscendingList.size(); i++) {
      if (firstAscendingList.get(i).getEntryId() !=
              completeList.get(i).getEntryId()) {
        firstAscEqualsBeginOfCompleteList = false
      }
    }

    boolean lastDescEqualsEndOfCompleteList = true
    for (int i = 0; i < lastDescendingList.size(); i++) {
      if (lastDescendingList.get(i).getEntryId() !=
              completeList.get(completeList.size()-1-i).getEntryId()) {
        lastDescEqualsEndOfCompleteList = false
      }
    }

    expect:
    firstAscendingList.size() == 3
    lastDescendingList.size() == 3
    firstAscEqualsBeginOfCompleteList
    lastDescEqualsEndOfCompleteList
  }

  def "test IO.getEntriesOfToday()"() {

    setup:
    Calendar yesterday = Time.getYesterday()
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    IO.add(createTestEntry()) // to be sure the DB is not empty
    List<DBEntry> completeList = IO.getAllEntriesAscending()
    List<DBEntry> todayAscendingList = IO.getEntriesOfTodayAscending()

    HashSet<Long> todayAscIDs = new HashSet<>()
    boolean todayAscIsOk = true
    todayAscendingList.each {
      if (it.getTimestamp() <= yesterday) {
        todayAscIsOk = false
      } else {
        todayAscIDs.add(it.getEntryId())
      }
    }

    boolean olderThanTodayIOk = true
    completeList.each {
      if (todayAscIDs.contains(it.getEntryId())) {
        return
      } else {
        if (it.getTimestamp() > yesterday) {
          olderThanTodayIOk = false
        }
      }

      expect:
      todayAscIsOk
      olderThanTodayIOk
    }
  }

}
