package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.FromToOptimizeDao;
import com.disney.model.FromToOptimize;

@Repository
public class FromToOptimizeDaoImpl extends BaseDaoImpl<FromToOptimize> implements FromToOptimizeDao {

	@SuppressWarnings("unchecked")
	@Override
	public FromToOptimize find(String from, String to) {
		String hql = "from FromToOptimize where fromCode = :from and toCode = :to";
		
		List<FromToOptimize> list = this.getCurrentSession().createQuery(hql)
				.setString("from", from)
				.setString("to", to)
				.list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FromToOptimize findStartWith(String from, String to) {
		String hql = "from FromToOptimize where fromCode like :from and toCode like :to";
		
		List<FromToOptimize> list = this.getCurrentSession().createQuery(hql)
				.setString("from", from+"%")
				.setString("to", to+"%")
				.list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
}
