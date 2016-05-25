package com.disney.handler.entrance.p1;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P3ToP1 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		int index = Integer.valueOf(from.substring(from.length()-4));

		if( index>=1 && index<=8 ){
			return "03-0003-000C";
		}
		
		if( index>=9 && index<=19 || (index==63)){
			return "03-0003-000D";
		}

		
		if( index>=20 && index<=47 || (index==58)){
			return "03-0003-000E";
		}

		
		if( (index>=48 && index<=57) || (index>=59 && index<=62)){
			return "03-0003-000F";
		}

		return null;
	}

}
