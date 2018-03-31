package com.wartbar.genericdb.access;

import java.util.Map;
import java.util.HashMap;

public class EnumEvaluation {


	static <E extends Enum<E>> Map<Long, String> enumToMap(Class<E> enumClass) {
		HashMap<Long, String> map = new HashMap<>();
		for (Enum<E> enumInstance: enumClass.getEnumConstants()) {
			map.put(new Long(enumInstance.ordinal()), enumInstance.name());
		}
		return map;
	}



}
