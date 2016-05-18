package com.disney;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.disney.service.LocationService;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:testconfig/applicationContext.xml", 
		"classpath:testconfig/dispatcherServlet.xml"})  
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class LocationControllerTest {

	@SuppressWarnings("unused")
	@Autowired
	private LocationService locationService;
  
  
    @Test  
    public void testAdd() {
    	
    }
    
}
