package com.disney.handler.jieshun.api;

public class JSApiResultBO {

	private int resultCode;
	private boolean success;
	private String errorMessage;
	private Object returnObject;
	
	
	public JSApiResultBO(Object obj){
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
	public Object getReturnObject() {
		return returnObject;
	}
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	
}
