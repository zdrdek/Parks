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
	public void getParkInfo(int id) {
		Park thePark = null;
		String sqlGetPark = ("SELECT * FROM park WHERE park_id = ?");
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetPark, id);
		if (results.next()) {
			thePark = mapRowToPark(results);
		}
		System.out.println('\n' + thePark.getName() + " National Park" + '\n' + "Location:                         "
				+ thePark.getLocation() + '\n' + "Established:                      " + thePark.getEstablishDate()
				+ '\n' + "Area:                             " + thePark.getArea() + " sq km " + '\n'
				+ "Annual Visitors:                  " + thePark.getVisitors() + '\n' + '\n'
				+ thePark.getDescription());

	}

	private Park mapRowToPark(SqlRowSet results) {
		Park thePark = new Park();
		thePark.setParkId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date"));
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));
		return thePark;
	}

}
