package com.disney.service;

import java.util.List;

import com.disney.bo.LoToLoBO;
import com.disney.model.Location;

public interface LocationService {
	
	public List<Location> findAll();
	
	public Location find(String qrCode);
	
	public LoToLoBO loadLoToLoBO(String from,String to);
	
	
/*	public void saveLoToLoBO(LoToLoBO bo);*/
}
