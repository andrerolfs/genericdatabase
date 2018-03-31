package com.wartbar.genericdb.access

class EntryExchangeTest extends spock.lang.Specification {

  enum color {
    red, green, blue
  }

  def "test mapFromEnum"() {

    setup:
    EntryExchange.initializeMapFromEnum(color.class)
    Map<Long, String> map = EntryExchange.getMapFromEnum()

    expect:
    map.get(new Long(0)) == "red"
    map.get(new Long(1)) == "green"
    map.get(new Long(2)) == "blue"
  }
}