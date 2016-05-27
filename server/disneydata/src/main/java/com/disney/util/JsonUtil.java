package com.disney.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static void main(String[] args) {
		String json = "[{\"address\": \"address2\",\"name\":\"haha2\",\"id\":2,\"email\":\"email2\"},"
				+ "{\"address\":\"address\",\"name\":\"haha\",\"id\":1,\"email\":\"email\"}]";

		try {
			List<LinkedHashMap<String, Object>> l = readJson2List(json);
			String a = MAPPER.writeValueAsString(l); 
			System.out.println(a);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static String objectToStringJson(Object o) throws JsonProcessingException{
		return MAPPER.writeValueAsString(o); 
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<LinkedHashMap<String, Object>> readJson2List(String jsonString) throws IOException,
			JsonMappingException, IOException {
		// 由于构造ObjectMapper对象是一个很耗时的过程，并且ObjectMapper对象是线程安全的，所以其对象应是类属性
		// ObjectMapper objectMapper = new ObjectMapper();
		return MAPPER.readValue(jsonString, List.class);
	}

	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> readJson2Map(String jsonString) throws IOException, JsonMappingException,
			IOException {
		if (!StringUtils.isEmpty(jsonString)) {
			// 由于构造ObjectMapper对象是一个很耗时的过程，并且ObjectMapper对象是线程安全的，所以其对象应是类属性
			// ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = MAPPER.readValue(jsonString, Map.class);
			return map;
		}
		return new HashMap<String, Object>();
	}

	public static Map<String, Object> getJsonFromRequest(HttpServletRequest request) throws Exception {
		String json = getJsonStringFromRequest(request);
		Map<String, Object> map = readJson2Map(json);

		return map;

	}

	public static String getJsonStringFromRequest(HttpServletRequest request) throws Exception {
		ServletInputStream sis = request.getInputStream();
		DataInputStream dataInStream = new DataInputStream(sis);
		byte[] buf = new byte[1024];
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int n = dataInStream.read(buf);
		while (n != -1) {
			os.write(buf, 0, n);
			n = dataInStream.read(buf);
		}
		byte[] res = os.toByteArray();
		String json = new String(res, "UTF-8");
		return json;
	}

}
