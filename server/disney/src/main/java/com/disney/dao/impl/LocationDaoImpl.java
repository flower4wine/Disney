package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.LocationDao;
import com.disney.model.Location;

@Repository
public class LocationDaoImpl extends BaseDaoImpl<Location> implements
		LocationDao {

	@SuppressWarnings("unchecked")
	@Override
	public Location find(String qrCode) {
		String hql ="from Location where location=:qrCode";
		
		List<Location> list = this.getCurrentSession().createQuery(hql).setString("qrCode", qrCode).list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

}
