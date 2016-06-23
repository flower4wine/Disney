package com.disney.handler.entrance.p1;

import java.util.ArrayList;
import java.util.List;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P2ToP1 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		
		int index = Integer.valueOf(from.substring(from.length()-4));
		List<Integer> clist= new ArrayList<Integer>();
		for (int i = 1; i <= 71; i++) {
			clist.add(i);
		}
		clist.add(77);
		clist.add(78);
		clist.add(81);
		clist.add(82);
		clist.add(84);
		clist.add(87);
		clist.add(111);
		clist.add(112);
		for (Integer c : clist) {
			if( index == c ){
				return "03-0003-000C";
			}
		}
		
		List<Integer> dlist= new ArrayList<Integer>();
		for (int i = 72; i <= 76; i++) {
			dlist.add(i);
		}
		dlist.add(79);
		dlist.add(80);
		dlist.add(83);
		dlist.add(85);
		dlist.add(86);
		dlist.add(113);
		for (int i = 88; i <= 110; i++) {
			dlist.add(i);
		}
		
		for (Integer d : dlist) {
			if( index == d){
				return "03-0003-000D";
			}
		}
		

		return null;
	}

}
