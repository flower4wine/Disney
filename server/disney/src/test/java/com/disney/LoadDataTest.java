package com.disney;

import java.text.ParseException;

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
    public void testAdd() throws ParseException {
		//测试时间
		/*SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   java.util.Date begin=dfs.parse("2016-04-26 06:09:48");
		   java.util.Date end = new Date();
		   long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒

		   long day1=between/(24*3600);
		   long hour1=between%(24*3600)/3600;
		   long minute1=between%3600/60;
		   long second1=between%60/60;
		   System.out.println(""+day1+"天"+hour1+"小时"+minute1+"分"+second1+"秒");*/
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
