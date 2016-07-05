package com.disney.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.disney.bo.wx.Article;
import com.disney.bo.wx.NewsMessage;
import com.disney.constant.QrCodeFix;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.DateUtils;
import com.disney.util.MessageUtil;
import com.disney.util.SignUtil;

@Controller
public class WeChatServerController {

	@Autowired
	private WeChatHandler wxHandler;

	@Autowired
	private LocationService locationService;


	@RequestMapping(value="/receive",method=RequestMethod.POST)
	public void receiveEvent(HttpServletRequest request,HttpServletResponse response) throws Exception {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//这里进行签名认证
		if(!SignUtil.checkSignature(request.getParameter("signature"),
				request.getParameter("timestamp"),
				request.getParameter("nonce"))){
			return;
		}

		PrintWriter out = response.getWriter();

		Map<String, String> requestMap = MessageUtil.parseXml(request);

		if(requestMap!=null && ("SCAN".equalsIgnoreCase(requestMap.get("Event")) ||"subscribe".equalsIgnoreCase( requestMap.get("Event")))){
			String key = requestMap.get("EventKey");

			if("subscribe".equalsIgnoreCase( requestMap.get("Event") ) && key.length()>=12 ){
				key = key.substring(key.length()-12, key.length());
			}

			String respXml = "";

			if(key.equals(QrCodeFix.LANDSCAPE_BRIDGE)){
				respXml = generateLandscapeBridgeXml(requestMap);
			}else{
				//标准二维码长度为12 例如03-0001-0001
				if(StringUtils.isEmpty(key) || key.length()!= 12){
					return;
				}
				
				respXml = generateQrCodeScanXml(requestMap, key);
			}
			
			out.print(respXml);

		}

		out.close();
		out = null;
	}
	
	private String getNewsMessageXML(Map<String, String> requestMap,String desc,String picUrl,String title,String url){
		NewsMessage msg = new NewsMessage();
		
		Article article = new Article();
		
		article.setDescription(desc);
		article.setPicUrl(picUrl);
		article.setTitle(title);
		article.setUrl(url);
		
		List<Article> articles = new ArrayList<Article>();
		articles.add(article);
		
		msg.setArticles(articles);
		msg.setArticleCount(msg.getArticles().size());
		
		msg.setToUserName(requestMap.get("FromUserName"));
		msg.setFromUserName(requestMap.get("ToUserName"));
		
		msg.setCreateTime(new Date().getTime());
		msg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
		String respXml = MessageUtil.messageToXml(msg);
		return respXml;
	}
	
	
	private String generateLandscapeBridgeXml(Map<String, String> requestMap){
		
		String desc = "欢迎您访问迪士尼智慧停车平台,您扫描的二维码 可以进入景观桥景观介绍，点击查看详情。";
		String picUrl = wxHandler.newsPicUrl();
		String title = "景观桥介绍测试";
		String url = wxHandler.landBridgeForwardUrl();
		
		return getNewsMessageXML(requestMap,desc,picUrl,title,url);
	}
	
	
	private String generateQrCodeScanXml(Map<String, String> requestMap,String key){
		String desc = qrCodeScan(requestMap, key);
		String picUrl =  wxHandler.newsPicUrl();
		String title = "迪士尼导览指南";
		String url = wxHandler.guideForwardUrl();
		return getNewsMessageXML(requestMap,desc,picUrl,title,url);
	}
	
	
	private String qrCodeScan(Map<String, String> requestMap,String key){

		//Save scan location
		UserLocation ul = locationService.findUserLocation(requestMap.get("FromUserName"));
		//过期清除原 停车地点
		Date now = DateUtils.getStartDate(new Date());

		if(ul==null){
			ul = new UserLocation();

			boolean isDebug = wxHandler.isDebug();

			if(isDebug){
				ul.setOpenId("test");
			}else{
				ul.setOpenId(requestMap.get("FromUserName"));
			}

		}

		//判断最后一次生成时间 是不是今天
		if(DateUtils.getStartDate(ul.getCreatedAt()).compareTo(now) != 0){
			ul.setScanLocation("");
			ul.setParkLocation("");
			ul.setLeaveLocation("");
			ul.setCreatedAt(now);
		}

		if(StringUtils.isNotEmpty(key) && key.length() == 12){
			ul.setScanLocation(key);
		}

		locationService.saveUserLocation(ul);

		Location parent = locationService.find(key.substring(0,7));
		
		String name = "";
		
		if(key.startsWith("03-")){
			name = parent.getName()+"内部二维码";
		}else{
			name = parent.getName();
		}

		return "欢迎您访问度假区智慧停车平台！您扫描的二维码是"+name+"。点击底部的【停车导览】菜单畅游度假区！点击【阅读全文】查看《导览指南》！";
	}


	@RequestMapping(value="/receive",method=RequestMethod.GET)
	@ResponseBody
	public String check(HttpServletRequest request,HttpServletResponse response) throws IOException {

		// 微信加密签名  
		String signature = request.getParameter("signature");  
		// 时间戳  
		String timestamp = request.getParameter("timestamp");  
		// 随机数  
		String nonce = request.getParameter("nonce");  
		// 随机字符串  
		String echostr = request.getParameter("echostr");  

		//
		PrintWriter out = response.getWriter();
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
			out.print(echostr);
		}

		out.close();
		out = null;
		
		return "";
	}

}
