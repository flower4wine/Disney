package com.disney;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:testconfig/applicationContext.xml", 
		"classpath:testconfig/dispatcherServlet.xml"})  
@Transactional
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class LoadDataTest {

  
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
    
	
}
