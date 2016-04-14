package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.UserLocationDao;
import com.disney.model.UserLocation;

@Repository
public class UserLocationDaoImpl extends BaseDaoImpl<UserLocation> implements
		UserLocationDao {

	@SuppressWarnings("unchecked")
	@Override
	public UserLocation find(String openId) {
		
		String hql = "from UserLocation where openId = :openId";
		
		List<UserLocation> list = this.getCurrentSession().createQuery(hql).setString("openId", openId).list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}
	
}
