package com.disney.handler.entrance.p3;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P4ToP3 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {

		int index = Integer.valueOf(from.substring(from.length()-4));

		if( index>=1 && index<=25 || (index>=67 && index<=70 ) || (index == 64) || (index == 74) ){
			return "03-0004-000C";
		}

		if( index>=26 && index<=63 || ( index>=71 &&  index<=73) || (index == 65)  || (index == 66) ){
			return "03-0004-000D";
		}

		return null;	
	}

}
