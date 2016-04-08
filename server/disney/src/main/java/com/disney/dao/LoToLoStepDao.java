package com.disney.dao;

import java.util.List;

import com.disney.model.LoToLoStep;

public interface LoToLoStepDao extends BaseDao<LoToLoStep> {
	
	List<LoToLoStep> find(Long lo2loId,Integer type);

}
