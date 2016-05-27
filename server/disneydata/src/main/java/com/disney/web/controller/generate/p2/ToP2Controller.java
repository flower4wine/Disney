package com.disney.web.controller.generate.p2;

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
		
		String parkEntrance = "03-0002-000B";
		
		//从P1 03-0001-000C出入口到   P2出入口03-0002-000B 找到车位
		
		generate("03-0001-000C",parkEntrance,true);
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p3",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p3(){
		
		String parkEntrance = "03-0002-000A";
		
		//从P3 C D E F出入口到   P2出入口03-0002-000B 找到车位
		
		generate("03-0003-000C",parkEntrance,false);
		generate("03-0003-000D",parkEntrance,false);
		generate("03-0003-000E",parkEntrance,false);
		generate("03-0003-000F",parkEntrance,false);
		
		return Ajax.buildSuccessResult();
		
	}
	
	@RequestMapping(value="/p4",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4(){
		
		String parkEntrance = "03-0002-000A";
		
		//从P4 C D出入口到   P2出入口03-0002-000B 找到车位
		
		generate("03-0004-000C",parkEntrance,false);
		generate("03-0004-000D",parkEntrance,false);
		
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
		 for(int i=0;i<GenerateFix.P1_QRCODE_NUM;i++){
			//inner
			String from = viewCode;
			String to = parkEntrance.substring(0,8) + getQrCodeSuffix(i+1);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.PARKINNER_2_PARKINNER);
		}
	}
	
}
