package com.disney.web.controller.generate.p1;

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
@RequestMapping("/p1")
public class ToP1Controller extends GenerateBaseController{

	@RequestMapping(value="/p2",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p2(){
		
		String parkEntrance = "03-0001-000C";
		
		//从P2 03-0002-000B出入口到   P1出入口03-0001-000C 找到车位
		
		generate("03-0002-000B",parkEntrance);
		return Ajax.buildSuccessResult();
		
	}
	
	private void generate(String viewCode,String parkEntrance){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}
		
		 FromToOptimize o2i = geFromTo(viewCode, parkEntrance, true, true, "02-0001-0001", "02-0001-0003", 2);
		 locationService.addFromTo(o2i);
		 
		//Generate location to location
		 for(int i=0;i<50;i++){
			//inner
			String from = viewCode;
			String to =  parkEntrance.substring(0,8) + getQrCodeSuffix(i+1);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.PARKINNER_2_PARKINNER);
		}
	}
	
}