package com.disney.web.controller.generate.wishingstar;

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
@RequestMapping("/wishingstar")
public class P2ToWishingstarController extends GenerateBaseController{

	@RequestMapping(value="/p2",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4Generate(){


		generate("04-0004-0001");
		generate("04-0004-0002");

		return Ajax.buildSuccessResult();
	}

	private void generate(String viewCode){

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
		
		generate(cList,"03-0002-000C",viewCode);
		generate(dList,"03-0002-000D",viewCode);

	}

	private void generate(List<Integer> qrCodes,String parkEntrance,String viewCode){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}

		FromToOptimize i2o = new FromToOptimize();
		FromToOptimize o2i = new FromToOptimize();
		if(viewCode.equals("04-0004-0001")){
			//Generate from to
			 i2o = geFromTo(parkEntrance, viewCode, true, "2路", "02-0001-0001", "02-0001-0004", 3);
			 locationService.addFromTo(i2o);
			 
			 o2i = geFromTo(viewCode, parkEntrance, true, "1路", "02-0001-0004", "02-0001-0008", 3);
			 locationService.addFromTo(o2i);
		}else{
			//Generate from to
			 i2o = geFromTo(parkEntrance, viewCode, true, "3路", "02-0001-0008", "02-0001-0007", 1);
			 locationService.addFromTo(i2o);
			 
			 o2i = geFromTo(viewCode, parkEntrance, true, "2路", "02-0001-0007", "02-0001-0001", 1);
			 locationService.addFromTo(o2i);
		}

		
		for(Integer i:qrCodes){
			//停车场内部到景点
			String from = parkEntrance.substring(0,8)+getQrCodeSuffix(i);
			String to = viewCode;
			generateLo2Lo(from,to,parkEntrance,i2o,Lo2LoType.PARKINNER_2_VIEW);
		}
		
		for(Integer i:qrCodes){
			//景点到停车场内部
			String from = viewCode;
			String to = parkEntrance.substring(0,8)+getQrCodeSuffix(i);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.VIEW_2_PARKINNER);
		}

	}
}
