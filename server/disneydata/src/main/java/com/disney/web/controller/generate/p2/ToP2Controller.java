package com.disney.web.controller.generate.p2;

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
import com.disney.web.controller.generate.GenerateFix;

@Controller
@RequestMapping("/p2")
public class ToP2Controller extends GenerateBaseController{

	@RequestMapping(value="/p1",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p1(){
		
		String parkEntrances = "03-0002-000C,03-0002-000D";
		
		//从P1 03-0001-000C出入口到   P2出入口03-0002-000B 找到车位
		for (String parkEntrance : parkEntrances.split(",")) {
			generate("03-0001-000C",parkEntrance,true);
		}
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p3",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p3(){
		
		String parkEntrances = "03-0002-000A,03-0002-000C,03-0002-000D";
		
		//从P3 C D E F出入口到   P2出入口03-0002-000B 找到车位
		for (String parkEntrance : parkEntrances.split(",")) {
			generate("03-0003-000C",parkEntrance,false);
			generate("03-0003-000D",parkEntrance,false);
			generate("03-0003-000E",parkEntrance,false);
			generate("03-0003-000F",parkEntrance,false);
		}
		
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p4",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4(){
		
		String parkEntrances = "03-0002-000A,03-0002-000C,03-0002-000D";
		
		//从P4 C D出入口到   P2出入口03-0002-000B 找到车位
		
		for (String parkEntrance : parkEntrances.split(",")) {
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
		
		 locationService.addFromTo(o2i);
		 
		 List<Integer> list= new ArrayList<Integer>();
			
			if(parkEntrance.equals("03-0002-000A")){
				for (int i = 1; i <= 48; i++) {
					list.add(i);
				}
				list.add(111);
				list.add(112);
			}else if(parkEntrance.equals("03-0002-000C")){
				for (int i = 49; i <= 71; i++) {
					list.add(i);
				}
				list.add(77);
				list.add(78);
				list.add(81);
				list.add(82);
				list.add(84);
				list.add(87);
				
			}else if(parkEntrance.equals("03-0002-000D")){
				for (int i = 72; i <= 76; i++) {
					list.add(i);
				}
				list.add(79);
				list.add(80);
				list.add(83);
				list.add(85);
				list.add(86);
				list.add(113);
				for (int i = 88; i <= 110; i++) {
					list.add(i);
				}
				
			}else{
				for (int i = 1; i <= GenerateFix.P2_QRCODE_NUM; i++) {
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
