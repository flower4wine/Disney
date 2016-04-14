package com.disney.dao;

import com.disney.model.UserLocation;

public interface UserLocationDao  extends BaseDao<UserLocation> {
	
	public UserLocation find(String openId);

}
