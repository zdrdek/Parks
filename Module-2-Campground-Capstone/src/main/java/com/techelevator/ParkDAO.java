package com.techelevator;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAvailableParks();

	public String getLocation();
	
	public String getDateEstablished();

	public String getArea();
	
}
