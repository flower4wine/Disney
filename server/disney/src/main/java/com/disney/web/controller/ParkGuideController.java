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
import com.disney.bo.QrCodeBO;
import com.disney.constant.Lo2LoStepType;
import com.disney.handler.config.SessionHelper;
import com.disney.handler.wechat.WeChatHandler;
import com.disney.model.Location;
import com.disney.model.UserLocation;
import com.disney.service.Lo2loService;
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
	
	@Autowired
	private Lo2loService lo2loService;
	
	@RequestMapping("/lo")
	public ModelAndView parkLocation(HttpServletRequest request,String co) throws Exception {
		
		//Validate check error
		if(StringUtils.isEmpty(co) || co.length()!=12){
			return ViewUtil.error("10001");
		}
		
		//增加接驳车站的导览
		if(!(co.startsWith("03") || co.startsWith("02")) ){
			return ViewUtil.error("10002");
		}
		
		
		String name = "/guide/location";
		
		ModelAndView view = ViewUtil.view(name);
		LocationVO vo = new LocationVO();
		
		
		QrCodeBO bo = locationService.queryQrCodeInfo(co);
		Location parentLoc = locationService.find(co.substring(0,7));
		
		if(bo==null || parentLoc==null){
			//位置信息不存在
			return ViewUtil.error("10005");
		}
		
		
		vo.setCode(bo.getQrode());
		vo.setLocationImg(bo.getLocationImg());
		vo.setName(parentLoc.getName());
		
		if(co.startsWith("03")){
			String region = StringUtils.isEmpty(bo.getRegion()) ? "" : bo.getRegion()+"  ";
			String range = StringUtils.isEmpty(bo.getCodeRange()) ? "" : (bo.getCodeRange()+"  附近");
			vo.setRemark(region + range);
			vo.setParkLocation(true);
		}else if(co.startsWith("02")){
			vo.setParkLocation(false);
			vo.setRemark(bo.getQrLocationName());
		}
		
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
			
			ul.setConfirmLocation(code);
			ul.setScanLocation(code);
			
			if(code.startsWith("03")){
				ul.setParkLocation(code);
			}
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
	
	
	/**
	 * 停车导览  停车场 到 奕欧来购物村的导览    接驳车站导览  到 奕欧来购物村的导览
	 * @param request
	 * @param toLocation
	 * @return
	 * @throws Exception
	 */
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
			
			//TODO 需要增加接驳车站导览到目标位置
			LoToLoBO bo = lo2loService.loadLoToLoBO(ul.getConfirmLocation(),toLocation);
			
			if(bo == null){
				return ViewUtil.error("10004");
			}
			
			// 根据 parkLocation判断停车位置是否靠近出入口  如果靠近出入口需要 忽略内部导览。 通过LoToLoBO 返回判断
			if(bo.isIgnoreInner()){
				view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.OUT,true));
			}else{
				view.addObject("guide", GuideVO.boToVo(bo,Lo2LoStepType.OUT));
			}
			
		}else{
			//未记录位置信息 如何处理  
			return ViewUtil.error("10007");
		}
		
		return view;
	}
}
