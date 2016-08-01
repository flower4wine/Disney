package com.disney.web.controller.generate.hotel;

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
@RequestMapping("/hotel")
public class BusToHotel extends GenerateBaseController{

	@RequestMapping(value="/bus",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> bus(){

		//只有外部导览
		generate("02-0001-0001",true,"2路","02-0001-0001","02-0001-0003",3);
		generate("02-0001-0002",true,"2路","02-0001-0002","02-0001-0003",2);
		generate("02-0001-0003",false,"","","",0);
		generate("02-0001-0004",true,"1路","02-0001-0004","02-0001-0011",1);
		generate("02-0001-0005",true,"3路","02-0001-0010","02-0001-0011",1);
		generate("02-0001-0006",true,"2路","02-0001-0006","02-0001-0003",4);
		generate("02-0001-0007",true,"2路","02-0001-0007","02-0001-0003",3);
		generate("02-0001-0008",true,"2路","02-0001-0001","02-0001-0003",2);
		generate("02-0001-0009",true,"2路","02-0001-0006","02-0001-0003",4);
		generate("02-0001-0010",true,"3路","02-0001-0010","02-0001-0011",1);
		generate("02-0001-0011",false,"","","",0);
		generate("02-0001-0012",true,"2路","02-0001-0002","02-0001-0003",1);
		
		return Ajax.buildSuccessResult();

	}


	private void generate(String from,boolean bus,String line,String fromBus,String toBus,int busNum){

		String to = "01-0001-0001";
		//CHECK
		if(locationService.find(from) == null || locationService.find(to) == null ){
			return;
		}
		
		 FromToOptimize fromTo = geFromTo(from, to, bus, line, fromBus, toBus, busNum);
		 locationService.addFromTo(fromTo);
		 
		 generateLo2Lo(from,to,"",fromTo,Lo2LoType.BUSSTATION_2_VIEW);

	}
}