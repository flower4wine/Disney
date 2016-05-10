package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.ParkDao;
import com.disney.model.Park;

@Repository
public class ParkDaoImpl extends BaseDaoImpl<Park> implements ParkDao {

	@SuppressWarnings("unchecked")
	@Override
	public Park findByJsCode(String jsCode) {
		String hql ="from Park where jsCode=:jsCode";
		
		List<Park> list = this.getCurrentSession().createQuery(hql).setString("jsCode", jsCode).list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
}
