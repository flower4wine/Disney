package com.disney.web.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.constant.QrCodeType;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.FromToOptimize;
import com.disney.model.Location;
import com.disney.model.QrCode;
import com.disney.service.LocationService;
import com.disney.util.WeChatCreateQrCodeUtil;

@Controller
public class GuideDataGenerateController {

	//private static Logger log = LoggerFactory.getLogger(GuideDataGenerateController.class);
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private WeChatHandler weChatHandler;
	
	@RequestMapping(value="/batchUpdateQrCode",method=RequestMethod.GET)
	@ResponseBody
	public String batchUpdateQrCode() throws Exception {
		
		String accessToken = weChatHandler.accessToken();
		
		for(QrCode code: locationService.allQrCodes()){
			
			String qrUrl = WeChatCreateQrCodeUtil.createQrCode(accessToken, code.getQrCode());
			code.setQrUrl(qrUrl);
			
			locationService.updateQrCode(code);
		}
		
    	return "OK";
    	
	}
	
	
	@RequestMapping(value="/cleanBeforeGenerate",method=RequestMethod.GET)
	@ResponseBody
	public String cleanBeforeGenerate() throws IOException {
		
    	return "OK";
    	
	}
	
	
	@RequestMapping(value="/reset",method=RequestMethod.GET)
	@ResponseBody
	public String reset() throws IOException {
		cleanBeforeGenerate();
		
		generateParentLocation();
		generateQrCode();
		generateFromToData();
		
    	return "OK";
    	
	}

	@RequestMapping(value="/generateParentLocation",method=RequestMethod.GET)
	@ResponseBody
	public String generateParentLocation() throws IOException {
		
		createParentLoc();
    	return "OK";
    	
	}
	
	@RequestMapping(value="/generateQrCode",method=RequestMethod.GET)
	@ResponseBody
	public String generateQrCode() throws IOException {
		
		createQrCodeData();
    	return "OK";
    	
	}
	
	@RequestMapping(value="/generateFromToData",method=RequestMethod.GET)
	@ResponseBody
	public String generateFromToData() throws IOException {
		createFromToData();
    	return "OK";
    	
	}
	
	
	private void createFromToData(){
    	//从外到内 有14条路线
		//P1 03-0001-000C
    	// 05-0001-0001(默认)
    	//P1去购物村 全部从03-0001-000C出入口走  外圈 南公交枢纽 坐2站到购物村站(外圈)
    	addFromTo("03-0001-000C", "05-0001-0001", true, false, "02-0001-0011", "02-0001-0008", 2);
    	addFromTo("05-0001-0001", "03-0001-000C", true, true, "02-0001-0001", "02-0001-0003", 2);
    	
    	
    	
    	//P2 03-0002-000B
    	// 05-0001-0001(默认)
    	//P2去购物村 全部从03-0002-000B 出入口走  到 购物村对应出入口
    	addFromTo("03-0002-000B", "05-0001-0001", false, false, null, null, 0);
    	addFromTo("05-0001-0001", "03-0002-000B", false, false, null, null, 0);
    	
    	
    	//P3  03-0003-000D 03-0003-000E 03-0003-000F
    	// 05-0001-0001(默认) 
    	//P3去购物村  有三种方案
    	//A.   03-0003-000D   1-25车位                                                                 05-0001-0003
    	//B.   03-0003-000E  26-54 61 62 65 66 67	   05-0001-0003
    	//C.   03-0003-000F  55-60 63 64 68 			05-0001-0002
    	 //出入口走  到 购物村对应出入口
    	addFromTo("03-0003-000D", "05-0001-0003", false, false, null, null, 0);
    	addFromTo("03-0003-000E", "05-0001-0003", false, false, null, null, 0);
    	addFromTo("03-0003-000F", "05-0001-0002", false, false, null, null, 0);
    	
    	addFromTo("05-0001-0003", "03-0003-000D", false, false, null, null, 0);
    	addFromTo("05-0001-0003", "03-0003-000E", false, false, null, null, 0);
    	addFromTo("05-0001-0002", "03-0003-000F", false, false, null, null, 0);
    	

    	//P4 03-0004-000C       03-0004-000D
    	//1.  03-0004-000C 05-0001-0003
    	//2.  03-0004-000D 05-0001-0003
    	// 出入口走  到 购物村对应出入口
    	addFromTo("03-0004-000C", "05-0001-0003", false, false, null, null, 0);
    	addFromTo("03-0004-000D", "05-0001-0003", false, false, null, null, 0);
    	
    	addFromTo("05-0001-0003", "03-0004-000C", false, false, null, null, 0);
    	addFromTo("05-0001-0003", "03-0004-000D", false, false, null, null, 0);
    	
    }
    
