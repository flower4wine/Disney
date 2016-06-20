package com.disney.dao;

import com.disney.model.LocationTimeDistance;

public interface LocationTimeDistanceDao extends BaseDao<LocationTimeDistance> {
	
	LocationTimeDistance getLocationTimeDistance(String from, String to);

}
