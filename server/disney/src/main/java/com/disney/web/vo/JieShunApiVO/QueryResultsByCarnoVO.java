package com.disney.web.vo.jieshunapivo;

public class QueryResultsByCarnoVO {
	
	//支付是否成功(0=true,1=false)
	private int payResults;
	
	//支付错误编码
	private String errorCode;
	
	//支付错误原因
	private String errorReason;

	public int getPayResults() {
		return payResults;
	}

	public void setPayResults(int payResults) {
		this.payResults = payResults;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	
}
