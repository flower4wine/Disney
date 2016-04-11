package com.disney.test;

import java.util.ArrayList;
import java.util.List;

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

import junit.framework.Assert;

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
    	int first = locationService.findAll().size();
    	
    	LoToLoBO bo = getLoToLoBO();
    	
    	locationService.saveLoToLoBO(bo);
    	
    	int second = locationService.findAll().size();
    	Assert.assertTrue(second==first+1);
    }
    
    private LoToLoBO getLoToLoBO(){
    	
    	LoToLoBO bo = new LoToLoBO();
    	QrCodeBO from = new QrCodeBO();
    	from.setQrode("03-0004-0002");
    	from.setCodeTypeName("行");
    	from.setCodeLocationName("P4停车场0002停车区域");
    	from.setQrLocationName("P4停车场0002停车区域");
		bo.setFrom(from );
		
    	QrCodeBO to = new QrCodeBO();
    	to.setQrode("03-0004-0003");
    	to.setCodeTypeName("行");
    	to.setCodeLocationName("P4停车场0003停车区域");
    	to.setQrLocationName("P4停车场0003停车区域");
		bo.setTo(to);
		
		bo.setTime("5分钟");
		bo.setDistince("1公里");
		bo.setInnerUrl("/resources/img/location/p4/inner/01-0004-0001-P4.jpg");
		bo.setOutUrl("/resources/img/location/p4/out/P4-02-0001-0001.jpg");
		
		List <LoToLoStepBO> innerSteps = new ArrayList();
		
		for(String a:"P4停车场0002停车区域,向西x米".split(",")){
			
			LoToLoStepBO t=getLoToLoStepBO(a,1);
			
			innerSteps.add(t);
			
		}
				
			
		bo.setInnerSteps(innerSteps );
    	
    	return bo;
    }
    
    private LoToLoStepBO getLoToLoStepBO(String remark,Integer i){
    	
    	return null;
    }
	
}
