package com.disney.main;

import com.disney.util.HttpClientUtil;

public class GenerateDataMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String prefix = "http://localhost:8080/disneydata";
		
		generate(prefix,"/generateParentLocation.html");
		generate(prefix,"/generateQrCode.html");
		
		generate(prefix,"/generateLocationTimeDistance.html");
		
		generate(prefix,"/shop/p1.html");
		generate(prefix,"/shop/p2.html");
		generate(prefix,"/shop/p3.html");
		generate(prefix,"/shop/p4.html");
		
		generate(prefix,"/disneyland/p1.html");
		generate(prefix,"/disneyland/p2.html");
		generate(prefix,"/disneyland/p3.html");
		generate(prefix,"/disneyland/p4.html");
		
		generate(prefix,"/disneyland/bus.html");
		
		generate(prefix,"/ecological/p1.html");
		generate(prefix,"/ecological/p2.html");
		generate(prefix,"/ecological/p3.html");
		generate(prefix,"/ecological/p4.html");
		
		generate(prefix,"/ecological/bus.html");
		
		generate(prefix,"/herbgarden/p1.html");
		generate(prefix,"/herbgarden/p2.html");
		generate(prefix,"/herbgarden/p3.html");
		generate(prefix,"/herbgarden/p4.html");
		
		generate(prefix,"/herbgarden/bus.html");
		
		generate(prefix,"/bifrost/p1.html");
		generate(prefix,"/bifrost/p2.html");
		generate(prefix,"/bifrost/p3.html");
		generate(prefix,"/bifrost/p4.html");
		
		generate(prefix,"/bifrost/bus.html");
		
		generate(prefix,"/hotel/p1.html");
		generate(prefix,"/hotel/p2.html");
		generate(prefix,"/hotel/p3.html");
		generate(prefix,"/hotel/p4.html");
		
		generate(prefix,"/hotel/bus.html");
		
		generate(prefix,"/maxus/p1.html");
		generate(prefix,"/maxus/p2.html");
		generate(prefix,"/maxus/p3.html");
		generate(prefix,"/maxus/p4.html");
		
		generate(prefix,"/maxus/bus.html");
		
		generate(prefix,"/town/p1.html");
		generate(prefix,"/town/p2.html");
		generate(prefix,"/town/p3.html");
		generate(prefix,"/town/p4.html");
		
		generate(prefix,"/town/bus.html");
		
		generate(prefix,"/toystoryhotel/p1.html");
		generate(prefix,"/toystoryhotel/p2.html");
		generate(prefix,"/toystoryhotel/p3.html");
		generate(prefix,"/toystoryhotel/p4.html");
		
		generate(prefix,"/toystoryhotel/bus.html");
		
		generate(prefix,"/wishingstar/p1.html");
		generate(prefix,"/wishingstar/p2.html");
		generate(prefix,"/wishingstar/p3.html");
		generate(prefix,"/wishingstar/p4.html");
		
		generate(prefix,"/wishingstar/bus.html");
		
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
		
		
		generate(prefix,"/bus/shop.html");
		
		generate(prefix,"/bus/p1.html");
		generate(prefix,"/bus/p2.html");
		generate(prefix,"/bus/p3.html");
		generate(prefix,"/bus/p4.html");
		
		generate(prefix,"/batchUpdateQrCode.html");
	}
	
	
	public static void generate(String prefix,String url){
		
		System.out.println(prefix+url);
		System.out.println(HttpClientUtil.get(prefix+url));
		System.out.println("");
		
	}

}
