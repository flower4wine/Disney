package com.disney.web.controller.generate.herbgarden;

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
@RequestMapping("/herbgarden")
public class P1ToHerbgardenController extends GenerateBaseController{

	
	@RequestMapping(value="/p1",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p1Generate(){
		
		String parkEntrance = "03-0001-000C";
		
		generate("04-0002-0001",parkEntrance);
		generate("04-0002-0002",parkEntrance);
		
		return Ajax.buildSuccessResult();
	}
	
	private void generate(String viewCode,String parkEntrance){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}
		
		FromToOptimize i2o = new FromToOptimize();
		FromToOptimize o2i = new FromToOptimize();
		
		if(viewCode.equals("04-0002-0001")){
			//Generate from to
			 i2o = geFromTo(parkEntrance, viewCode, true, "3路", "02-0001-0011", "02-0001-0007", 3);
			 locationService.addFromTo(i2o);
			 
			 o2i = geFromTo(viewCode, parkEntrance, true, "2路", "02-0001-0007", "02-0001-0003", 3);
			 locationService.addFromTo(o2i);
		}else{
			//Generate from to
			 i2o = geFromTo(parkEntrance, viewCode, true, "3路", "02-0001-0011", "02-0001-0008", 2);
			 locationService.addFromTo(i2o);
			 
			 o2i = geFromTo(viewCode, parkEntrance, true, "2路", "02-0001-0001", "02-0001-0003", 2);
			 locationService.addFromTo(o2i);
		}
		 
		//Generate location to location
		 for(int i=0;i<GenerateFix.P1_QRCODE_NUM;i++){
			//inner
			String from = parkEntrance.substring(0,8) + getQrCodeSuffix(i+1);
			String to = viewCode;
			generateLo2Lo(from,to,parkEntrance,i2o,Lo2LoType.PARKINNER_2_VIEW);
		}
		 
		 for(int i=0;i<GenerateFix.P1_QRCODE_NUM;i++){
			//inner
			String from = viewCode;
			String to = parkEntrance.substring(0,8) + getQrCodeSuffix(i+1);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.VIEW_2_PARKINNER);
		}
	}

}
