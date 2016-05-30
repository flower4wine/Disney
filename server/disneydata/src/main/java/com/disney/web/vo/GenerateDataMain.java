package com.disney.web.vo;

import com.disney.util.HttpClientUtil;

public class GenerateDataMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String prefix = "http://localhost:8080/disneydata";
		
		generate(prefix,"/generateParentLocation.html");
		generate(prefix,"/generateQrCode.html");
		
		generate(prefix,"/shop/p1.html");
		generate(prefix,"/shop/p2.html");
		generate(prefix,"/shop/p3.html");
		generate(prefix,"/shop/p4.html");
		
		generate(prefix,"/p1/p2.html");
		generate(prefix,"/p1/p3.html");
		generate(prefix,"/p1/p4.html");
		generate(prefix,"/p2/p1.html");
		generate(prefix,"/p2/p3.html");
		generate(prefix,"/p2/p4.html");
		generate(prefix,"/p3/p1.html");
		generate(prefix,"/p3/p2.html");
		generate(prefix,"/p3/p4.html");
		generate(prefix,"/p4/p1.html");
		generate(prefix,"/p4/p2.html");
		generate(prefix,"/p4/p3.html");
		

	}
	
	
	public static void generate(String prefix,String url){
		
		System.out.println(prefix+url);
		System.out.println(HttpClientUtil.get(prefix+url));
		System.out.println("");
		
	}

}