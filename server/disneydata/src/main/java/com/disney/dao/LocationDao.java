package com.disney.dao;

import com.disney.model.Location;

public interface LocationDao extends BaseDao<Location>{
	public Location find(String qrCode);
}
