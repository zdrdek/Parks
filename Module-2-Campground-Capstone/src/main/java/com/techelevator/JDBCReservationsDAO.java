package com.techelevator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationsDAO implements ReservationsDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationsDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void searchForReservations() {
		Scanner input = new Scanner(System.in);
		System.out.println("Which campground (enter 0 to cancel)? _");
		long campgroundNumber = input.nextLong();
		if (campgroundNumber == 0) {
			return;
		}
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String arrivalDateS = input.nextLine();
		Date arrivalDate = null;
		try {
			arrivalDate =new SimpleDateFormat("yyyy-mm-dd").parse(arrivalDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localArrivalDate = LocalDate.parse(arrivalDateS);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String departureDateS = input.nextLine();
		Date departureDate = null;
		try {
			departureDate =new SimpleDateFormat("yyyy-mm-dd").parse(departureDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localDepartureDate = LocalDate.parse(departureDateS);
		long daysBetween = ChronoUnit.DAYS.between(localArrivalDate, localDepartureDate);
		System.out.println("\n" + "Results Matching Your Search Criteria");
		List<Reservations> theReservations = new ArrayList<>();
		List<Site> theSites = new ArrayList<>();
		String getAvailableReservations = "SELECT site.site_id, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities,"
											+ "campgroun.daily_fee * ? \n" + 
											"FROM site\n" + 
											"JOIN reservation\n" + 
											"ON site.site_id = reservation.site_id\n" + 
											"JOIN campground\n" + 
											"ON campground.campground_id = site.campground_id\n" + 
											"WHERE campground.campground_id = ? AND site.site_id NOT IN(SELECT site.site_id\n" + 
											"                                                     FROM site\n" + 
											"                                                     JOIN reservation\n" + 
											"                 ON site.site_id = reservation.site_id\n" + 
											"                 WHERE '2018-07-26' <= reservation.to_date \n" + 
											"                 AND '2018-08-05' >= reservation.from_date)\n" + 
											"LIMIT 5;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAvailableReservations, daysBetween, campgroundNumber);
		System.out.println("Results Matching Your Search Criteria:");
		System.out.println("Site No.     Max Occupancy     Handicap Accessible     Max RV Length     Utilities     Cost");
		while (results.next()) {
			Site theSite = mapRowToSite(results);
			theSites.add(theSite);
			Reservations theReservation = mapRowToReservations(results);
			theReservations.add(theReservation);
			System.out.println(theReservation.getSiteId() + "     " +
			                   theSite.getMaxOccupancy() + "     " +
					theSite.getIsAccessible() + "     " + 
			                   theSite.getMaxRVLength() + "     " +
					theSite.getHasUtilities() + "     " +
			                   theReservation.getDailyFee());
		}
		
		
	}
	
	public void availableReservations() {
		
	}
	
	private Reservations mapRowToReservations(SqlRowSet results) {
		Reservations theReservations = new Reservations();
		theReservations.setReservationId(results.getLong("reservation_id"));
		theReservations.setSiteId(results.getLong("site_id"));
		theReservations.setName(results.getString("name"));
		theReservations.setFromDate(results.getDate("from_date"));
		theReservations.setToDate(results.getDate("to_date"));
		theReservations.setDateEntered(LocalDate.now());
		theReservations.setDailyFee(results.getBigDecimal("daily_fee"));
		return theReservations;
	}
	private Site mapRowToSite(SqlRowSet results) {
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
