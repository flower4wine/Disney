package com.disney.util;

import java.util.HashMap;
import java.util.Map;

public class Ajax {
	
	public static final String EXCEPTION_ERROR_MSG = "系统处理异常。";//"系统处理异常。";
	
	public static final String ATTR_SUCCESS = "success";
	public static final String ATTR_MESSAGE = "message";
	public static final String ATTR_VALUE = "value";
	public static final String ATTR_DATA = "data";

	public static final String MESSAGE_SUCCESS = "ok";

	public static Map<String, Object> buildBasicResult(boolean success, String message) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(ATTR_SUCCESS, success);
		resultMap.put(ATTR_MESSAGE, message);
		return resultMap;
	}

	public static Map<String, Object> buildSuccessResult() {
		return buildBasicResult(true, MESSAGE_SUCCESS);
	}

	public static Map<String, Object> getSuccessReturnMapWithData(Object object) {
		Map<String, Object> returnMap = buildSuccessResult();
		returnMap.put(ATTR_DATA, object);
		return returnMap;
	}

	public static Map<String, Object> getSuccessReturnMapWithData(String key, Object object) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put(key, object);
		return getSuccessReturnMapWithData(returnMap);
	}

	public static Map<String, Object> buildErrorResult(String errorMessage) {
		return buildBasicResult(false, errorMessage);
	}

	public static Map<String, Object> buildExceptionResult() {
		return buildBasicResult(false, EXCEPTION_ERROR_MSG);
	}

}
