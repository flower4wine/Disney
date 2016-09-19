package com.disney.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.disney.util.DateUtils;
import com.disney.util.HttpsRequestUtil;
import com.disney.util.JsonUtil;
import com.disney.util.WeChatBaseUtil;

public class SendMain {

	/**
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void send() throws Exception{
		for(int i=0;i<2;i++){
			Map<String,Object> map = WeChatBaseUtil.getToken("wxff0ac43e1980f229","60f544a9b90f8c6031b0f08e4dddea0e");
			String accessToken = (String) map.get("access_token");
			String url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token="+accessToken;
			
			//to_user:oF4ntt_TjLY4-2hQhTiiJUUet7nA
			//from_user:gh_4800be1b45ff
			
			
			
			Map paramMap = new HashMap();
			
			List toUsers = new ArrayList();
			//toUsers.add("oF4ntt_TjLY4-2hQhTiiJUUet7nA");
			toUsers.add("oM8akv32ZKTdRVRSOZCI01N-q2y0");
			toUsers.add("oF4ntt_TjLY4-2hQhTiiJUUet7nA");
			
			paramMap.put("touser", toUsers);
			paramMap.put("msgtype", "text");
			
			Map content = new HashMap();
			content.put("content", "Welcome to visit "+ DateUtils.longToDate(System.currentTimeMillis()) +" "+ UUID.randomUUID().toString() +" !");
			
			paramMap.put("text", content);
			
			
			String param = JsonUtil.objectToStringJson(paramMap);
			//System.out.println(param);
			
			//https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN
			String str =  HttpsRequestUtil.postString(url, param);
			
			
			Thread.sleep(1000);  
			
			System.out.println(str);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		send();
	}

}
