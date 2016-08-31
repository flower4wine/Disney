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
import com.disney.web.controller.generate.GenerateFix;

@Controller
@RequestMapping("/wishingstar")
public class P4ToWishingstarController extends GenerateBaseController{

	@RequestMapping(value="/p4",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> p4Generate(){

		generate("03-0004-000C","04-0004-0001");
		generate("03-0004-000D","04-0004-0001");
		generate("03-0004-000B","04-0004-0002");

		return Ajax.buildSuccessResult();
	}

	private void generate(String parkEntrance,String viewCode){
		//CHECK
		if(locationService.find(viewCode) == null || locationService.find(parkEntrance) == null ){
			return;
		}
		
		List<Integer> list= new ArrayList<Integer>();
		
		if(parkEntrance.equals("03-0004-000C")){
			for (int i = 1; i <= 25; i++) {
				list.add(i);
			}
			list.add(64);
			for (int i = 67; i <= 70; i++) {
				list.add(i);
			}
			list.add(74);
		}else if(parkEntrance.equals("03-0004-000D")){
			for (int i = 26; i <= 63; i++) {
				list.add(i);
			}
			list.add(65);
			list.add(66);
			for (int i = 71; i <= 73; i++) {
				list.add(i);
			}
		}else{
			for (int i = 1; i <= GenerateFix.P4_QRCODE_NUM; i++) {
				list.add(i);
			}
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
			 i2o = geFromTo(parkEntrance, viewCode, false, "", "", "", 0);
			 locationService.addFromTo(i2o);
			 
			 o2i = geFromTo(viewCode, parkEntrance, false, "", "", "", 0);
			 locationService.addFromTo(o2i);
		}

		
		for (Integer suffix : list) {
			//停车场内部到景点
			String from = parkEntrance.substring(0,8)+getQrCodeSuffix(suffix);
			String to = viewCode;
			generateLo2Lo(from,to,parkEntrance,i2o,Lo2LoType.PARKINNER_2_VIEW);
		}
		
		for (Integer suffix : list) {
			//景点到停车场内部
			String from = viewCode;
			String to = parkEntrance.substring(0,8)+getQrCodeSuffix(suffix);
			generateLo2Lo(from,to,parkEntrance,o2i,Lo2LoType.VIEW_2_PARKINNER);
		}

	}
}