    private void addFromTo(String from,String to,boolean bus,boolean inside,String fromBus,String toBus,int busStationNum){
    	
    	FromToOptimize ft = new FromToOptimize();
    	
    	ft.setFromCode(from);
    	ft.setToCode(to);
    	ft.setFromBus(fromBus);
    	ft.setToBus(toBus);
    	ft.setBus(bus);
    	ft.setInside(inside);
    	ft.setBusStationNum(busStationNum);
    	
    	locationService.addFromTo(ft);
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
    	locationService.save(getLocation("上海迪斯尼乐园酒店","01-0001",1));
    	locationService.save(getLocation("玩具总动员酒店","01-0002",1));
    	
    	
    	//02 行：接驳站点、MINI PTH、东PTC、西PTC、西PTH、南PTH
    	locationService.save(getLocation("接驳站点","02-0001",2));
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
    	locationService.save(getLocation("迪斯尼停车场","03-0007",3));
    	locationService.save(getLocation("管理中心停车场","03-0008",3));
    	
    	//04 娱：奇想花园、香草园、生态园、星愿湖公园、景观桥
    	locationService.save(getLocation("奇想花园","04-0001",4));
    	locationService.save(getLocation("香草园","04-0002",4));
    	locationService.save(getLocation("生态园","04-0003",4));
    	locationService.save(getLocation("星愿湖公园","04-0004",4));
    	
    	locationService.save(getLocation("绿地生态公园二期","04-0005",4));
    	locationService.save(getLocation("香草小街","04-0006",4));
    	
    	
    	locationService.save(getLocation("景观桥","04-0001",4));
    	
    	
    	//05购：奕欧来上海购物村
    	locationService.save(getLocation("奕欧来上海购物村","05-0001",5));
    	
    	//06商：申迪中心、迪士尼小镇
    	locationService.save(getLocation("申迪中心","06-0001",6));
    	locationService.save(getLocation("迪士尼小镇","06-0002",6));
	}
    
  	private void createQrCodeData(){
      	
      	//01-0001 迪斯尼乐园酒店
      	locationService.addQrCode("01-0001-0001", "迪斯尼乐园酒店入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("01-0001-0002", "迪斯尼乐园酒店入口B",QrCodeType.VIEW_ENTRANCE);
      	
      	//01-0002 玩具总动员酒店
      	locationService.addQrCode("01-0002-0001", "玩具总动员酒店入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//02-0001 接泊车站点
      	locationService.addQrCode("02-0001-0001", "购物村站(内圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0002", "申迪中心站(内圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0003", "迪斯尼乐园酒店站",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0004", "西公交枢纽站",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0005", "玩具总动员酒店站(内圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0006", "申迪北路站(内圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0007", "奇妙路地铁站",QrCodeType.BUS_STATION);
      	
      	
      	locationService.addQrCode("02-0001-0008", "购物村站(外圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0009", "申迪北路站(外圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0010", "玩具总动员酒店站(外圈)",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0011", "南公交枢纽站",QrCodeType.BUS_STATION);
      	locationService.addQrCode("02-0001-0012", "申迪中心站(外圈)",QrCodeType.BUS_STATION);
      	
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
      	locationService.addQrCode("04-0001-0001", "奇想花园出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0002-0001", "香草园出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0003-0001", "生态园出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("04-0004-0001", "星愿湖公园出入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//05-0001 奕欧来上海购物村
      	locationService.addQrCode("05-0001-0001", "奕欧来上海购物村入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("05-0001-0002", "奕欧来上海购物村入口B",QrCodeType.VIEW_ENTRANCE);
      	
      	//06商：申迪中心、迪士尼小镇
      	locationService.addQrCode("06-0001-0001", "申迪中心出入口A",QrCodeType.VIEW_ENTRANCE);
      	locationService.addQrCode("06-0001-0002", "申迪中心出入口B",QrCodeType.VIEW_ENTRANCE);
      	
      	locationService.addQrCode("06-0002-0001", "迪士尼小镇出入口A",QrCodeType.VIEW_ENTRANCE);
      	
      	
      	//生成停车场内部
      	//03-0001 P1停车场    内部
      	for(int i=0;i<50;i++){
      		locationService.addQrCode("03-0001-"+getNum(i+1), "P1停车场区域"+getNum(i+1),QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0002 P1停车场    内部
      	for(int i=0;i<118;i++){
      		locationService.addQrCode("03-0002-"+getNum(i+1), "P2停车场区域"+getNum(i+1),QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0003 P1停车场    内部
      	for(int i=0;i<67;i++){
      		locationService.addQrCode("03-0003-"+getNum(i+1), "P3停车场区域"+getNum(i+1),QrCodeType.PARK_INNER);
      	}
      	
      	
      	//03-0004 P1停车场    内部
      	for(int i=0;i<111;i++){
      		locationService.addQrCode("03-0004-"+getNum(i+1), "P4停车场区域"+getNum(i+1),QrCodeType.PARK_INNER);
      	}
      	
      }
    
    
    private String getNum(int num){
    	String str = "0000"+num;
		return str.substring(str.length()-4, str.length());
    }
    


}
