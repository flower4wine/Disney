package com.disney.handler.entrance.p3;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P2ToP3 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		return "03-0002-000B";
	}

}
