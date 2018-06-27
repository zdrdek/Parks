package com.techelevator;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public void getCampgrounds(long id) {
		List<Campground> theCampgrounds = new ArrayList<>();
		String getAllCampgrounds = ("SELECT * " + 
				"FROM campground " +
				"WHERE park_id = ?");
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAllCampgrounds, id);
		while (results.next()) {
			Campground theCampground = mapRowToCampgrounds(results);
			theCampgrounds.add(theCampground);
			System.out.println("#" + theCampground.getId() 
			+ "   " + theCampground.getName() 
			+ "   " + theCampground.getOpenFromMonth() 
			+ "   " + theCampground.getOpenToMonth() 
			+ "   " + theCampground.getDailyFee());
			
		}
		
	}

	private Campground mapRowToCampgrounds(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setId(results.getLong("campground_id"));
		theCampground.setParkId(results.getLong("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFromMonth(results.getInt("open_from_mm"));
		theCampground.setOpenToMonth(results.getInt("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		return theCampground;
	}

}
