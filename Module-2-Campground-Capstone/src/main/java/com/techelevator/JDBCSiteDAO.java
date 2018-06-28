package com.techelevator;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCSiteDAO implements SiteDAO{
	
	
	
	private Site mapRowToCampgrounds(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setSiteNumber(results.getLong("site_number"));
		theSite.setMaxOccupancy(results.getLong("max_occupancy"));
		theSite.setCampgroundId(results.getLong("campground_id"));
		theSite.setIsAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setHasUtilities(results.getBoolean("utilities"));
		return theSite;
	}

}
