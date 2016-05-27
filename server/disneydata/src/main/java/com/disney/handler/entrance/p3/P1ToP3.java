package com.disney.handler.entrance.p3;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P1ToP3 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		return "03-0001-000C";
	}

}
