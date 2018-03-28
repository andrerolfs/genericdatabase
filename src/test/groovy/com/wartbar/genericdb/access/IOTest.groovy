package com.wartbar.genericdb.access

import com.wartbar.genericdb.model.DBEntry


/**
 * Created by amos on 04.03.17.
 */
class IOTest extends spock.lang.Specification {

  public static Entry createTestEntry() {
    Entry entry = Entry.createNew();
    entry.addData(1,2,3);
    entry.addData(4,5,6);
    entry.addData(7,8,9);
    entry.addString(1,2,"Hello");
    entry.addString(3,4,"World");
    entry.addString(5,6,"Hi");
    entry.addString(7,8,"User");
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
              descendingList.get(descendingList.size()-1-i).getEntryId()) {
        ascEqualsInvertedDesc = false
      }
    }

    expect:
    ascendingList.size() >= 3
    ascendingList.size() == descendingList.size()
    ascendingList.get(0).getEntryId() == descendingList.get(descendingList.size()-1).getEntryId()
    ascEqualsInvertedDesc
  }
}
