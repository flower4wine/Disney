package com.disney.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapUtil {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> T getJsonObject(String jsonStr, Class<T> valueType) throws IOException {
		return MAPPER.readValue(jsonStr, valueType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void setMethod(Object method, Object value, Object thisObj) {
		
		if(value==null){
			return;
		}

		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			String met = (String) method;
			met = met.trim();
			if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase())) {
				met = met.substring(0, 1).toUpperCase() + met.substring(1);
			}
			if (!String.valueOf(method).startsWith("set")) {
				met = "set" + met;
			}
			Class types[] = new Class[1];
			types[0] = value.getClass(); //Class.forName("java.lang.String");

			Method m = null;
			try{
				
				m = c.getMethod(met, types);
				m.invoke(thisObj, value);
				
			} catch (NoSuchMethodException e) {

				if(value instanceof Integer){
					types[0] = Long.class;
					m = c.getMethod(met, types);
					m.invoke(thisObj, Long.valueOf(value.toString()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static void setValue(Map map, Object thisObj) {
		Set set = map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			Object val = map.get(obj);
			setMethod(obj, val, thisObj);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String,Object> getValue(Object thisObj) {
		Map map = new HashMap();

		if (thisObj == null) {
			return map;
		}

		Class c;
		try {
			c = Class.forName(thisObj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get") && (!method.equals("getClass"))) {
					try {
						Object value = m[i].invoke(thisObj);
						String key = method.substring(3);
						key = key.substring(0, 1).toLowerCase() + key.substring(1);
						map.put(key, value);
					} catch (Exception e) {
						System.out.println("error:" + method);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] argc) {

	}

}
