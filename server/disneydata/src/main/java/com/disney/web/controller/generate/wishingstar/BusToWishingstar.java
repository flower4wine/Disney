package com.disney.web.controller.generate.wishingstar;

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
@RequestMapping("/wishingstar")
public class BusToWishingstar extends GenerateBaseController{

	@RequestMapping(value="/bus",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> bus(){

		//只有外部导览
		generate("02-0001-0001","04-0004-0001",true,"2路","02-0001-0001","02-0001-0004",3);
		generate("02-0001-0002","04-0004-0001",true,"2路","02-0001-0002","02-0001-0004",2);
		generate("02-0001-0003","04-0004-0001",true,"2路","02-0001-0003","02-0001-0004",1);
		generate("02-0001-0004","04-0004-0001",false,"","","",0);
		generate("02-0001-0005","04-0004-0001",false,"","","",0);
		generate("02-0001-0006","04-0004-0001",true,"2路","02-0001-0006","02-0001-0007",1);
		generate("02-0001-0007","04-0004-0001",false,"","","",0);
		generate("02-0001-0008","04-0004-0001",true,"2路","02-0001-0001","02-0001-0004",3);
		generate("02-0001-0009","04-0004-0001",true,"2路","02-0001-0006","02-0001-0007",1);
		generate("02-0001-0010","04-0004-0001",false,"","","",0);
		generate("02-0001-0011","04-0004-0001",true,"2路","02-0001-0003","02-0001-0004",1);
		generate("02-0001-0012","04-0004-0001",true,"2路","02-0001-0002","02-0001-0004",2);
		
		
		generate("02-0001-0001","04-0004-0002",true,"3路","02-0001-0008","02-0001-0007",1);
		generate("02-0001-0002","04-0004-0002",true,"3路","02-0001-0012","02-0001-0007",2);
		generate("02-0001-0003","04-0004-0002",true,"3路","02-0001-0011","02-0001-0007",3);
		generate("02-0001-0004","04-0004-0002",false,"","","",0);
		generate("02-0001-0005","04-0004-0002",true,"2路","02-0001-0005","02-0001-0007",2);
		generate("02-0001-0006","04-0004-0002",true,"2路","02-0001-0006","02-0001-0007",1);
		generate("02-0001-0007","04-0004-0002",false,"","","",0);
		generate("02-0001-0008","04-0004-0002",true,"3路","02-0001-0008","02-0001-0007",1);
		generate("02-0001-0009","04-0004-0002",true,"2路","02-0001-0006","02-0001-0007",1);
		generate("02-0001-0010","04-0004-0002",true,"2路","02-0001-0005","02-0001-0007",2);
		generate("02-0001-0011","04-0004-0002",true,"3路","02-0001-0011","02-0001-0007",3);
		generate("02-0001-0012","04-0004-0002",true,"3路","02-0001-0012","02-0001-0007",2);
		
		
		return Ajax.buildSuccessResult();

	}


	private void generate(String from,String to,boolean bus,String line,String fromBus,String toBus,int busNum){

		//CHECK
		if(locationService.find(from) == null || locationService.find(to) == null ){
			return;
		}
		
		 FromToOptimize fromTo = geFromTo(from, to, bus, line, fromBus, toBus, busNum);
		 locationService.addFromTo(fromTo);
		 
		 generateLo2Lo(from,to,"",fromTo,Lo2LoType.BUSSTATION_2_VIEW);

	}
}