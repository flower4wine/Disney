package com.disney.web.controller.generate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.constant.QrCodeFix;
import com.disney.constant.QrCodeType;
import com.disney.handler.codecheck.ExcelCodeLoader;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.service.Lo2loService;
import com.disney.service.LocationService;
import com.disney.util.Ajax;
import com.disney.util.HttpClientUtil;
import com.disney.web.vo.QrCodeVerifyVO;

@Controller
public class GuideDataGenerateController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private Lo2loService lo2loService;
	
	@Autowired
	private ExcelCodeLoader excelCodeLoader;
	
	
	private void loadFromUrl(String url){
		Map<String,Object> map = HttpClientUtil.get(url);
		
		if(map != null && map.get("items")!=null ){
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> items = (List<Map<String, Object>>) map.get("items");
			
			for(Map<String,Object> item:items){
				
				String from = (String) item.get("from");
				String to = (String) item.get("to");
				Integer distance  = (Integer) item.get("distance");
				
				lo2loService.saveLocationDistance(from, to, distance);
			}
		}
	}

	@RequestMapping(value="/generateLocationTimeDistance",method=RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object> generateLocationTimeDistance() throws Exception {
		
		//停车场内部 通过文件导入  
		loadFromUrl("http://localhost/p1-data.json");
		loadFromUrl("http://localhost/p2-data.json");
		loadFromUrl("http://localhost/p3-data.json");
		loadFromUrl("http://localhost/p4-data.json");
		

		//外部停车场到景点
		lo2loService.saveLocationDistance("03-0001-000C", "05-0001-0001", 1600, 100);
		lo2loService.saveLocationDistance("03-0001-000C", "05-0001-0002", 1600, 200);
		lo2loService.saveLocationDistance("03-0001-000C", "05-0001-0003", 1600, 400);
		lo2loService.saveLocationDistance("03-0001-000C", "05-0001-0004", 1600, 500);
		
		lo2loService.saveLocationDistance("03-0001-000C", "04-0001-0001", 1600, 550);
		
		lo2loService.saveLocationDistance("03-0001-000C", "01-0001-0001", 320);
		
		lo2loService.saveLocationDistance("03-0001-000C", "01-0002-0001", 2500, 285);
		
		lo2loService.saveLocationDistance("03-0001-000C", "04-0002-0001", 4200, 780);
		lo2loService.saveLocationDistance("03-0001-000C", "04-0002-0002", 1800, 550);
		
		lo2loService.saveLocationDistance("03-0001-000C", "04-0003-0001", 4200, 640);
		
		lo2loService.saveLocationDistance("03-0001-000C", "04-0004-0001", 1600, 310);
		lo2loService.saveLocationDistance("03-0001-000C", "04-0004-0002", 4200, 275);
		
		lo2loService.saveLocationDistance("03-0001-000C", "05-0002-0001", 800, 180);
		
		lo2loService.saveLocationDistance("03-0001-000C", "06-0002-0001", 1600, 305);
		
		
		lo2loService.saveLocationDistance("03-0002-000C", "05-0001-0001", 100);
		lo2loService.saveLocationDistance("03-0002-000C", "05-0001-0002", 200);
		lo2loService.saveLocationDistance("03-0002-000C", "05-0001-0003", 400);
		lo2loService.saveLocationDistance("03-0002-000C", "05-0001-0004", 500);
		
		lo2loService.saveLocationDistance("03-0002-000C", "04-0001-0001", 2400, 1200);
		
		lo2loService.saveLocationDistance("03-0002-000C", "01-0001-0001", 1350, 830);
		
		lo2loService.saveLocationDistance("03-0002-000C", "01-0002-0001", 3850, 800);
		
		lo2loService.saveLocationDistance("03-0002-000C", "04-0002-0001", 2400, 1000);
		lo2loService.saveLocationDistance("03-0002-000C", "04-0002-0002", 710);
		
		lo2loService.saveLocationDistance("03-0002-000C", "04-0003-0001", 2400, 990);
		
		lo2loService.saveLocationDistance("03-0002-000C", "04-0004-0001", 3850, 820);
		lo2loService.saveLocationDistance("03-0002-000C", "04-0004-0002", 2400, 1090);
		
		lo2loService.saveLocationDistance("03-0002-000C", "05-0002-0001", 800, 180);
		
		lo2loService.saveLocationDistance("03-0002-000C", "06-0002-0001", 2950, 280);
		
		
		lo2loService.saveLocationDistance("03-0002-000D", "05-0001-0001",150);
		lo2loService.saveLocationDistance("03-0002-000D", "05-0001-0002",250);
		lo2loService.saveLocationDistance("03-0002-000D", "05-0001-0003",450);
		lo2loService.saveLocationDistance("03-0002-000D", "05-0001-0004",550);
		
		lo2loService.saveLocationDistance("03-0002-000D", "04-0001-0001", 2400, 1100);
		
		lo2loService.saveLocationDistance("03-0002-000D", "01-0001-0001", 1350, 730);
		
		lo2loService.saveLocationDistance("03-0002-000D", "01-0002-0001", 3850, 700);
		
		lo2loService.saveLocationDistance("03-0002-000D", "04-0002-0001", 2400, 900);
		lo2loService.saveLocationDistance("03-0002-000D", "04-0002-0002", 710);
		
		lo2loService.saveLocationDistance("03-0002-000D", "04-0003-0001", 2400, 890);
		
		lo2loService.saveLocationDistance("03-0002-000D", "04-0004-0001", 3850, 720);
		lo2loService.saveLocationDistance("03-0002-000D", "04-0004-0002", 2400, 990);
		
		lo2loService.saveLocationDistance("03-0002-000D", "05-0002-0001", 800, 80);
		
		lo2loService.saveLocationDistance("03-0002-000D", "06-0002-0001", 2950, 180);
		
		
		
		lo2loService.saveLocationDistance("03-0003-000A", "04-0002-0001", 630);
		lo2loService.saveLocationDistance("03-0003-000B", "04-0002-0001", 750);
		
		lo2loService.saveLocationDistance("03-0003-000A", "04-0003-0001", 770);
		lo2loService.saveLocationDistance("03-0003-000B", "04-0003-0001", 890);
		
		lo2loService.saveLocationDistance("03-0003-000A", "04-0004-0002", 1100);
		lo2loService.saveLocationDistance("03-0003-000B", "04-0004-0002", 1220);
		
		
		lo2loService.saveLocationDistance("03-0003-000C", "05-0001-0001",580);
		lo2loService.saveLocationDistance("03-0003-000C", "05-0001-0002",480);
		lo2loService.saveLocationDistance("03-0003-000C", "05-0001-0003",180);
		lo2loService.saveLocationDistance("03-0003-000C", "05-0001-0004",80);
		
		lo2loService.saveLocationDistance("03-0003-000C", "04-0001-0001", 3000, 700);
		
		lo2loService.saveLocationDistance("03-0003-000C", "01-0001-0001", 1350, 370);
		
		lo2loService.saveLocationDistance("03-0003-000C", "01-0002-0001", 3850, 360);
		
		lo2loService.saveLocationDistance("03-0003-000C", "04-0002-0001", 2400, 1270);
		lo2loService.saveLocationDistance("03-0003-000C", "04-0002-0002", 165);
		
		lo2loService.saveLocationDistance("03-0003-000C", "04-0003-0001", 2400, 1150);
		
		lo2loService.saveLocationDistance("03-0003-000C", "04-0004-0001", 3850, 385);
		lo2loService.saveLocationDistance("03-0003-000C", "04-0004-0002", 2400, 775);
		
		lo2loService.saveLocationDistance("03-0003-000C", "05-0002-0001", 800, 360);
		
		lo2loService.saveLocationDistance("03-0003-000C", "06-0002-0001", 2950, 360);
		
		
		
		lo2loService.saveLocationDistance("03-0003-000D", "05-0001-0001",550);
		lo2loService.saveLocationDistance("03-0003-000D", "05-0001-0002",450);
		lo2loService.saveLocationDistance("03-0003-000D", "05-0001-0003",150);
		lo2loService.saveLocationDistance("03-0003-000D", "05-0001-0004",50);
		
		lo2loService.saveLocationDistance("03-0003-000D", "04-0001-0001", 3000, 750);
		
		lo2loService.saveLocationDistance("03-0003-000D", "01-0001-0001", 1350, 450);
		
		lo2loService.saveLocationDistance("03-0003-000D", "01-0002-0001", 3850, 440);
		
		lo2loService.saveLocationDistance("03-0003-000D", "04-0002-0001", 2400, 1350);
		lo2loService.saveLocationDistance("03-0003-000D", "04-0002-0002", 245);
		
		lo2loService.saveLocationDistance("03-0003-000D", "04-0003-0001", 2400, 1230);
		
		lo2loService.saveLocationDistance("03-0003-000D", "04-0004-0001", 3850, 465);
		lo2loService.saveLocationDistance("03-0003-000D", "04-0004-0002", 2400, 855);
		
		lo2loService.saveLocationDistance("03-0003-000D", "05-0002-0001", 800, 440);
		
		lo2loService.saveLocationDistance("03-0003-000D", "06-0002-0001", 2950, 440);
		
		
		lo2loService.saveLocationDistance("03-0003-000E", "05-0001-0001",580);
		lo2loService.saveLocationDistance("03-0003-000E", "05-0001-0002",480);
		lo2loService.saveLocationDistance("03-0003-000E", "05-0001-0003",180);
		lo2loService.saveLocationDistance("03-0003-000E", "05-0001-0004",80);
		
		lo2loService.saveLocationDistance("03-0003-000E", "04-0001-0001", 3000, 850);
		
		lo2loService.saveLocationDistance("03-0003-000E", "01-0001-0001", 1350, 580);
		
		lo2loService.saveLocationDistance("03-0003-000E", "01-0002-0001", 3850, 570);
		
		lo2loService.saveLocationDistance("03-0003-000E", "04-0002-0001", 2400, 1480);
		lo2loService.saveLocationDistance("03-0003-000E", "04-0002-0002", 375);
		
		lo2loService.saveLocationDistance("03-0003-000E", "04-0003-0001", 2400, 1360);
		
		lo2loService.saveLocationDistance("03-0003-000E", "04-0004-0001", 3850, 595);
		lo2loService.saveLocationDistance("03-0003-000E", "04-0004-0002", 2400, 985);
		
		lo2loService.saveLocationDistance("03-0003-000E", "05-0002-0001", 800, 570);
		
		lo2loService.saveLocationDistance("03-0003-000E", "06-0002-0001", 2950, 570);
		
		
		
		lo2loService.saveLocationDistance("03-0003-000F", "05-0001-0001",550);
		lo2loService.saveLocationDistance("03-0003-000F", "05-0001-0002",450);
		lo2loService.saveLocationDistance("03-0003-000F", "05-0001-0003",150);
		lo2loService.saveLocationDistance("03-0003-000F", "05-0001-0004",50);
		
		lo2loService.saveLocationDistance("03-0003-000F", "04-0001-0001", 3000, 950);
		
		lo2loService.saveLocationDistance("03-0003-000F", "01-0001-0001", 1350, 695);
		
		lo2loService.saveLocationDistance("03-0003-000F", "01-0002-0001", 3850, 685);
		
		lo2loService.saveLocationDistance("03-0003-000F", "04-0002-0001", 2400, 1595);
		lo2loService.saveLocationDistance("03-0003-000F", "04-0002-0002", 490);
		
		lo2loService.saveLocationDistance("03-0003-000F", "04-0003-0001", 2400, 1475);
		
		lo2loService.saveLocationDistance("03-0003-000F", "04-0004-0001", 3850, 710);
		lo2loService.saveLocationDistance("03-0003-000F", "04-0004-0002", 2400, 110);
		
		lo2loService.saveLocationDistance("03-0003-000F", "05-0002-0001", 800, 685);
		
		lo2loService.saveLocationDistance("03-0003-000F", "06-0002-0001", 2950, 685);
		
		
		
		
		lo2loService.saveLocationDistance("03-0004-000B", "04-0002-0001", 350);
		
		lo2loService.saveLocationDistance("03-0004-000B", "04-0003-0001", 500);
		
		lo2loService.saveLocationDistance("03-0004-000B", "04-0004-0002", 800);
		
		lo2loService.saveLocationDistance("03-0004-000C", "05-0001-0001",650);
		lo2loService.saveLocationDistance("03-0004-000C", "05-0001-0002",550);
		lo2loService.saveLocationDistance("03-0004-000C", "05-0001-0003",250);
		lo2loService.saveLocationDistance("03-0004-000C", "05-0001-0004",150);
		
		lo2loService.saveLocationDistance("03-0004-000C", "04-0001-0001", 3000, 900);
		
		lo2loService.saveLocationDistance("03-0004-000C", "01-0001-0001", 1350, 570);
		
		lo2loService.saveLocationDistance("03-0004-000C", "01-0002-0001", 3850, 575);
		
		lo2loService.saveLocationDistance("03-0004-000C", "04-0002-0001", 600);
		lo2loService.saveLocationDistance("03-0004-000C", "04-0002-0002", 420);
		
		lo2loService.saveLocationDistance("03-0004-000C", "04-0003-0001", 660);
		
		lo2loService.saveLocationDistance("03-0004-000C", "04-0004-0001", 3850, 580);
		lo2loService.saveLocationDistance("03-0004-000C", "04-0004-0002", 2400, 795);
		
		lo2loService.saveLocationDistance("03-0004-000C", "05-0002-0001", 800, 560);
		
		lo2loService.saveLocationDistance("03-0004-000C", "06-0002-0001", 2950, 570);
		
		
		
		lo2loService.saveLocationDistance("03-0004-000D", "05-0001-0001",700);
		lo2loService.saveLocationDistance("03-0004-000D", "05-0001-0002",600);
		lo2loService.saveLocationDistance("03-0004-000D", "05-0001-0003",400);
		lo2loService.saveLocationDistance("03-0004-000D", "05-0001-0004",300);
		
		lo2loService.saveLocationDistance("03-0004-000D", "04-0001-0001", 3000, 1000);
		
		lo2loService.saveLocationDistance("03-0004-000D", "01-0001-0001", 1350, 670);
		
		lo2loService.saveLocationDistance("03-0004-000D", "01-0002-0001", 3850, 675);
		
		lo2loService.saveLocationDistance("03-0004-000D", "04-0002-0001", 700);
		lo2loService.saveLocationDistance("03-0004-000D", "04-0002-0002", 520);
		
		lo2loService.saveLocationDistance("03-0004-000C", "04-0003-0001", 760);
		
		lo2loService.saveLocationDistance("03-0004-000D", "04-0004-0001", 3850, 680);
		lo2loService.saveLocationDistance("03-0004-000D", "04-0004-0002", 2400, 895);
		
		lo2loService.saveLocationDistance("03-0004-000D", "05-0002-0001", 800, 660);
		
		lo2loService.saveLocationDistance("03-0004-000D", "06-0002-0001", 2950, 670);
		
		
		
		//接驳车站到购物村(其他景点)
		lo2loService.saveLocationDistance("02-0001-0001", "05-0001-0001",320);
		lo2loService.saveLocationDistance("02-0001-0001", "05-0001-0002",170);
		lo2loService.saveLocationDistance("02-0001-0001", "05-0001-0003",360);
		lo2loService.saveLocationDistance("02-0001-0001", "05-0001-0004",630);
		
		lo2loService.saveLocationDistance("02-0001-0001", "04-0001-0001",3000,500);
		
		lo2loService.saveLocationDistance("02-0001-0001", "01-0001-0001", 1350,165);
		
		lo2loService.saveLocationDistance("02-0001-0001", "01-0002-0001", 3850, 126);
		
		lo2loService.saveLocationDistance("02-0001-0001", "04-0002-0001", 2400, 1130);
		lo2loService.saveLocationDistance("02-0001-0001", "04-0002-0002", 50);
		
		lo2loService.saveLocationDistance("02-0001-0001", "04-0003-0001", 2400, 960);
		
		lo2loService.saveLocationDistance("02-0001-0001", "04-0004-0001", 2950, 150);
		lo2loService.saveLocationDistance("02-0001-0001", "04-0004-0002", 2400, 605);
		
		lo2loService.saveLocationDistance("02-0001-0001", "05-0002-0001", 800, 140);
		
		lo2loService.saveLocationDistance("02-0001-0001", "06-0002-0001", 2950, 145);
		
		
		lo2loService.saveLocationDistance("02-0001-0002", "05-0001-0001",1000,100);
		lo2loService.saveLocationDistance("02-0001-0002", "05-0001-0002",1000,100);
		lo2loService.saveLocationDistance("02-0001-0002", "05-0001-0003",1000,250);
		lo2loService.saveLocationDistance("02-0001-0002", "05-0001-0004",1000,360);
		
		lo2loService.saveLocationDistance("02-0001-0002", "04-0001-0001",2100,500);
		
		lo2loService.saveLocationDistance("02-0001-0002", "01-0001-0001", 550,165);
		
		lo2loService.saveLocationDistance("02-0001-0002", "01-0002-0001", 3300, 126);
		
		lo2loService.saveLocationDistance("02-0001-0002", "04-0002-0001", 3400, 800);
		lo2loService.saveLocationDistance("02-0001-0002", "04-0002-0002", 1000, 290);
		
		lo2loService.saveLocationDistance("02-0001-0002", "04-0003-0001", 3400, 630);
		
		lo2loService.saveLocationDistance("02-0001-0002", "04-0004-0001", 2150, 165);
		lo2loService.saveLocationDistance("02-0001-0002", "04-0004-0002", 3400, 280);
		
		lo2loService.saveLocationDistance("02-0001-0002", "05-0002-0001", 140);
		
		lo2loService.saveLocationDistance("02-0001-0002", "06-0002-0001", 2150, 145);
		
		
		lo2loService.saveLocationDistance("02-0001-0003", "05-0001-0001",1800,300);
		lo2loService.saveLocationDistance("02-0001-0003", "05-0001-0002",1800,300);
		lo2loService.saveLocationDistance("02-0001-0003", "05-0001-0003",1800,450);
		lo2loService.saveLocationDistance("02-0001-0003", "05-0001-0004",1800,560);
		
		lo2loService.saveLocationDistance("02-0001-0003", "04-0001-0001",1600,500);
		
		lo2loService.saveLocationDistance("02-0001-0003", "01-0001-0001", 150);
		
		lo2loService.saveLocationDistance("02-0001-0003", "01-0002-0001", 2100, 140);
		
		lo2loService.saveLocationDistance("02-0001-0003", "04-0002-0001", 4200, 880);
		lo2loService.saveLocationDistance("02-0001-0003", "04-0002-0002", 1800, 440);
		
		lo2loService.saveLocationDistance("02-0001-0003", "04-0003-0001", 4200, 720);
		
		lo2loService.saveLocationDistance("02-0001-0003", "04-0004-0001", 1600, 165);
		lo2loService.saveLocationDistance("02-0001-0003", "04-0004-0002", 4200, 380);
		
		lo2loService.saveLocationDistance("02-0001-0003", "05-0002-0001", 800, 250);
		
		lo2loService.saveLocationDistance("02-0001-0003", "06-0002-0001", 1600, 145);
		
		
		lo2loService.saveLocationDistance("02-0001-0004", "05-0001-0001",3700,100);
		lo2loService.saveLocationDistance("02-0001-0004", "05-0001-0002",3700,100);
		lo2loService.saveLocationDistance("02-0001-0004", "05-0001-0003",3700,250);
		lo2loService.saveLocationDistance("02-0001-0004", "05-0001-0004",3700,360);
		
		lo2loService.saveLocationDistance("02-0001-0004", "04-0001-0001",500);
		
		lo2loService.saveLocationDistance("02-0001-0004", "01-0001-0001", 1900, 265);
		
		lo2loService.saveLocationDistance("02-0001-0004", "01-0002-0001", 900, 140);
		
		lo2loService.saveLocationDistance("02-0001-0004", "04-0002-0001", 6100, 580);
		lo2loService.saveLocationDistance("02-0001-0004", "04-0002-0002", 3700, 385);
		
		lo2loService.saveLocationDistance("02-0001-0004", "04-0003-0001", 6100, 580);
		
		lo2loService.saveLocationDistance("02-0001-0004", "04-0004-0001", 165);
		lo2loService.saveLocationDistance("02-0001-0004", "04-0004-0002", 930);
		
		lo2loService.saveLocationDistance("02-0001-0004", "05-0002-0001", 2700, 150);
		
		lo2loService.saveLocationDistance("02-0001-0004", "06-0002-0001", 145);
		
		
		
		lo2loService.saveLocationDistance("02-0001-0005", "05-0001-0001",3400,120);
		lo2loService.saveLocationDistance("02-0001-0005", "05-0001-0002",3400,120);
		lo2loService.saveLocationDistance("02-0001-0005", "05-0001-0003",3400,270);
		lo2loService.saveLocationDistance("02-0001-0005", "05-0001-0004",3400,380);
		
		lo2loService.saveLocationDistance("02-0001-0005", "04-0001-0001",2700,820);
		
		lo2loService.saveLocationDistance("02-0001-0005", "01-0001-0001", 1600, 265);
		
		lo2loService.saveLocationDistance("02-0001-0005", "01-0002-0001", 140);
		
		lo2loService.saveLocationDistance("02-0001-0005", "04-0002-0001", 2700, 580);
		lo2loService.saveLocationDistance("02-0001-0005", "04-0002-0002", 3400, 400);
		
		lo2loService.saveLocationDistance("02-0001-0005", "04-0003-0001", 2700, 580);
		
		lo2loService.saveLocationDistance("02-0001-0005", "04-0004-0001", 900);
		lo2loService.saveLocationDistance("02-0001-0005", "04-0004-0002", 2700, 280);
		
		lo2loService.saveLocationDistance("02-0001-0005", "05-0002-0001", 3400, 180);
		
		lo2loService.saveLocationDistance("02-0001-0005", "06-0002-0001", 885);
		
		
		
		lo2loService.saveLocationDistance("02-0001-0006", "05-0001-0001",2800,180);
		lo2loService.saveLocationDistance("02-0001-0006", "05-0001-0002",2800,440);
		lo2loService.saveLocationDistance("02-0001-0006", "05-0001-0003",2800,350);
		lo2loService.saveLocationDistance("02-0001-0006", "05-0001-0004",2800,610);
		
		lo2loService.saveLocationDistance("02-0001-0006", "04-0001-0001",1300,800);
		
		lo2loService.saveLocationDistance("02-0001-0006", "01-0001-0001", 3700, 395);
		
		lo2loService.saveLocationDistance("02-0001-0006", "01-0002-0001", 1300, 250);
		
		lo2loService.saveLocationDistance("02-0001-0006", "04-0002-0001", 1300, 780);
		lo2loService.saveLocationDistance("02-0001-0006", "04-0002-0002", 2800, 340);
		
		lo2loService.saveLocationDistance("02-0001-0006", "04-0003-0001", 1300, 580);
		
		lo2loService.saveLocationDistance("02-0001-0006", "04-0004-0001", 1300, 1020);
		lo2loService.saveLocationDistance("02-0001-0006", "04-0004-0002", 1300, 280);
		
		lo2loService.saveLocationDistance("02-0001-0006", "05-0002-0001", 3700, 280);
		
		lo2loService.saveLocationDistance("02-0001-0006", "06-0002-0001", 1300, 1070);
		
		
		
		lo2loService.saveLocationDistance("02-0001-0007", "05-0001-0001",1500,180);
		lo2loService.saveLocationDistance("02-0001-0007", "05-0001-0002",1500,440);
		lo2loService.saveLocationDistance("02-0001-0007", "05-0001-0003",1500,350);
		lo2loService.saveLocationDistance("02-0001-0007", "05-0001-0004",1500,610);
		
		lo2loService.saveLocationDistance("02-0001-0007", "04-0001-0001",800);
		
		lo2loService.saveLocationDistance("02-0001-0007", "01-0001-0001", 2850, 120);
		
		lo2loService.saveLocationDistance("02-0001-0007", "01-0002-0001", 2700, 110);
		
		lo2loService.saveLocationDistance("02-0001-0007", "04-0002-0001", 780);
		lo2loService.saveLocationDistance("02-0001-0007", "04-0002-0002", 1500, 50);
		
		lo2loService.saveLocationDistance("02-0001-0007", "04-0003-0001", 580);
		
		lo2loService.saveLocationDistance("02-0001-0007", "04-0004-0001", 900 );
		lo2loService.saveLocationDistance("02-0001-0007", "04-0004-0002", 280);
		
		lo2loService.saveLocationDistance("02-0001-0007", "05-0002-0001", 2300, 130);
		
		lo2loService.saveLocationDistance("02-0001-0007", "06-0002-0001", 880 );
		
		
		
		lo2loService.saveLocationDistance("02-0001-0008", "05-0001-0001",100);
		lo2loService.saveLocationDistance("02-0001-0008", "05-0001-0002",100);
		lo2loService.saveLocationDistance("02-0001-0008", "05-0001-0003",250);
		lo2loService.saveLocationDistance("02-0001-0008", "05-0001-0004",360);
		
		lo2loService.saveLocationDistance("02-0001-0008", "04-0001-0001",2400,800);
		
		lo2loService.saveLocationDistance("02-0001-0008", "01-0001-0001", 150);
		
		lo2loService.saveLocationDistance("02-0001-0008", "01-0002-0001", 2100, 140);
		
		lo2loService.saveLocationDistance("02-0001-0008", "04-0002-0001", 4200, 880);
		lo2loService.saveLocationDistance("02-0001-0008", "04-0002-0002", 1800, 440);
		
		lo2loService.saveLocationDistance("02-0001-0008", "04-0003-0001", 4200, 720);
		
		lo2loService.saveLocationDistance("02-0001-0008", "04-0004-0001", 1600, 165);
		lo2loService.saveLocationDistance("02-0001-0008", "04-0004-0002", 4200, 380);
		
		lo2loService.saveLocationDistance("02-0001-0008", "05-0002-0001", 800, 250);
		
		lo2loService.saveLocationDistance("02-0001-0008", "06-0002-0001", 1600, 145);
		
		
		
		
		lo2loService.saveLocationDistance("02-0001-0009", "05-0001-0001",2800,200);
		lo2loService.saveLocationDistance("02-0001-0009", "05-0001-0002",2800,460);
		lo2loService.saveLocationDistance("02-0001-0009", "05-0001-0003",2800,370);
		lo2loService.saveLocationDistance("02-0001-0009", "05-0001-0004",2800,630);
		
		lo2loService.saveLocationDistance("02-0001-0009", "04-0001-0001",1300,820);
		
		lo2loService.saveLocationDistance("02-0001-0009", "01-0001-0001", 150);
		
		lo2loService.saveLocationDistance("02-0001-0009", "01-0002-0001", 1300, 140);
		
		lo2loService.saveLocationDistance("02-0001-0009", "04-0002-0001", 1300, 870);
		lo2loService.saveLocationDistance("02-0001-0009", "04-0002-0002", 2800, 430);
		
		lo2loService.saveLocationDistance("02-0001-0009", "04-0003-0001", 1300, 770);
		
		lo2loService.saveLocationDistance("02-0001-0009", "04-0004-0001", 920);
		lo2loService.saveLocationDistance("02-0001-0009", "04-0004-0002", 1300, 470);
		
		lo2loService.saveLocationDistance("02-0001-0009", "05-0002-0001", 3600, 300);
		
		lo2loService.saveLocationDistance("02-0001-0009", "06-0002-0001", 1300, 950);
		
		
		
		
		lo2loService.saveLocationDistance("02-0001-0010", "05-0001-0001",3400,140);
		lo2loService.saveLocationDistance("02-0001-0010", "05-0001-0002",3400,140);
		lo2loService.saveLocationDistance("02-0001-0010", "05-0001-0003",3400,290);
		lo2loService.saveLocationDistance("02-0001-0010", "05-0001-0004",3400,400);
		
		lo2loService.saveLocationDistance("02-0001-0010", "04-0001-0001",2700,800);
		
		lo2loService.saveLocationDistance("02-0001-0010", "01-0001-0001", 1600, 350);
		
		lo2loService.saveLocationDistance("02-0001-0010", "01-0002-0001", 140);
		
		lo2loService.saveLocationDistance("02-0001-0010", "04-0002-0001", 2700, 720);
		lo2loService.saveLocationDistance("02-0001-0010", "04-0002-0002", 3500, 440);
		
		lo2loService.saveLocationDistance("02-0001-0010", "04-0003-0001", 2700, 600);
		
		lo2loService.saveLocationDistance("02-0001-0010", "04-0004-0001", 900);
		lo2loService.saveLocationDistance("02-0001-0010", "04-0004-0002", 2700, 360);
		
		lo2loService.saveLocationDistance("02-0001-0010", "05-0002-0001", 2400, 220);
		
		lo2loService.saveLocationDistance("02-0001-0010", "06-0002-0001", 900);
		
		
		
		lo2loService.saveLocationDistance("02-0001-0011", "05-0001-0001",1800,100);
		lo2loService.saveLocationDistance("02-0001-0011", "05-0001-0002",1800,100);
		lo2loService.saveLocationDistance("02-0001-0011", "05-0001-0003",1800,250);
		lo2loService.saveLocationDistance("02-0001-0011", "05-0001-0004",1800,360);
		
		lo2loService.saveLocationDistance("02-0001-0011", "04-0001-0001",1600,700);
		
		lo2loService.saveLocationDistance("02-0001-0011", "01-0001-0001", 350);
		
		lo2loService.saveLocationDistance("02-0001-0011", "01-0002-0001",3050, 350);
		
		lo2loService.saveLocationDistance("02-0001-0011", "04-0002-0001", 4200, 600);
		lo2loService.saveLocationDistance("02-0001-0011", "04-0002-0002", 1800, 400);
		
		lo2loService.saveLocationDistance("02-0001-0011", "04-0003-0001", 4200, 680);
		
		lo2loService.saveLocationDistance("02-0001-0011", "04-0004-0001", 1600, 300);
		lo2loService.saveLocationDistance("02-0001-0011", "04-0004-0002", 4200, 380);
		
		lo2loService.saveLocationDistance("02-0001-0011", "05-0002-0001", 800, 250);
		
		lo2loService.saveLocationDistance("02-0001-0011", "06-0002-0001", 1600, 310);
		
		
		
		lo2loService.saveLocationDistance("02-0001-0012", "05-0001-0001",1000,100);
		lo2loService.saveLocationDistance("02-0001-0012", "05-0001-0002",1000,100);
		lo2loService.saveLocationDistance("02-0001-0012", "05-0001-0003",1000,250);
		lo2loService.saveLocationDistance("02-0001-0012", "05-0001-0004",1000,360);
		
		lo2loService.saveLocationDistance("02-0001-0012", "04-0001-0001",2100,520);
		
		lo2loService.saveLocationDistance("02-0001-0012", "01-0001-0001", 550, 190);
		
		lo2loService.saveLocationDistance("02-0001-0012", "01-0002-0001", 2500, 170);
		
		lo2loService.saveLocationDistance("02-0001-0012", "04-0002-0001", 2300, 580);
		lo2loService.saveLocationDistance("02-0001-0012", "04-0002-0002", 1000, 400);
		
		lo2loService.saveLocationDistance("02-0001-0012", "04-0003-0001", 2300, 680);
		
		lo2loService.saveLocationDistance("02-0001-0012", "04-0004-0001", 2150, 165);
		lo2loService.saveLocationDistance("02-0001-0012", "04-0004-0002", 2300, 380);
		
		lo2loService.saveLocationDistance("02-0001-0012", "05-0002-0001", 230);
		
		lo2loService.saveLocationDistance("02-0001-0012", "06-0002-0001", 2150, 170);
		
		
		
		
		
		//停车场到停车场
		lo2loService.saveLocationDistance("03-0002-000C", "03-0001-000C",1350,760);
		lo2loService.saveLocationDistance("03-0002-000D", "03-0001-000C",1350,665);
		lo2loService.saveLocationDistance("03-0003-000C", "03-0001-000C",1350,340);
		lo2loService.saveLocationDistance("03-0003-000D", "03-0001-000C",1350,440);
		lo2loService.saveLocationDistance("03-0003-000E", "03-0001-000C",1350,590);
		lo2loService.saveLocationDistance("03-0003-000F", "03-0001-000C",1350,700);
		lo2loService.saveLocationDistance("03-0004-000C", "03-0001-000C",1350,780);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0001-000C",1350,930);
		
		lo2loService.saveLocationDistance("03-0001-000C", "03-0002-000C",1800,340);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0002-000D",1800,240);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0003-000C",1800,530);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0003-000D",1800,590);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0003-000E",1800,740);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0003-000F",1800,860);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0004-000C",1800,780);
		lo2loService.saveLocationDistance("03-0001-000C", "03-0004-000D",1800,930);
		
		lo2loService.saveLocationDistance("03-0003-000C", "03-0002-000A",750);
		lo2loService.saveLocationDistance("03-0003-000D", "03-0002-000A",810);
		lo2loService.saveLocationDistance("03-0003-000E", "03-0002-000A",1000);
		lo2loService.saveLocationDistance("03-0003-000F", "03-0002-000A",1100);
		
		lo2loService.saveLocationDistance("03-0003-000C", "03-0002-000C",840);
		lo2loService.saveLocationDistance("03-0003-000D", "03-0002-000C",900);
		lo2loService.saveLocationDistance("03-0003-000E", "03-0002-000C",1100);
		lo2loService.saveLocationDistance("03-0003-000F", "03-0002-000C",1200);
		
		lo2loService.saveLocationDistance("03-0003-000C", "03-0002-000D",740);
		lo2loService.saveLocationDistance("03-0003-000D", "03-0002-000D",800);
		lo2loService.saveLocationDistance("03-0003-000E", "03-0002-000D",1000);
		lo2loService.saveLocationDistance("03-0003-000F", "03-0002-000D",1100);
		
		lo2loService.saveLocationDistance("03-0004-000C", "03-0002-000A",1050);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0002-000A",1150);
		
		lo2loService.saveLocationDistance("03-0004-000C", "03-0002-000C",1100);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0002-000C",1200);
		
		lo2loService.saveLocationDistance("03-0004-000C", "03-0002-000D",1000);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0002-000D",1100);
		
		lo2loService.saveLocationDistance("03-0004-000C", "03-0003-000A",30);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0003-000A",150);
		lo2loService.saveLocationDistance("03-0004-000C", "03-0003-000B",130);
		lo2loService.saveLocationDistance("03-0004-000D", "03-0003-000B",30);
		
		//接驳车站到停车场
		lo2loService.saveLocationDistance("02-0001-0001", "03-0001-000C",1350,170);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0001-000C",550,170);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0001-000C",170);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0001-000C",1900,40);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0001-000C",1600,60);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0001-000C",2900,160);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0001-000C",2850,170);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0001-000C",1350,510);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0001-000C",2900,40);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0001-000C",1600,40);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0001-000C",40);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0001-000C",550,220);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0002-000C",640);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0002-000C",1000,370);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0002-000C",1800,445);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0002-000C",3700,320);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0002-000C",3400,350);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0002-000C",2800,640);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0002-000C",1500,640);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0002-000C",320);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0002-000C",2800,760);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0002-000C",3400,320);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0002-000C",1800,320);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0002-000C",1000,320);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0002-000D",540);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0002-000D",1000,270);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0002-000D",1800,345);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0002-000D",3700,220);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0002-000D",3400,250);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0002-000D",2800,540);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0002-000D",1500,540);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0002-000D",220);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0002-000D",2800,660);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0002-000D",3400,320);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0002-000D",1800,220);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0002-000D",1000,220);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0003-000C",220);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0003-000C",1000,525);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0003-000C",1800,605);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0003-000C",3700,510);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0003-000C",3400,530);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0003-000C",2800,220);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0003-000C",1500,220);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0003-000C",510);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0003-000C",2800,440);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0003-000C",3400,510);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0003-000C",1800,510);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0003-000C",1000,510);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0003-000D",295);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0003-000D",1000,525);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0003-000D",1800,605);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0003-000D",3700,440);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0003-000D",3400,460);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0003-000D",2800,295);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0003-000D",1500,295);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0003-000D",440);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0003-000D",2800,515);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0003-000D",3400,440);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0003-000D",1800,440);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0003-000D",1000,440);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0003-000E",455);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0003-000E",1000,540);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0003-000E",1800,625);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0003-000E",3700,460);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0003-000E",3400,480);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0003-000E",2800,455);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0003-000E",1500,455);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0003-000E",460);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0003-000E",2800,565);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0003-000E",3400,460);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0003-000E",1800,460);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0003-000E",1000,460);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0003-000F",560);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0003-000F",1000,410);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0003-000F",1800,525);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0003-000F",3700,360);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0003-000F",3400,380);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0003-000F",2800,560);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0003-000F",1500,560);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0003-000F",360);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0003-000F",2800,670);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0003-000F",3400,360);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0003-000F",1800,360);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0003-000F",1000,360);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0004-000C",450);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0004-000C",1000,765);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0004-000C",1800,875);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0004-000C",3700,715);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0004-000C",3400,735);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0004-000C",2800,450);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0004-000C",1500,450);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0004-000C",715);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0004-000C",2800,560);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0004-000C",3400,715);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0004-000C",1800,715);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0004-000C",1000,715);
		
		lo2loService.saveLocationDistance("02-0001-0001", "03-0004-000D",590);
		lo2loService.saveLocationDistance("02-0001-0002", "03-0004-000D",1000,765);
		lo2loService.saveLocationDistance("02-0001-0003", "03-0004-000D",1800,1015);
		lo2loService.saveLocationDistance("02-0001-0004", "03-0004-000D",3700,855);
		lo2loService.saveLocationDistance("02-0001-0005", "03-0004-000D",3400,875);
		lo2loService.saveLocationDistance("02-0001-0006", "03-0004-000D",2800,590);
		lo2loService.saveLocationDistance("02-0001-0007", "03-0004-000D",1500,590);
		lo2loService.saveLocationDistance("02-0001-0008", "03-0004-000D",855);
		lo2loService.saveLocationDistance("02-0001-0009", "03-0004-000D",2800,750);
		lo2loService.saveLocationDistance("02-0001-0010", "03-0004-000D",3400,855);
		lo2loService.saveLocationDistance("02-0001-0011", "03-0004-000D",1800,855);
		lo2loService.saveLocationDistance("02-0001-0012", "03-0004-000D",1000,855);
		
		return Ajax.buildSuccessResult();
	}
	
	
	@RequestMapping(value="/batchUpdateQrCode",method=RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object> batchUpdateQrCode() throws Exception {
		
		QrCodeVerifyVO vo = null;
		
		for(Map<String,String> map:excelCodeLoader.getAllQrCode()){
			vo = QrCodeVerifyVO.getVO(map);
			
			if(vo!=null){
				QrCode qc = locationService.findQrCodeByCode(vo.getCode());
				
				if(qc!=null){
					
					qc.setRegion(vo.getRegion());
					qc.setSize(vo.getSize());
					qc.setStyle(vo.getStyle());
					qc.setCodeRange(vo.getRange());
					
					if( isNearEntrance(qc.getQrCode()) ){
						qc.setNearEntra(true);
					}
					
					locationService.updateQrCode(qc);
				}
			}
		}
		
		return Ajax.buildSuccessResult();
    	
	}
	
	
	private boolean isNearEntrance(String code){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("03-0001-0016", "");
		map.put("03-0001-0028", "");
		map.put("03-0001-0032", "");
		
		map.put("03-0002-0086", "");
		map.put("03-0002-0087", "");
		map.put("03-0002-0092", "");
		map.put("03-0002-0112", "");
		
		map.put("03-0003-0001", "");
		map.put("03-0003-0019", "");
		map.put("03-0003-0058", "");
		map.put("03-0003-0059", "");
		map.put("03-0003-0063", "");
		
		map.put("03-0004-0068", "");
		map.put("03-0004-0070", "");
		map.put("03-0004-0071", "");
		map.put("03-0004-0074", "");
		
		
		return map.containsKey(code);
		
	}
	

	@RequestMapping(value="/generateParentLocation",method=RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  generateParentLocation() throws IOException {
		
		createParentLoc();
    	return Ajax.buildSuccessResult();
    	
	}
	
	@RequestMapping(value="/generateQrCode",method=RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  generateQrCode() throws IOException {
		
		createQrCodeData();
		return Ajax.buildSuccessResult();
    	
	}
	
    private Location getLocation(String name,String code,Integer type){
    	Location l = new Location();
    	
    	l.setLocation(code);
    	l.setLocationImg("/resources/img/location/"+code+".jpg");
    	l.setQrCodeLocation(false);
    	l.setType(type);
    	l.setName(name);
    	
    	return l;
    }
    
	private void createParentLoc(){
		//01 住：迪士尼乐园酒店、玩具总动员酒店
    	locationService.save(getLocation("上海迪士尼乐园酒店","01-0001",1));
    	locationService.save(getLocation("玩具总动员酒店","01-0002",1));
    	
    	
    	//02 行：接驳站点、MINI PTH、东PTC、西PTC、西PTH、南PTH
    	locationService.save(getLocation("接驳车站","02-0001",2));
    	locationService.save(getLocation("MINI PTH","02-0002",2));
    	locationService.save(getLocation("东公交枢纽","02-0003",2));
    	locationService.save(getLocation("西公交枢纽","02-0004",2));
    	locationService.save(getLocation("南公交枢纽","02-0005",2));
    	locationService.save(getLocation("北公交枢纽","02-0006",2));
    	
    	//03 停：P1-4停车场、P5-6停车场、迪士尼停车场、管理中心地下停车场
    	locationService.save(getLocation("P1停车场","03-0001",3));
    	locationService.save(getLocation("P2停车场","03-0002",3));
    	locationService.save(getLocation("P3停车场","03-0003",3));
    	locationService.save(getLocation("P4停车场","03-0004",3));
    	locationService.save(getLocation("P5停车场","03-0005",3));
    	locationService.save(getLocation("P6停车场","03-0006",3));
    	locationService.save(getLocation("迪士尼停车场","03-0007",3));
    	locationService.save(getLocation("管理中心停车场","03-0008",3));
    	
    	//04 娱：奇想花园、香草园、生态园、星愿湖公园、景观桥
    	locationService.save(getLocation("迪士尼乐园","04-0001",4));
    	locationService.save(getLocation("香草园","04-0002",4));
    	locationService.save(getLocation("生态园","04-0003",4));
    	locationService.save(getLocation("星愿湖公园","04-0004",4));
    	
    	locationService.save(getLocation("景观桥",QrCodeFix.LANDSCAPE_BRIDGE.substring(0, 7),4));
    	
    	locationService.save(getLocation("香草小街","04-0006",4));
    	locationService.save(getLocation("绿地生态公园二期","04-0007",4));
    	
    	
    	//05购：奕欧来上海购物村
    	locationService.save(getLocation("奕欧来上海购物村","05-0001",5));
    	locationService.save(getLocation("Maxus大通广场","05-0002",5));
    	
    	//06商：申迪中心、迪士尼小镇
    	locationService.save(getLocation("申迪中心","06-0001",6));
    	locationService.save(getLocation("迪士尼小镇","06-0002",6));
	}
    
  	private void createQrCodeData(){
      	
      	//01-0001 迪士尼乐园酒店
      	locationService.addQrCode("01-0001-0001", "迪士尼乐园酒店入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("01-0001-0002", "迪士尼乐园酒店入口B",QrCodeType.VIEW_ENTRANCE);
      	
      	//01-0002 玩具总动员酒店
      	locationService.addQrCode("01-0002-0001", "玩具总动员酒店入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//02-0001 接泊车站点
      	locationService.addQrCode("02-0001-0001", "购物村站(1/2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0002", "申迪南路站(1/2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0003", "迪士尼乐园酒店站(1/2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0004", "西公交枢纽站(1/2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0005", "玩具总动员酒店站(2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0006", "申迪北路站(2路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0007", "地铁站(1/2路/3路)",QrCodeType.BUS_STATION);
      	
      	
      	locationService.addQrCode("02-0001-0008", "购物村站(1/3路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0009", "申迪北路站(3路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0010", "玩具总动员酒店站(3路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0011", "南公交枢纽站(1/3路)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0012", "申迪南路站(1/3路)",QrCodeType.BUS_STATION);
      	
      	//02-0004 西公交枢纽
      	locationService.addQrCode("02-0004-0001", "西公交枢纽出入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	//02-0005 南公交枢纽
      	locationService.addQrCode("02-0005-0001", "南公交枢纽出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("02-0005-0002", "南公交枢纽出入口B",QrCodeType.VIEW_ENTRANCE);
      	

      	//03-0001 P1停车场 出入口
      	locationService.addQrCode("03-0001-000A", "P1停车场出入口A",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0001-000B", "P1停车场出入口B",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0001-000C", "P1停车场出入口C",QrCodeType.PARK_ENTRANCE);
      	
      	//03-0002 P2停车场
      	locationService.addQrCode("03-0002-000A", "P2停车场出入口A",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0002-000B", "P2停车场出入口B",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0002-000C", "P2停车场出入口C",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0002-000D", "P2停车场出入口D",QrCodeType.PARK_ENTRANCE);
      	
      	//03-0003 P3停车场
      	locationService.addQrCode("03-0003-000A", "P3停车场出入口A",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0003-000B", "P3停车场出入口B",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0003-000C", "P3停车场出入口C",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0003-000D", "P3停车场出入口D",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0003-000E", "P3停车场出入口E",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0003-000F", "P3停车场出入口F",QrCodeType.PARK_ENTRANCE);
      	
      	//03-0004 P4停车场
      	locationService.addQrCode("03-0004-000A", "P4停车场出入口A",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0004-000B", "P4停车场出入口B",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0004-000C", "P4停车场出入口C",QrCodeType.PARK_ENTRANCE);
      	locationService.addQrCode("03-0004-000D", "P4停车场出入口D",QrCodeType.PARK_ENTRANCE);
      	
      	//04 娱：奇想花园、香草园、生态园、星愿湖公园、景观桥
      	locationService.addQrCode("04-0001-0001", "迪士尼乐园出入口",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0002-0001", "香草园北出入口",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0002-0002", "香草园南出入口",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0003-0001", "生态园出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0004-0001", "星愿湖公园西出入口",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0004-0002", "星愿湖公园东出入口",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	locationService.addQrCode(QrCodeFix.LANDSCAPE_BRIDGE, "景观桥景点二维码",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//05-0001 奕欧来上海购物村
      	locationService.addQrCode("05-0001-0001", "奕欧来上海购物村东南门",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("05-0001-0002", "奕欧来上海购物村西北门",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("05-0001-0003", "奕欧来上海购物村米莱门",QrCodeType.VIEW_ENTRANCE);
    	locationService.addQrCode("05-0001-0004", "奕欧来上海购物村戈什门",QrCodeType.VIEW_ENTRANCE);
    	locationService.addQrCode("05-0002-0001", "Maxus大通广场出入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	//06商：申迪中心、迪士尼小镇
      	locationService.addQrCode("06-0001-0001", "申迪中心出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("06-0001-0002", "申迪中心出入口B",QrCodeType.VIEW_ENTRANCE);
      	
      	locationService.addQrCode("06-0002-0001", "迪士尼小镇出入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//生成停车场内部
      	//03-0001 P1停车场    内部
      	for(int i=0;i<GenerateFix.P1_QRCODE_NUM;i++){
      		locationService.addQrCode("03-0001-"+getNum(i+1), "P1停车场",QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0002 P1停车场    内部
      	for(int i=0;i<GenerateFix.P2_QRCODE_NUM;i++){
      		locationService.addQrCode("03-0002-"+getNum(i+1), "P2停车场",QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0003 P1停车场    内部
      	for(int i=0;i<GenerateFix.P3_QRCODE_NUM;i++){
      		locationService.addQrCode("03-0003-"+getNum(i+1), "P3停车场",QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0004 P1停车场    内部
      	for(int i=0;i<GenerateFix.P4_QRCODE_NUM;i++){
      		locationService.addQrCode("03-0004-"+getNum(i+1), "P4停车场",QrCodeType.PARK_INNER);
      	}
      	
      }
    
    
    private String getNum(int num){
    	String str = "0000"+num;
		return str.substring(str.length()-4, str.length());
    }
    


}
