package com.disney.dao.impl;

import org.springframework.stereotype.Repository;

import com.disney.dao.LocationTimeDistanceDao;
import com.disney.model.LocationTimeDistance;

@Repository
public class LocationTimeDistanceDaoImpl extends
BaseDaoImpl<LocationTimeDistance> implements LocationTimeDistanceDao {

	@Override
	public LocationTimeDistance getLocationTimeDistance(String from, String to) {

		String hql = "from LocationTimeDistance where fromCode = :from and toCode = :to";

		Object obj =  this.getCurrentSession()
				.createQuery(hql)
				.setString("from", from)
				.setString("to", to)
				.uniqueResult();


		if(obj!=null){
			return (LocationTimeDistance)obj;
		}

		obj =  this.getCurrentSession()
				.createQuery(hql)
				.setString("from", to)
				.setString("to", from)
				.uniqueResult();

		if(obj!=null){
			return (LocationTimeDistance)obj;
		}

		return null;
	}

}
