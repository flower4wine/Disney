package com.disney.handler.entrance.p4;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P3ToP4 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		int index = Integer.valueOf(from.substring(from.length()-4));

		if( index>=1 && index<=35 || (index>=63) ){
			return "03-0003-000A";
		}

		
		if( (index>=36 && index<=62) || (index>=59 && index<=62)){
			return "03-0003-000B";
		}

		return null;
	}

}
