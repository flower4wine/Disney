package com.disney.handler.codecheck;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class ExcelCodeLoaderImpl implements ExcelCodeLoader {

	private Map<String,Map<String,String>> recodes = new HashMap<String,Map<String,String>>();

	@PostConstruct  
	public void init() throws Exception {
		
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("target.csv");
		BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
          
		String line="";

		while((line=reader.readLine())!=null){//一行一行读

			if(StringUtils.isNotEmpty(line)){

				String[] items = line.split(",");

				Map<String,String> map = new HashMap<String,String>();

				map.put("name", items[0]);
				map.put("region", items[1]);
				map.put("range", items[2]);
				map.put("code", items[3]);
				map.put("size", items[4]);
				map.put("style", items[5]);

				recodes.put(map.get("code"),map);

			}

		}
	}

	@Override
	public Map<String, String> getQrCodeInfo(String code) {
		return recodes.get(code);
	}

	@Override
	public List<Map<String, String>> getAllQrCode() {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		
		for(String key:recodes.keySet()){
			list.add(recodes.get(key));
		}
		
		return list;
	}


}
