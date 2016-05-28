package com.disney.handler.entrance.p4;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P1ToP4 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		return "03-0001-000C";
	}

}
