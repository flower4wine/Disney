package com.disney.dao;

import com.disney.model.QrCode;

public interface QrCodeDao extends BaseDao<QrCode> {
	
	public QrCode find(String code);
	
	public int count();
	
	public QrCode findByUrl(String url);

}
