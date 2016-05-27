package com.disney.handler.entrance;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.disney.handler.entrance.p1.P2ToP1;
import com.disney.handler.entrance.p1.P3ToP1;
import com.disney.handler.entrance.p1.P4ToP1;
import com.disney.handler.entrance.p2.P1ToP2;
import com.disney.handler.entrance.p2.P3ToP2;
import com.disney.handler.entrance.p2.P4ToP2;
import com.disney.handler.entrance.p3.P1ToP3;
import com.disney.handler.entrance.p3.P2ToP3;
import com.disney.handler.entrance.p3.P4ToP3;
import com.disney.handler.entrance.p4.P1ToP4;
import com.disney.handler.entrance.p4.P2ToP4;
import com.disney.handler.entrance.p4.P3ToP4;

@Service
public class EntranceHandlerImpl implements EntranceHandler {
	
	private Map<String,ParkEntranceHandler> handlers = new HashMap<String,ParkEntranceHandler>();

	@Override
	public String getEntrance(String from, String to) {
		
		ParkEntranceHandler handler = handlers.get(from.substring(0,7)+"-"+to.substring(0,7));
		
		if(handler==null){
			
			//P1
			if(from.startsWith("03-0002") && to.startsWith("03-0001")){
				handler = new P2ToP1();
			}
			
			if(from.startsWith("03-0003") && to.startsWith("03-0001")){
				handler = new P3ToP1();
			}
			
			
			if(from.startsWith("03-0004") && to.startsWith("03-0001")){
				handler = new P4ToP1();
			}
			
			//P2
			if(from.startsWith("03-0001") && to.startsWith("03-0002")){
				handler = new P1ToP2();
			}
			
			if(from.startsWith("03-0003") && to.startsWith("03-0002")){
				handler = new P3ToP2();
			}
			
			if(from.startsWith("03-0004") && to.startsWith("03-0002")){
				handler = new P4ToP2();
			}
			
			//P3
			if(from.startsWith("03-0002") && to.startsWith("03-0003")){
				handler = new P2ToP3();
			}
			
			if(from.startsWith("03-0004") && to.startsWith("03-0003")){
				handler = new P4ToP3();
			}
			
			
			if(from.startsWith("03-0001") && to.startsWith("03-0003")){
				handler = new P1ToP3();
			}
			
			//P4
			if(from.startsWith("03-0002") && to.startsWith("03-0004")){
				handler = new P2ToP4();
			}
			
			if(from.startsWith("03-0003") && to.startsWith("03-0004")){
				handler = new P3ToP4();
			}
			
			
			if(from.startsWith("03-0001") && to.startsWith("03-0004")){
				handler = new P1ToP4();
			}
			
			
			handlers.put(from.substring(0,7)+"-"+to.substring(0,7), handler);
		}
		
		return handler!=null ? handler.getEntrance(from) : null;
	}

}
