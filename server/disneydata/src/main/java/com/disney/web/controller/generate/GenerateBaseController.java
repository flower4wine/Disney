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
import com.disney.model.LocationTimeDistance;
import com.disney.service.Lo2loService;
import com.disney.service.LocationService;

public class GenerateBaseController  {

	@Autowired
	protected Lo2loService lo2loService;

	@Autowired
	protected LocationService locationService;


	protected void addCodeToList(List<Integer> list,String str){
		for(String s:str.split(",")){
			list.add(Integer.valueOf(s));
		}
	}
	
	protected FromToOptimize geFromTo(String from,String to,boolean bus,String line,String fromBus,String toBus,int busStationNum){

		FromToOptimize ft = new FromToOptimize();

		ft.setFromCode(from);
		ft.setToCode(to);
		ft.setFromBus(fromBus);
		ft.setToBus(toBus);
		ft.setBus(bus);
		ft.setLine(line);
		
		ft.setBusStationNum(busStationNum);

		return ft;

	}

	protected String getQrCodeSuffix(int num){
		String str = "0000"+num;
		return str.substring(str.length()-4, str.length());
	}
	
	/**
	 * 跟停车场相关的 导览
	 * @param from
	 * @param to
	 * @param parkEntrance
	 * @param ft
	 * @param lo2loType
	 */
	protected void generateLo2Lo(String from,String to,String parkEntrance,FromToOptimize ft,Integer lo2loType){

		LoToLo lo = new LoToLo();

		lo.setFromQrCode(from);
		lo.setToQrCode(to);
		
		int time = 0;
		int distance =  0;
		
		LocationTimeDistance ltd1 = null;
		LocationTimeDistance ltd2 = null;

		if(lo2loType == Lo2LoType.PARKINNER_2_VIEW){
			
			lo.setOutUrl("/resources/img/lotolo/out/" +to.substring(0,7)+ "/"+parkEntrance+"-"+to+".jpg");
			lo.setInnerUrl("/resources/img/lotolo/inner/"+from.substring(0,7)+"/"+from+"-"+parkEntrance+".jpg");
			
			//获得From 到 parkEntrance 距离时间  
			 ltd1 = lo2loService.getLocationTimeDistance(from,parkEntrance);
			//获得parkEntrance 到 to的距离时间
			 ltd2 = lo2loService.getLocationTimeDistance(parkEntrance,to);
			
		}else if(lo2loType == Lo2LoType.VIEW_2_PARKINNER){
			
			lo.setOutUrl("/resources/img/lotolo/out/"+from.substring(0,7)+ "/"+from+"-"+parkEntrance+".jpg");
			lo.setInnerUrl("/resources/img/lotolo/inner/"+to.substring(0,7)+"/"+parkEntrance+"-"+to+".jpg");
			
			//获得to 到 parkEntrance 距离时间  
			 ltd1 = lo2loService.getLocationTimeDistance(to,parkEntrance);
			//获得parkEntrance 到 from的距离时间
			 ltd2 = lo2loService.getLocationTimeDistance(parkEntrance,from);
			
			
		}else if(lo2loType == Lo2LoType.PARKINNER_2_PARKINNER){
			
			lo.setOutUrl("/resources/img/lotolo/out/"+to.substring(0,7)+ "/"+from+"-"+parkEntrance+".jpg");
			lo.setInnerUrl("/resources/img/lotolo/inner/"+to.substring(0,7)+"/"+parkEntrance+"-"+to+".jpg");
			
			//获得From 到 parkEntrance 距离时间  
			 ltd1 = lo2loService.getLocationTimeDistance(from,parkEntrance);
			//获得parkEntrance 到 to的距离时间
			 ltd2 = lo2loService.getLocationTimeDistance(parkEntrance,to);
			
			
		}else if( lo2loType == Lo2LoType.BUSSTATION_2_VIEW){
			//接驳站为一个目录
			lo.setOutUrl("/resources/img/lotolo/out/" +from+ "/"+from+"-"+to+".jpg");
			
			//获得From 到 to 距离时间  
			ltd1 = lo2loService.getLocationTimeDistance(from,to);
			
		}else if(lo2loType == Lo2LoType.BUSSTATION_2_PARKINNER){
			//同 景点到停车场内部   接驳站为一个目录
			lo.setOutUrl("/resources/img/lotolo/out/"+from+ "/"+from+"-"+parkEntrance+".jpg");
			lo.setInnerUrl("/resources/img/lotolo/inner/"+to.substring(0,7)+"/"+parkEntrance+"-"+to+".jpg");
			
			//获得From 到 parkEntrance 距离时间  
			 ltd1 = lo2loService.getLocationTimeDistance(from,parkEntrance);
			//获得parkEntrance 到 to的距离时间
			 ltd2 = lo2loService.getLocationTimeDistance(parkEntrance,to);
		}
		
		if(ltd1!=null){
			time+= ltd1.getTime();
			distance += ltd1.getDistance();
		}
		
		if(ltd2!=null){
			time+= ltd2.getTime();
			distance += ltd2.getDistance();
		}

		lo.setLo2loType(lo2loType);

		lo.setTime(time+"");
		lo.setDistince(distance+"");

		List<LoToLoStep> innerSteps = getInnerStep(from, ft,lo2loType);
		List<LoToLoStep> outSteps = getOutStep(ft);

		innerSteps.addAll(outSteps);

		lo2loService.saveLo2Lo(lo, innerSteps);

	}

	private List<LoToLoStep> getInnerStep(String startCode,FromToOptimize fromTo,Integer lo2loType){
		List<LoToLoStep> list = new ArrayList<LoToLoStep>();
		
		if(lo2loType == Lo2LoType.BUSSTATION_2_VIEW){
			return list;
		}
		
		LoToLoStep s = new LoToLoStep();
		s.setStep(1);
		s.setType(Lo2LoStepType.IN);
				
		if(lo2loType == Lo2LoType.PARKINNER_2_VIEW || lo2loType == Lo2LoType.PARKINNER_2_BUSSTATION){
			//停车导览
			Location from = locationService.find(fromTo.getFromCode());
			s.setRemark("从停车区域步行到"+from.getName());
			
		}else {
			Location to = locationService.find(fromTo.getToCode());
			s.setRemark("从"+to.getName()+"步行到停车区域");
		}
		
		s.setStepType(Lo2LoStepType.WALK);

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

			LoToLoStep s = null;
			int i=1;
			
			if( !fromTo.getFromCode().equals(fromTo.getFromBus())){
				s = new LoToLoStep();
				s.setStep(i);
				s.setType(Lo2LoStepType.OUT);
				s.setRemark("从"+from.getName()+"步行到"+fromBus.getName());
				s.setStepType(Lo2LoStepType.WALK);
				i++;
				list.add(s);
			}

			s = new LoToLoStep();
			s.setStep(i);
			s.setType(Lo2LoStepType.OUT);
			s.setRemark("乘坐接驳车"+fromTo.getLine()+fromTo.getBusStationNum()+"站," +"从"+fromBus.getName()+"上车,到"+toBus.getName()+"下车");
			s.setStepType(Lo2LoStepType.BUS);
			i++;

			list.add(s);
			
			
			if( !fromTo.getToCode().equals(fromTo.getToBus())){
				s = new LoToLoStep();
				s.setStep(i);
				s.setType(Lo2LoStepType.OUT);
				s.setRemark("从"+toBus.getName()+"步行到"+to.getName());
				s.setStepType(Lo2LoStepType.WALK);
				list.add(s);
			}

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
