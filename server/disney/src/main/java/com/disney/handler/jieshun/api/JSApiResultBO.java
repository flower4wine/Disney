package com.disney.handler.jieshun.api;

import java.util.Map;

public class JSApiResultBO {

	private int resultCode;
	private boolean success;
	private String errorMessage;
	private Map<String,Object> returnObject;
	
	
	public JSApiResultBO(Map<String,Object> obj){
		this.resultCode = 0;
		this.success = true;
		this.returnObject = obj;
	}
	
	public JSApiResultBO(int resultCode,String errorMessage){
		this.resultCode = resultCode;
		this.success = false;
		this.errorMessage = errorMessage;
	}
	
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Map<String,Object> getReturnObject() {
		return returnObject;
	}
	public void setReturnObject(Map<String,Object> returnObject) {
		this.returnObject = returnObject;
	}
	
}
