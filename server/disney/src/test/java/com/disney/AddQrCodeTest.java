package com.disney;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.disney.model.Location;
import com.disney.service.LocationService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:testconfig/applicationContext.xml", 
		"classpath:testconfig/dispatcherServlet.xml"})  
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class AddQrCodeTest {

	@Autowired
	private LocationService locationService;
  
  
    @Test  
    public void testAdd() {
    	
    	//03-0001 P1停车场
    	locationService.addQrCode("03-0001-000A", "P1停车场出入口A",true);
    	locationService.addQrCode("03-0001-000B", "P1停车场出入口B",true);
    	
    	//03-0002 P2停车场
    	locationService.addQrCode("03-0002-000A", "P2停车场出入口A",true);
    	
    	//03-0003 P3停车场
    	locationService.addQrCode("03-0003-000A", "P2停车场出入口A",true);
    	locationService.addQrCode("03-0003-000B", "P3停车场出入口B",true);
    	locationService.addQrCode("03-0003-000C", "P3停车场出入口C",true);
    	
    	//03-0004 P4停车场
    	locationService.addQrCode("03-0004-000A", "P4停车场出入口A",true);
    	locationService.addQrCode("03-0004-000B", "P4停车场出入口B",true);
    	
    	//01-0001 迪斯尼乐园酒店
    	locationService.addQrCode("01-0001-000A", "迪斯尼乐园酒店入口A",true);
    	locationService.addQrCode("01-0001-000B", "迪斯尼乐园酒店入口B",true);
    	
    	//05-0001 奕欧来上海购物村
    	locationService.addQrCode("05-0001-000A", "奕欧来上海购物村入口A",true);
    	
    	
    	Location l = locationService.find("03-0001-000A");
    	Assert.assertNotNull(l);
    }
    
    @Test  
    public void testFromTo() {
    	/*
    	{"from":"03-0001-000A","to":"01-0001-000A"},
    	{"from":"03-0001-000A","to":"05-0001-000A"},

    	{"from":"03-0002-000A","to":"01-0001-000B"},
    	{"from":"03-0002-000A","to":"05-0001-000A"},

    	{"from":"03-0003-000A","to":"01-0001-000B"},
    	{"from":"03-0003-000A","to":"05-0001-000A"},

    	{"from":"03-0004-000A","to":"01-0001-000B"},
    	{"from":"03-0004-000A","to":"05-0001-000A"},*/
    	
    	locationService.addFromTo("03-0001-000A", "01-0001-000A");
    	locationService.addFromTo("03-0001-000A", "05-0001-000A");
    	
    	locationService.addFromTo("03-0002-000A", "01-0001-000b");
    	locationService.addFromTo("03-0002-000A", "05-0001-000A");
    	
    	locationService.addFromTo("03-0003-000A", "01-0001-000b");
    	locationService.addFromTo("03-0003-000A", "05-0001-000A");
    	
    	locationService.addFromTo("03-0004-000A", "01-0001-000B");
    	locationService.addFromTo("03-0004-000A", "05-0001-000A");
    	
    }
    
    
	
}
