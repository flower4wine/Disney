package com.disney.dao;

import com.disney.model.FromToOptimize;

public interface FromToOptimizeDao extends BaseDao<FromToOptimize> {
	
	public FromToOptimize find(String from,String to);
	
	public FromToOptimize findStartWith(String from,String to);
	
}
