package com.techelevator;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAvailableParks();

	public void getParkInfo(int id);
	
}
