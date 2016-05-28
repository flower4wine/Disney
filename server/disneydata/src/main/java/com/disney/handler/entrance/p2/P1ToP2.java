package com.disney.handler.entrance.p2;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P1ToP2 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		return "03-0001-000C";
	}

}
