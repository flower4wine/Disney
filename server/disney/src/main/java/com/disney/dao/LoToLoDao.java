package com.disney.dao;

import com.disney.model.LoToLo;

public interface LoToLoDao extends BaseDao<LoToLo> {
	public LoToLo find(String from,String to);
}
