package com.disney;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.disney.constant.Lo2LoStepType;
import com.disney.model.FromToOptimize;
import com.disney.model.LoToLo;
import com.disney.model.LoToLoStep;
import com.disney.model.Location;
import com.disney.service.LocationService;
import com.disney.util.FileUtil;
import com.disney.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:testconfig/applicationContext.xml", 
		"classpath:testconfig/dispatcherServlet.xml"})  
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class LoadDataTest {

	@Autowired
	private LocationService locationService;
  
	@Test  
    public void testAdd() {
    	
    	/*List<LinkedHashMap<String, Object>> all = laodDataFromFile("E:/t-data.json");
    	List<LinkedHashMap<String, Object>> p1 = laodDataFromFile("E:/p1-data.json");
    	List<LinkedHashMap<String, Object>> p2 = laodDataFromFile("E:/p2-data.json");
    	
    	for(Map map: p1){
    		createLo2lo(map,all);
    	}
    	
    	for(Map map: p2){
    		createLo2lo(map,all);
    	}*/
    	
    }
    
   
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void createLo2lo(Map<String,Object> inner,List all){
    	
    	String from = (String) inner.get("from");
    	String to = (String) inner.get("to");
    	
    	FromToOptimize fromTo = locationService.getFromTo(from.substring(0, 7), to.substring(0, 7));
    	
    	if(fromTo==null){
    		return;
    	}
    	
    	String f = fromTo.getFromCode();
    	String t = fromTo.getToCode();
    	
    	Map<String,Object> out = getMap(f,t,all);
    	
    	if(out==null){
    		return;
    	}
    	
    	LoToLo lo = new LoToLo();
    	
    	lo.setFromQrCode(from);
    	lo.setToQrCode(to);
    	lo.setInnerUrl("/resources/img/lotolo"+inner.get("inner").toString());
    	lo.setOutUrl("/resources/img/lotolo"+out.get("out").toString());
    	
    	
    	int time = getIntegerFromMap(inner,"time") +  getIntegerFromMap(out,"time");
    	int distince =  getIntegerFromMap(inner,"distance") +  getIntegerFromMap(out,"distance");
    	
    	lo.setTime(time+"");
    	lo.setDistince(distince+"");
    	
    	List<LoToLoStep> innerSteps = getInnerStep(inner, fromTo);
    	List<LoToLoStep> outSteps = getOutStep(out, fromTo);
    	
    	innerSteps.addAll(outSteps);
    	
    	locationService.saveLo2Lo(lo, innerSteps);
    	
    }
    
    private List<LoToLoStep> getInnerStep(Map<String,Object> inner,FromToOptimize fromTo){
    	LoToLoStep s = new LoToLoStep();
    	
    	s.setStep(1);
    	s.setType(Lo2LoStepType.IN);
    	
    	if(inner.get("from").toString().startsWith("03-")){
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
    
    private List<LoToLoStep> getOutStep(Map<String,Object> out,FromToOptimize fromTo){
    	
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
    
    private Integer getIntegerFromMap(Map<String,Object> map,String key){
    	int value = Integer.valueOf(map.get(key).toString());
    	return value>0 ? value : 1;
    }
    
    private Map<String,Object> getMap(String f,String t,List<Map<String,Object>> list){
    	
    	Map <String,Object> m = null;
    	
    	for(Map<String,Object> map:list){
    		if(f.equals(map.get("from")) && t.equals(map.get("to"))){
    			m = map;
    			break;
    		}
    	}
    	return m;
    }
    
    
    
    
    @SuppressWarnings("unused")
	private List<LinkedHashMap<String, Object>> laodDataFromFile(String path){
    	
    	String json;
		try {
			
			json = FileUtil.readFile(path);
			List<LinkedHashMap<String, Object>> list = JsonUtil.readJson2List(json);
			return list;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
    	
    }
    
	
}
