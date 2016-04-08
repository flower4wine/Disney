package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.LoToLoStepDao;
import com.disney.model.LoToLoStep;

@Repository
public class LoToLoStepDaoImpl extends BaseDaoImpl<LoToLoStep> implements LoToLoStepDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<LoToLoStep> find(Long lo2loId, Integer type) {
		String hql = "from LoToLoStep where loToLoId = :lo2loId and type = :type order by step asc";
		
		 return this.getCurrentSession()
				.createQuery(hql)
				.setLong("lo2loId", lo2loId)
				.setInteger("type", type)
				.list();
	}
	
}
