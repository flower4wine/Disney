package com.disney.bo;

import java.util.List;


public class LoToLoBO {
	
	private QrCodeBO from;
	private QrCodeBO to;
	
	private List<LoToLoStepBO> innerSteps;
	private List<LoToLoStepBO> outSteps;
	
	private String time;
	private String distince;
	
	private String innerUrl;
	private String outUrl;
	
	public QrCodeBO getFrom() {
		return from;
	}
	public void setFrom(QrCodeBO from) {
		this.from = from;
	}
	public QrCodeBO getTo() {
		return to;
	}
	public void setTo(QrCodeBO to) {
		this.to = to;
	}
	public List<LoToLoStepBO> getInnerSteps() {
		return innerSteps;
	}
	public void setInnerSteps(List<LoToLoStepBO> innerSteps) {
		this.innerSteps = innerSteps;
	}
	public List<LoToLoStepBO> getOutSteps() {
		return outSteps;
	}
	public void setOutSteps(List<LoToLoStepBO> outSteps) {
		this.outSteps = outSteps;
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
	public String getInnerUrl() {
		return innerUrl;
	}
	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}
	public String getOutUrl() {
		return outUrl;
	}
	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}
	
}
