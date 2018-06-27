package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAvailableParks() {
		List<Park> parkList = new ArrayList<>();
		String findAllParks = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(findAllParks);
		while (results.next()) {
			Park thePark = new Park();
			parkList.add(thePark);
		}
		return parkList;
	}
	
	@Override
	public String getLocation(int parkID) {
		String findLocation = ("SELECT location " + 
				"FROM park " + 
				"WHERE park_id = 1");
		SqlRowSet results = jdbcTemplate.queryForRowSet(findLocation);
		String location = results.toString();
		System.out.println(location);
		return location;
		
	}
	
	@Override
	public String getDateEstablished() {
		String findDateEstablished = ("SELECT establish_date " + 
				"FROM park " + 
				"WHERE park_id = 1");
		SqlRowSet results = jdbcTemplate.queryForRowSet(findDateEstablished);
		String dateEstablished = results.toString();
		dateEstablished = dateEstablished.concat("/" + dateEstablished.substring(0, 4));
		dateEstablished = dateEstablished.substring(5).replace('-', '/');
		return dateEstablished;
	}
	
	@Override
	public String getArea() {
		String findArea = ("SELECT area " + 
				"FROM park " + 
				"WHERE park_id = 1");
		SqlRowSet results = jdbcTemplate.queryForRowSet(findArea);
		String area = results.toString();
		ArrayList<Character> arealist = new ArrayList<>();
		for (int i = 0; i < area.length(); i++) {
			arealist.add(area.charAt(i));
		}
		int size = area.length();
		int commas = size / 3;
		if (size % 3 == 0 && size > 3) {
			for (int i = 3; i < size; i = i + 3) {
				arealist.add(i, ',');
			}
		}
		return area;
		
	}
	
	

}
