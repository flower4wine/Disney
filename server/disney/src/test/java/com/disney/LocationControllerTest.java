package com.disney;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.bo.QrCodeBO;
import com.disney.service.LocationService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:testconfig/applicationContext.xml", 
		"classpath:testconfig/dispatcherServlet.xml"})  
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class LocationControllerTest {

	@Autowired
	private LocationService locationService;
  
  
    @Test  
    public void testAdd() {
    	/*String from = "03-0001-0001";
    	String to = "01-0001-000A";
    	
    	LoToLoBO bo = getLoToLoBO(from,to);
    	//locationService.saveLoToLoBO(bo);
    	
    	Assert.assertNotNull(locationService.loadLoToLoBO(from, to));*/
    	
    }
    
    private LoToLoBO getLoToLoBO(String fromC,String toC){
    	
    	LoToLoBO bo = new LoToLoBO();
    	
    	QrCodeBO from = new QrCodeBO();
    	from.setQrode(fromC);
		bo.setFrom(from );
		
    	QrCodeBO to = new QrCodeBO();
    	to.setQrode(toC);
		bo.setTo(to);
		
		bo.setTime("5分钟");
		bo.setDistince("1公里");
		
		bo.setInnerUrl("/resources/img/location/p4/inner/01-0004-0001-P4.jpg");
		bo.setOutUrl("/resources/img/location/p4/out/P4-02-0001-0001.jpg");
		
		List <LoToLoStepBO> steps = new ArrayList<LoToLoStepBO>();
		
		for(String a:"P4停车场0002停车区域,向西x米".split(",")){
			LoToLoStepBO t=getLoToLoStepBO(a,1);
			steps.add(t);
		}
		bo.setInnerSteps(steps);
		
		steps = new ArrayList<LoToLoStepBO>();
		for(String a:"出口乘坐接泊车,乘坐导览线2站到迪斯尼酒店下车".split(",")){
			LoToLoStepBO t=getLoToLoStepBO(a,1);
			steps.add(t);
		}
		
		bo.setOutSteps(steps);	
    	return bo;
    }
    
    private LoToLoStepBO getLoToLoStepBO(String remark,Integer i){
    	LoToLoStepBO bo = new LoToLoStepBO();
    	
    	bo.setRemark(remark);
    	bo.setStep(i);
    	
    	return bo;
    }
	
}
