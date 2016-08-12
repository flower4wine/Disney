package com.disney.web.controller.generate.bifrost;

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
@RequestMapping("/bifrost")
public class P3ToBifrostController  extends GenerateBaseController{

	@RequestMapping(value="/p3",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p3Generate(){


		generate("04-0008-0001");

		return Ajax.buildSuccessResult();
	}

	private void generate(String viewCode){

		List<Integer> cList = new ArrayList<Integer>();
		List<Integer> dList = new ArrayList<Integer>();
		List<Integer> eList = new ArrayList<Integer>();
		List<Integer> fList = new ArrayList<Integer>();


		addCodeToList(cList,"1,2,3,4,5,6,7,8");
		addCodeToList(dList,"9,10,11,12,13,14,15,16,17,18,19,63");
		addCodeToList(eList,"20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,58");
		addCodeToList(fList,"48,49,50,51,52,53,54,55,56,57,59,60,61,62");
		
		generate(cList,"03-0003-000C",viewCode);
		generate(dList,"03-0003-000D",viewCode);
		generate(eList,"03-0003-000E",viewCode);
		generate(fList,"03-0003-000F",viewCode);

	}
	

	private void generate(List<Integer> qrCodes,String parkEntrance,String viewCode){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}

		//Generate from to
		FromToOptimize i2o = geFromTo(parkEntrance, viewCode, true, "2路", "02-0001-0001", "02-0001-0002", 1);
		locationService.addFromTo(i2o);

		FromToOptimize o2i = geFromTo(viewCode, parkEntrance, true, "1路", "02-0001-0012", "02-0001-0008", 1);
		locationService.addFromTo(o2i);

		
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
