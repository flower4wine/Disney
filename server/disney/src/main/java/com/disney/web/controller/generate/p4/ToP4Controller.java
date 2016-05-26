package com.disney.web.controller.generate.p4;

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
@RequestMapping("/p4")
public class ToP4Controller extends GenerateBaseController{
	
	@RequestMapping(value="/p1",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p1(){
		String parkEntrances = "03-0004-000C,03-0004-000D";
		
		//从P1 03-0001-000C 出入口到   P4出入口 C D 找到车位
		
		for (String parkEntrance : parkEntrances.split(",")) {
			generate("03-0001-000C",parkEntrance,true);
		}
		
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p2",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p2(){
		String parkEntrances = "03-0004-000C,03-0004-000D";
		
		//从P2 03-0002-000A 出入口到   P4出入口 C D 找到车位
		
		for (String parkEntrance : parkEntrances.split(",")) {
			generate("03-0002-000A",parkEntrance,false);
		}
		
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p3",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p3(){
		String parkEntrances = "03-0004-000C,03-0004-000D";
		
		//从P3 A B 出入口到   P4出入口 C D 找到车位
		for (String parkEntrance : parkEntrances.split(",")) {
			generate("03-0003-000A",parkEntrance,false);
			generate("03-0003-000B",parkEntrance,false);
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
			o2i = geFromTo(viewCode, parkEntrance, true, true, "02-0001-0011", "02-0001-0008", 2);
		}else{
			o2i = geFromTo(viewCode, parkEntrance, false, false, null, null, 0);
		}
		
		 locationService.addFromTo(o2i);
		 
		//Generate location to location
		 for(int i=0;i<63;i++){
			//inner
			String from = viewCode;
			String to = parkEntrance.substring(0,8) + getQrCodeSuffix(i+1);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.PARKINNER_2_PARKINNER);
		}
	}
	
}