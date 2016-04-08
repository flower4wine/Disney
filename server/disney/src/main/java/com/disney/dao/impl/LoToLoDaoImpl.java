package com.disney.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.disney.dao.LoToLoDao;
import com.disney.model.LoToLo;

@Repository
public class LoToLoDaoImpl extends BaseDaoImpl<LoToLo> implements LoToLoDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public LoToLo find(String from,String to){
		
		String hql = "from LoToLo where fromQrCode =:from and toQrCode =:to";
		
		List<LoToLo> list = this.getCurrentSession()
				.createQuery(hql)
				.setString("from", from)
				.setString("to", to)
				.list();
		
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

}
