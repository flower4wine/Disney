package com.disney.handler.entrance.p3;

import java.util.ArrayList;
import java.util.List;

import com.disney.handler.entrance.ParkEntranceHandler;

public class P2ToP3 implements ParkEntranceHandler {

	@Override
	public String getEntrance(String from) {
		int index = Integer.valueOf(from.substring(from.length()-4));
		
		if( incList(index) ){
			return "03-0002-000C";
		}
		
		if( inbList(index) ){
			return "03-0002-000D";
		}

		if(inaList(index)){
			return "03-0002-000A";
		}
		return null;
	}
	
	private boolean inaList(int index){
		List<Integer> aList = new ArrayList<Integer>();
				
		for (int i = 1; i <= 48; i++) {
			aList.add(i);
		}
		
		for (Integer num : aList) {
			if(index == num){
				return true;
			}
		}
		
		return false;
		
	}
	
	private boolean incList(int index){
		List<Integer> cList = new ArrayList<Integer>();
				
		for (int i = 49; i <= 71; i++) {
			cList.add(i);
		}
		cList.add(77);
		cList.add(78);
		cList.add(81);
		cList.add(82);
		cList.add(84);
		cList.add(87);
		
		for (Integer num : cList) {
			if(index == num){
				return true;
			}
		}
		
		return false;
		
	}
	
	private boolean inbList(int index){
		
		List<Integer> dList = new ArrayList<Integer>();
		for (int i = 72; i <= 76; i++) {
			dList.add(i);
		}
		dList.add(79);
		dList.add(80);
		dList.add(83);
		dList.add(85);
		dList.add(86);
		dList.add(113);
		for (int i = 88; i <= 110; i++) {
			dList.add(i);
		}
		
		for (Integer num : dList) {
			if(index == num){
				return true;
			}
		}
		
		return false;
		
	}

}
