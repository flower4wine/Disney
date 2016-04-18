package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.QrCodeDao;
import com.disney.model.QrCode;

@Repository
public class QrCodeDaoImpl extends BaseDaoImpl<QrCode> implements QrCodeDao {
	
	public QrCode find(String code) {
		
		String hql = "from QrCode where qrCode = :code";
		
		@SuppressWarnings("unchecked")
		List<QrCode> list =  this.getCurrentSession()
			.createQuery(hql)
			.setString("code", code)
			.list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		
		return null;
		
	}

	@Override
	public int count() {
		
		String hql = "from QrCode";
		
		@SuppressWarnings("unchecked")
		List<QrCode> list =  this.getCurrentSession()
				.createQuery(hql)
				.list();
		
		return (list == null) ? 0 : list.size();
	}

}
