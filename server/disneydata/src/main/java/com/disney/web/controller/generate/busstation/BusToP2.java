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
public class BusToP2 extends GenerateBaseController{

	@RequestMapping(value="/p2",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p2(){

		//只有外部导览
		generate("02-0001-0001",false,"","","",0);

		generate("02-0001-0002",true,"3路","02-0001-0012","02-0001-0008",1);
		generate("02-0001-0003",true,"3路","02-0001-0011","02-0001-0008",2);

		generate("02-0001-0004",true,"1路","02-0001-0004","02-0001-0008",3);
		generate("02-0001-0005",true,"3路","02-0001-0010","02-0001-0008",3);

		generate("02-0001-0006",true,"2路","02-0001-0006","02-0001-0001",2);
		generate("02-0001-0007",true,"2路","02-0001-0007","02-0001-0001",1);

		generate("02-0001-0008",false,"","","",0);

		generate("02-0001-0009",true,"2路","02-0001-0006","02-0001-0001",2);

		generate("02-0001-0010",true,"3路","02-0001-0010","02-0001-0008",3);
		generate("02-0001-0011",true,"3路","02-0001-0011","02-0001-0008",2);
		generate("02-0001-0012",true,"3路","02-0001-0012","02-0001-0008",1);

		return Ajax.buildSuccessResult();

	}


	private void generate(String from,boolean bus,String line,String fromBus,String toBus,int busNum){

		List<Integer> cList = new ArrayList<Integer>();

		for (int i = 1; i <= 71; i++) {
			cList.add(i);
		}
		cList.add(77);
		cList.add(78);
		cList.add(81);
		cList.add(82);
		cList.add(84);
		cList.add(87);
		cList.add(111);
		cList.add(112);

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

		generate(cList,geFromTo(from, "03-0002-000C", bus, line, fromBus, toBus, busNum),from,"03-0002-000C");
		generate(dList,geFromTo(from, "03-0002-000D", bus, line, fromBus, toBus, busNum),from,"03-0002-000D");

	}

	private void generate(List<Integer> codeList,FromToOptimize fromTo,String from,String parkEntrance){
		locationService.addFromTo(fromTo);

		for(Integer code : codeList){
			String to = parkEntrance.substring(0,8) + getQrCodeSuffix(code);
			generateLo2Lo(from,to,parkEntrance,fromTo,Lo2LoType.BUSSTATION_2_PARKINNER);
		}
	}

}