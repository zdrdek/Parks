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
	
	

}
