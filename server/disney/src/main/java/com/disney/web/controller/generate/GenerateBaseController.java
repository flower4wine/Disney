package com.disney.web.controller.generate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.disney.constant.Lo2LoStepType;
import com.disney.constant.Lo2LoType;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.service.Lo2loService;
import com.disney.service.LocationService;

public class GenerateBaseController  {
	
	@Autowired
	protected Lo2loService lo2loService;
	
	@Autowired
	protected LocationService locationService;


	protected FromToOptimize geFromTo(String from,String to,boolean bus,boolean inside,String fromBus,String toBus,int busStationNum){

		FromToOptimize ft = new FromToOptimize();

		ft.setFromCode(from);
		ft.setToCode(to);
		ft.setFromBus(fromBus);
		ft.setToBus(toBus);
		ft.setBus(bus);
		ft.setInside(inside);
		ft.setBusStationNum(busStationNum);

		return ft;

	}
	
	protected String getQrCodeSuffix(int num){
    	String str = "0000"+num;
		return str.substring(str.length()-4, str.length());
    }
	
	protected void generateLo2Lo(String from,String to,String parkEntrance,FromToOptimize ft,Integer lo2loType){
		
		LoToLo lo = new LoToLo();
		
		lo.setFromQrCode(from);
    	lo.setToQrCode(to);
    	
    	if(lo2loType == Lo2LoType.PARKINNER_2_VIEW){
    		lo.setOutUrl("/resources/img/lotolo/out/"+parkEntrance+"-"+to+".jpg");
    		
    		lo.setInnerUrl("/resources/img/lotolo/inner/05-0001/"+from.substring(0,7)+"/"+from+"-"+parkEntrance+".jpg");
    	}else{
    		lo.setOutUrl("/resources/img/lotolo/out/"+from+"-"+parkEntrance+".jpg");
    		
        	lo.setInnerUrl("/resources/img/lotolo/inner/05-0001/"+to.substring(0,7)+"/"+parkEntrance+"-"+to+".jpg");
    	}
    	
    	lo.setLo2loType(lo2loType);
    	
    	int time = 10;
    	int distince =  1000;
    	
    	lo.setTime(time+"");
    	lo.setDistince(distince+"");
    	
    	List<LoToLoStep> innerSteps = getInnerStep(from, ft);
    	List<LoToLoStep> outSteps = getOutStep(ft);
    	
    	innerSteps.addAll(outSteps);
    	
    	lo2loService.saveLo2Lo(lo, innerSteps);
		
	}
	
	protected List<LoToLoStep> getInnerStep(String startCode,FromToOptimize fromTo){
	    	LoToLoStep s = new LoToLoStep();
	    	
	    	s.setStep(1);
	    	s.setType(Lo2LoStepType.IN);
	    	
	    	if(startCode.startsWith("03-")){
	    		//停车导览
	    		Location from = locationService.find(fromTo.getFromCode());
	    		s.setRemark("从停车区域步行到"+from.getName());
	    	}else{
	    		Location to = locationService.find(fromTo.getToCode());
	    		s.setRemark("从"+to.getName()+"步行到停车区域");
	    	}
	    	
	    	s.setStepType(Lo2LoStepType.WALK);
	    	
	    	List<LoToLoStep> list = new ArrayList<LoToLoStep>();
	    	list.add(s);
	    	return list;
	    }
	    
	    private List<LoToLoStep> getOutStep(FromToOptimize fromTo){
	    	
	    	List<LoToLoStep>  list = new ArrayList<LoToLoStep> ();
	    	
	    	Location from = locationService.find(fromTo.getFromCode());
	    	Location to = locationService.find(fromTo.getToCode());
	    	
	    	if(fromTo.getBus()){
	    		
	        	Location fromBus = locationService.find(fromTo.getFromBus());
	        	Location toBus = locationService.find(fromTo.getToBus());
	        	
	        	LoToLoStep s = new LoToLoStep();
	        	s.setStep(1);
	        	s.setType(Lo2LoStepType.OUT);
	        	s.setRemark("从"+from.getName()+"步行到"+fromBus.getName());
	        	s.setStepType(Lo2LoStepType.WALK);
	        	
	        	list.add(s);
	        	
	        	s = new LoToLoStep();
	         	s.setStep(2);
	         	s.setType(Lo2LoStepType.OUT);
	         	s.setRemark("乘坐接驳车"+(fromTo.getInside()?"内圈":"外圈")+"环线" +
	         	"从"+fromBus.getName()+"上车,到"+toBus.getName()+"下车");
	         	s.setStepType(Lo2LoStepType.BUS);
	         	
	         	list.add(s);
	         	
	         	 s = new LoToLoStep();
	         	s.setStep(3);
	         	s.setType(Lo2LoStepType.OUT);
	         	s.setRemark("从"+toBus.getName()+"步行到"+to.getName());
	         	s.setStepType(Lo2LoStepType.WALK);
	         	list.add(s);
	    		
	    	}else{
	    		LoToLoStep s = new LoToLoStep();
	        	
	        	s.setStep(1);
	        	s.setType(Lo2LoStepType.OUT);
	        	s.setRemark("从"+from.getName()+"步行到"+to.getName());
	        	s.setStepType(Lo2LoStepType.WALK);
	        
	        	list.add(s);
	    	}
	    	
	    	return list;
	    }
	
	
	

}
