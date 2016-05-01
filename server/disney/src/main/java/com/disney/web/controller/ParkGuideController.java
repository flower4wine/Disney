package com.disney.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.disney.bo.LoToLoBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.FromToOptimize;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.LocationService;
import com.disney.util.ViewUtil;
import com.disney.web.vo.GuideVO;
import com.disney.web.vo.LocationVO;

@Controller
@RequestMapping("/pg")
public class ParkGuideController {
	
	//private static Logger log = LoggerFactory.getLogger(ParkGuideController.class);
	
	@Autowired
	private WeChatHandler weChatHandler;

	@Autowired
	private LocationService locationService;
	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			return ViewUtil.error("10001");
		}
		
		if(!co.startsWith("03")){
			return ViewUtil.error("10002");
		}
		
		Location parent = locationService.find(co.substring(0,7));
		Location child = locationService.find(co);
		
		String name = "/guide/location";
		
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		if(parent==null || child==null){
			//位置信息不存在
			return ViewUtil.error("10005");
		}
		
		
		vo.setName(parent.getName());
		vo.setCode(co);
		vo.setRemark(child.getName());
		vo.setLocationImg(child.getLocationImg());
	
		//Get WeXin info
		ViewUtil.getWeChatInfo(view, weChatHandler, request);

		view.addObject("location", vo);
		return view;
	}
	
	@RequestMapping("/loConfirm")
	public ModelAndView locationConfirm(HttpSession session, String code) throws Exception {
		String userOpenId = SessionHelper.getLoginUserOpenId(session);
		
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul==null){
			ul = new UserLocation();
			ul.setOpenId(userOpenId);
		}
		
		ul.setCreatedAt(new Date());
		
		if(StringUtils.isNotEmpty(code) && code.length() == 12){
			ul.setParkLocation(code);
		}
		locationService.saveUserLocation(ul);
		
		String name = "redirect:/pg/locations.html";
		ModelAndView view = ViewUtil.view(name);
		
		return view;
	}
	
	@RequestMapping("/locations")
	public ModelAndView outlets(HttpServletRequest request) throws Exception {
		String name = "/guide/locations";
		ModelAndView view = ViewUtil.view(name);
		return view;
	}
	
	
	@RequestMapping("/toLocation")
	public ModelAndView outlet(HttpServletRequest request,String toLocation) throws Exception {
		String name = "/guide/toLocation";
		ModelAndView view = ViewUtil.view(name);
		
		if(StringUtils.isEmpty(toLocation) || toLocation.length()!=12){
			//03-0001-0001 二维码格式不正确
			return ViewUtil.error("10001");
		}
		
		String userOpenId = SessionHelper.getLoginUserOpenId(request.getSession());
		UserLocation ul = locationService.findUserLocation(userOpenId);
		
		if(ul!=null && StringUtils.isNotEmpty(ul.getParkLocation()) && ul.getParkLocation().length()==12){
			//选择性路线 选取唯一路线
			FromToOptimize fromTo = locationService.getFromTo(ul.getParkLocation().substring(0, 7), toLocation.substring(0, 7));
			
			if(fromTo == null){
				return ViewUtil.error("10004");
			}
			
			LoToLoBO bo = locationService.loadLoToLoBO(ul.getParkLocation(),fromTo.getToCode());
			
			if(bo == null){
				return ViewUtil.error("10004");
			}
			
			view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.OUT));
			
		}else{
			//未记录位置信息 如何处理  
			return ViewUtil.error("10007");
		}
		
		return view;
	}
}
