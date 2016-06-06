package com.disney.web.controller.generate.busstation;

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
@RequestMapping("/bus")
public class BusToP3 extends GenerateBaseController{

	@RequestMapping(value="/p3",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p3(){

		//只有外部导览
		generate("02-0001-0001",false,"","","",0);
		
		generate("02-0001-0002",true,"3路","02-0001-0012","02-0001-0008",1);
		generate("02-0001-0003",true,"3路","02-0001-0011","02-0001-0008",2);
		generate("02-0001-0004",true,"1路","02-0001-0004","02-0001-0008",3);
		
		generate("02-0001-0005",true,"2路","02-0001-0005","02-0001-0001",3);
		generate("02-0001-0006",true,"2路","02-0001-0006","02-0001-0001",2);
		generate("02-0001-0007",true,"2路","02-0001-0007","02-0001-0001",1);
		
		generate("02-0001-0008",false,"","","",0);
		
		generate("02-0001-0009",true,"2路","02-0001-0006","02-0001-0001",2);
		generate("02-0001-0010",true,"2路","02-0001-0005","02-0001-0001",3);
		
		generate("02-0001-0011",true,"3路","02-0001-0011","02-0001-0008",2);
		generate("02-0001-0012",true,"3路","02-0001-0012","02-0001-0008",1);
		
		return Ajax.buildSuccessResult();

	}

	private void generate(String from,boolean bus,String line,String fromBus,String toBus,int busNum){

		List<Integer> cList = new ArrayList<Integer>();
		List<Integer> dList = new ArrayList<Integer>();
		List<Integer> eList = new ArrayList<Integer>();
		List<Integer> fList = new ArrayList<Integer>();


		addCodeToList(cList,"1,2,3,4,5,6,7,8");
		addCodeToList(dList,"9,10,11,12,13,14,15,16,17,18,19,63");
		addCodeToList(eList,"20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,58");
		addCodeToList(fList,"48,49,50,51,52,53,54,55,56,57,59,60,61,62");

		generate(cList,geFromTo(from, "03-0003-000C", bus, line, fromBus, toBus, busNum),from,"03-0003-000C");
		generate(dList,geFromTo(from, "03-0003-000D", bus, line, fromBus, toBus, busNum),from,"03-0003-000D");
		generate(eList,geFromTo(from, "03-0003-000E", bus, line, fromBus, toBus, busNum),from,"03-0003-000E");
		generate(fList,geFromTo(from, "03-0003-000F", bus, line, fromBus, toBus, busNum),from,"03-0003-000F");

	}

	private void generate(List<Integer> codeList,FromToOptimize fromTo,String from,String parkEntrance){
		locationService.addFromTo(fromTo);

		for(Integer code : codeList){
			String to = parkEntrance.substring(0,8) + getQrCodeSuffix(code);
			generateLo2Lo(from,to,parkEntrance,fromTo,Lo2LoType.BUSSTATION_2_PARKINNER);
		}
	}
}