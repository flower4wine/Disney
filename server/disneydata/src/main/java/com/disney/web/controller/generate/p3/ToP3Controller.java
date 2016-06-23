package com.disney.web.controller.generate.p3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.constant.Lo2LoType;
import com.disney.model.FromToOptimize;
import com.disney.util.Ajax;
import com.disney.web.controller.generate.GenerateBaseController;

@Controller
@RequestMapping("/p3")
public class ToP3Controller extends GenerateBaseController{
	
	@RequestMapping(value="/p1",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p1(){
		String parkEntrances = "03-0003-000C,03-0003-000D,03-0003-000E,03-0003-000F";
		//String parkEntrance = "03-0003-000C";
		
		for (String parkEntrance : parkEntrances.split(",")) {
			//从P1 03-0001-000C出入口分别到   P3出入口C D E F 找到车位
			generate("03-0001-000C",parkEntrance,true);
		}
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p2",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p2(){
		String parkEntrances = "03-0003-000C,03-0003-000D,03-0003-000E,03-0003-000F";
		//String parkEntrance = "03-0003-000C";
		
		for (String parkEntrance : parkEntrances.split(",")) {
			//从P2 03-0002-000A出入口分别到   P3出入口C D E F 找到车位
			generate("03-0002-000A",parkEntrance,false);
			generate("03-0002-000C",parkEntrance,false);
			generate("03-0002-000D",parkEntrance,false);
		}
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p4",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4(){
		
		String parkEntrances = "03-0003-000A,03-0003-000B";
		
		for (String parkEntrance : parkEntrances.split(",")) {
			//从P4 C D 出入口分别到   P3出入口03-0003-000A,03-0003-000B 找到车位
			generate("03-0004-000C",parkEntrance,false);
			generate("03-0004-000D",parkEntrance,false);
		}
		
		return Ajax.buildSuccessResult();
		
	}
	
	private void generate(String viewCode,String parkEntrance,boolean bus){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}
		FromToOptimize o2i = new FromToOptimize();
		if(bus){
			o2i = geFromTo(viewCode, parkEntrance, true, "3路", "02-0001-0011", "02-0001-0008", 2);
		}else{
			o2i = geFromTo(viewCode, parkEntrance, false, "", null, null, 0);
		}
		
		List<Integer> list= new ArrayList<Integer>();
		
		if(parkEntrance.equals("03-0003-000C")){
			for (int i = 1; i <= 8; i++) {
				list.add(i);
			}
		}else if(parkEntrance.equals("03-0003-000D")){
			for (int i = 9; i <= 19; i++) {
				list.add(i);
			}
			list.add(63);
		}else if(parkEntrance.equals("03-0003-000E")){
			for (int i = 20; i <= 47; i++) {
				list.add(i);
			}
			list.add(58);
		}else if(parkEntrance.equals("03-0003-000F")){
			for (int i = 48; i <= 57; i++) {
				list.add(i);
			}
			for (int i = 59; i <= 62; i++) {
				list.add(i);
			}
		}else if(parkEntrance.equals("03-0003-000A")){
			for (int i = 1; i <= 35; i++) {
				list.add(i);
			}
			list.add(63);
			
		}else if(parkEntrance.equals("03-0003-000B")){
			for (int i = 36; i <= 62; i++) {
				list.add(i);
			}
		}else{
			for (int i = 1; i <= 63; i++) {
				list.add(i);
			}
		}
		 locationService.addFromTo(o2i);
		 
		//Generate location to location
		 for (Integer suffix : list) {
			//inner
				String from = viewCode;
				String to = parkEntrance.substring(0,8) + getQrCodeSuffix(suffix);
				generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.PARKINNER_2_PARKINNER);
		}
		 
	}
	
}
