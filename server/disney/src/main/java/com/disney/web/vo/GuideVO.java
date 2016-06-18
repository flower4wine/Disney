package com.disney.web.vo;

import java.util.ArrayList;
import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.bo.LoToLoStepBO;
import com.disney.constant.Lo2LoStepType;

public class GuideVO {
	
	private String destName;
	private List<GuideStepVO> steps = new ArrayList<GuideStepVO>();
	private String time;
	private String distince;
	
	private String innerPic;
	private String outPic;
	
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDistince() {
		return distince;
	}
	public void setDistince(String distince) {
		this.distince = distince;
	}
	public String getInnerPic() {
		return innerPic;
	}
	public void setInnerPic(String innerPic) {
		this.innerPic = innerPic;
	}
	public String getOutPic() {
		return outPic;
	}
	public void setOutPic(String outPic) {
		this.outPic = outPic;
	}
	
	public List<GuideStepVO> getSteps() {
		return steps;
	}
	public void setSteps(List<GuideStepVO> steps) {
		this.steps = steps;
	}
	
	/**
	 * 内外都导览
	 * @param bo
	 * @param type
	 * @return
	 */
	public static GuideVO boToVo(LoToLoBO bo,Integer type){
		return boToVo(bo,type,false);
	}
	
	
	/**
	 * 只进行外部导览
	 * @param bo
	 * @param type
	 * @param ignoreInner
	 * @return
	 */
	public static GuideVO boToVo(LoToLoBO bo,Integer type,boolean ignoreInner){
		
		GuideVO vo = new GuideVO();
		
		vo.setDestName(bo.getTo().getQrLocationName());
		
		
		List<LoToLoStepBO> steps = new ArrayList<LoToLoStepBO>();
		
		if(ignoreInner){
			steps.addAll(bo.getOutSteps());
		}else{
			if(type == Lo2LoStepType.OUT){
				steps.addAll(bo.getInnerSteps());
				steps.addAll(bo.getOutSteps());
			}else{
				steps.addAll(bo.getOutSteps());
				steps.addAll(bo.getInnerSteps());
			}
		}
		
		List<GuideStepVO> voSteps = new ArrayList<GuideStepVO>();
		
		for(LoToLoStepBO step:steps){
			GuideStepVO voStep = new GuideStepVO();
			
			voStep.setRemark(step.getRemark());
			voStep.setStepType(step.getStepType());
			
			voSteps.add(voStep);
		}
		
		vo.setSteps(voSteps);
		
		vo.setTime(bo.getTime());
		vo.setDistince(bo.getDistince());
		vo.setOutPic(bo.getOutUrl());
		
		if(ignoreInner){
			vo.setInnerPic(bo.getOutUrl());
		}else{
			vo.setInnerPic(bo.getInnerUrl());
		}
		
		return vo;
		
	}
}
