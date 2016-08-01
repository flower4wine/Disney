package com.disney.web.controller.generate.ecological;

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
@RequestMapping("/ecological")
public class P4ToEcologicalController extends GenerateBaseController{

	@RequestMapping(value="/p4",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4Generate(){

		generate("03-0004-000B","04-0003-0001");

		return Ajax.buildSuccessResult();
	}

	private void generate(String parkEntrance,String viewCode){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}

		//Generate from to
		FromToOptimize i2o = geFromTo(parkEntrance, viewCode, false, "", "", "", 0);
		locationService.addFromTo(i2o);

		FromToOptimize o2i = geFromTo(viewCode, parkEntrance, false, "", "", "", 0);
		locationService.addFromTo(o2i);

		
		for(int i=0;i<GenerateFix.P4_QRCODE_NUM;i++){
			//停车场内部到景点
			String from = parkEntrance.substring(0,8)+getQrCodeSuffix(i);
			String to = viewCode;
			generateLo2Lo(from,to,parkEntrance,i2o,Lo2LoType.PARKINNER_2_VIEW);
		}
		
		for(int i=0;i<GenerateFix.P4_QRCODE_NUM;i++){
			//景点到停车场内部
			String from = viewCode;
			String to = parkEntrance.substring(0,8)+getQrCodeSuffix(i);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.VIEW_2_PARKINNER);
		}

	}
}
