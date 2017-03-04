package com.wartbar.genericdb.access

import com.wartbar.genericdb.model.DBEntry


/**
 * Created by amos on 04.03.17.
 */
class IOTest extends spock.lang.Specification {

  public static Entry createTestEntry() {
    Entry entry = Entry.createNew();
    entry.addData(1,1,1);
    entry.addData(1,1,2);
    entry.addData(1,1,3);
    entry.addData(1,2,1);
    entry.addData(2,2,1);
    entry.addData(3,1,3);
    entry.addString(1,1,"Hello");
    entry.addString(1,2,"World");
    entry.addString(2,1,"Hi");
    entry.addString(2,2,"User");
    return entry;
  }

  def "test IO.add"() {
    setup:

    long id = IO.add(createTestEntry());
    DBEntry e = IO.getEntry(id)

    expect:
    id == e.getEntryId()
  }

}
