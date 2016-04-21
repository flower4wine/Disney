package com.disney.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.disney.bo.wx.TextMessage;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.DateUtils;
import com.disney.util.SignUtil;
import com.disney.util.ViewUtil;
import com.disney.wx.MessageUtil;

@Controller
public class AuthController {
	
	private static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	WeChatHandler wxHandler;
	
	@Autowired
	LocationService locationService;

	@RequestMapping("/auth")
	public ModelAndView auth() {
		ModelAndView view = ViewUtil.view("/auth");

		if(wxHandler.isDebug()){
			view.setViewName("redirect:/wxlogin.html");
		}else{
			view.addObject("authUrl",wxHandler.getWXBaseAuthUrl());
		}

		return view;
	}

	@RequestMapping("/wxlogin")
	public ModelAndView wxlogin(String code,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String toUrl = SessionHelper.getToUrl(request);

		//Judge if from wexin forward
		if(StringUtils.isNotEmpty(code)){
			Map<String,Object> token = wxHandler.getAuthInfo(code);

			if(token == null || token.get("openid")==null){
				//获得Token失败
				log.error("微信登录出错。");

			}else{
				SessionHelper.setUserOpenId(session, token.get("openid").toString());
				SessionHelper.clearToUrl(session);
			}

		}else{
			boolean isDebug = wxHandler.isDebug();
			if(isDebug){
				if(!SessionHelper.isLogin(session)){
					SessionHelper.setUserOpenId(session, "test");
				}
			}
		}

		return ViewUtil.view("redirect:"+toUrl);
	}

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
			
			// 发送方帐号
			String fromUserName = requestMap.get("FromUserName");
			// 开发者微信号
			String toUserName = requestMap.get("ToUserName");
			
			
			//Save scan location
			UserLocation ul = locationService.findUserLocation(fromUserName);
			//过期清除原 停车地点
			Date now = DateUtils.getStartDate(new Date());
			
			if(ul==null){
				ul = new UserLocation();
				
				boolean isDebug = wxHandler.isDebug();
				
				if(isDebug){
					ul.setOpenId("test");
				}else{
					ul.setOpenId(fromUserName);
				}
				
			}
			
			//判断最后一次生成时间 是不是今天
			if(DateUtils.getStartDate(ul.getCreatedAt()).compareTo(now) != 0){
				ul.setParkLocation("");
				ul.setLeaveLocation("");
				ul.setCreatedAt(now);
			}
			
			if(StringUtils.isNotEmpty(key) && key.length() == 12){
				ul.setScanLocation(key);
				
				if(key.startsWith("03-")){
					ul.setParkLocation(key);
				}else{
					ul.setLeaveLocation(key);
				}
			}
			
			locationService.saveUserLocation(ul);
			
			String respXml = "";
			
			Location parent = locationService.find(key.substring(0,7));
			Location location = locationService.find(key);
			TextMessage msg = new TextMessage();
			msg.setToUserName(fromUserName);
			msg.setFromUserName(toUserName);
			msg.setCreateTime(new Date().getTime());
			msg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			msg.setContent("欢迎您访问迪斯尼智慧停车平台,您扫描的二维码为："+parent.getName()+"," +location.getName()+",<a href='http://disney.digirogar.com/disney'>请点击菜单进行操作。</a>");
			
			respXml = MessageUtil.messageToXml(msg);
			out.print(respXml);
			
		}
		
		out.close();
		out = null;
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
