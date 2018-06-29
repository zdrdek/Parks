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

	Scanner input = new Scanner(System.in);

	public void searchForReservations() {
		System.out.println("Which campground (enter 0 to cancel)? _");
		long campgroundNumber = input.nextLong();
		input.nextLine();
		if (campgroundNumber == 0) {
			return;
		}
		Date arrivalDate = null;
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String arrivalDateS = input.nextLine();
		int arrivalMonth = Integer.parseInt(arrivalDateS.substring(5, 7));
		try {
			arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(arrivalDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localArrivalDate = LocalDate.parse(arrivalDateS);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String departureDateS = input.nextLine();
		int departureMonth = Integer.parseInt(departureDateS.substring(5, 7));
		Date departureDate = null;
		try {
			departureDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localDepartureDate = LocalDate.parse(departureDateS);
		long daysBetween = ChronoUnit.DAYS.between(localArrivalDate, localDepartureDate);
		BigDecimal BDDaysBetween = new BigDecimal(daysBetween);
		System.out.println("\n" + "Results Matching Your Search Criteria");
		List<Reservations> theReservations = new ArrayList<>();
		List<Site> theSites = new ArrayList<>();
		List<Campground> theCampgrounds = new ArrayList<>();
		String getAvailableReservations = "SELECT DISTINCT site.site_id, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities,"
				+ "campground.daily_fee, campground.open_from_mm, campground.open_to_mm\n" + "FROM site\n"
				+ "JOIN campground\n" + "ON campground.campground_id = site.campground_id\n"
				+ "WHERE campground.campground_id = ? AND site.site_id NOT IN(SELECT site.site_id\n"
				+ "                                                     FROM site\n"
				+ "                                                     JOIN reservation\n"
				+ "                 ON site.site_id = reservation.site_id\n"
				+ "                 WHERE ? <= reservation.to_date \n"
				+ "                 AND ? >= reservation.from_date)\n" + "LIMIT 5;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAvailableReservations, campgroundNumber, arrivalDate,
				departureDate);
		System.out.println(
				"Site No.     Max Occupancy     Handicap Accessible     Max RV Length     Utilities     Total Cost"
						+ '\n'
						+ "--------     -------------     -------------------     -------------     ---------     ----------");
		int outputCounter = 0;
		while (results.next()) {
			Site theSite = mapRowToSite(results);
			theSites.add(theSite);
			Reservations theReservation = mapRowToReservations(results);
			theReservations.add(theReservation);
			Campground theCampground = mapRowToCampground(results);
			theCampgrounds.add(theCampground);
			if (theCampground.getOpenFromMonth() < arrivalMonth && theCampground.getOpenFromMonth() < departureMonth
					&& theCampground.getOpenToMonth() > arrivalMonth
					&& theCampground.getOpenToMonth() > departureMonth) {
				System.out.println("# " + theReservation.getSiteId() + "               " + theSite.getMaxOccupancy()
						+ "     " + theSite.getIsAccessible() + "     " + theSite.getMaxRVLength() + "     "
						+ theSite.getHasUtilities() + "     " + "$"
						+ theCampground.getDailyFee().multiply(BDDaysBetween));
				outputCounter++;
			}
		}
		if (outputCounter == 0) {
			System.out.println("No campsites available for search parameters. Please try again.");
			return;
		}
		System.out.println("Which site would you like to reserve? (Enter 0 to cancel)");
		Long desiredSite = input.nextLong();
		input.nextLine();
		if (desiredSite == 0) {
			return;
		}
		Reservations finalReservation = new Reservations();
		System.out.println("What name should the reservation be under?");
		String reserveName = input.nextLine();
		LocalDate today = LocalDate.now();
		String setNewReservation = ("INSERT INTO reservation(site_id, name, from_date, to_date, create_date)"
				+ "VALUES(   ?,     ?,      ?,        ?,       ?)");
		finalReservation.setSiteId(desiredSite);
		finalReservation.setName(reserveName);
		finalReservation.setFromDate(arrivalDate);
		finalReservation.setToDate(departureDate);
		finalReservation.setDateEntered(today);
		jdbcTemplate.update(setNewReservation, finalReservation.getSiteId(), finalReservation.getName(),
				finalReservation.getFromDate(), finalReservation.getToDate(), finalReservation.getDateEntered());
		String getNewResID = ("SELECT reservation_id FROM reservation " + "ORDER BY reservation_id DESC LIMIT 1");
		SqlRowSet reserveID = jdbcTemplate.queryForRowSet(getNewResID);
		Reservations justID = new Reservations();
		while (reserveID.next()) {
			justID = mapRowToResID(reserveID);
		}

		System.out
				.println("The Reservation has been made, your confirmation id is: " + justID.getReservationId() + '\n');
		return;

	}

	@Override
	public void displayParkWideReservations(int parkNum) {
		Date arrivalDate = null;
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String arrivalDateS = input.nextLine();
		int arrivalMonth = Integer.parseInt(arrivalDateS.substring(5, 7));
		try {
			arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(arrivalDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localArrivalDate = LocalDate.parse(arrivalDateS);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String departureDateS = input.nextLine();
		int departureMonth = Integer.parseInt(departureDateS.substring(5, 7));
		Date departureDate = null;
		try {
			departureDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localDepartureDate = LocalDate.parse(departureDateS);
		long daysBetween = ChronoUnit.DAYS.between(localArrivalDate, localDepartureDate);
		BigDecimal BDDaysBetween = new BigDecimal(daysBetween);
		System.out.println("\n" + "Results Matching Your Search Criteria");
		List<Reservations> theReservations = new ArrayList<>();
		List<Site> theSites = new ArrayList<>();
		List<Campground> theCampgrounds = new ArrayList<>();
		String getAvailableReservations = "SELECT DISTINCT campground.name, site.site_id, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities,"
				+ "campground.daily_fee, campground.open_from_mm, campground.open_to_mm \n" + "FROM site \n"
				+ "JOIN campground \n" + "ON campground.campground_id = site.campground_id \n" + "JOIN park "
				+ "ON park.park_id = campground.park_id "
				+ "WHERE park.park_id = ? AND campground.campground_id = ? AND site.site_id NOT IN(SELECT site.site_id \n"
				+ "                                                     FROM site \n"
				+ "                                                     JOIN reservation \n"
				+ "                 ON site.site_id = reservation.site_id\n"
				+ "                 WHERE ? <= reservation.to_date \n"
				+ "                 AND ? >= reservation.from_date)\n" + "LIMIT 5;";
		int campgroundCycle = 7;
		int outputCounter = 0;
		System.out.println(
				"Campground Name     Site No.     Max Occupancy     Handicap Accessible     Max RV Length     Utilities     Total Cost"
						+ '\n'
						+ "---------------     --------     -------------     -------------------     -------------     ---------     ----------");
		while (campgroundCycle > 0) {
			campgroundCycle--;
			SqlRowSet results = jdbcTemplate.queryForRowSet(getAvailableReservations, parkNum, campgroundCycle,
					arrivalDate, departureDate);

			while (results.next() && campgroundCycle > 0) {

				Campground campgroundName = mapRowToCampgroundName(results);
				Site theSite = mapRowToSite(results);
				theSites.add(theSite);
				Reservations theReservation = mapRowToReservations(results);
				theReservations.add(theReservation);
				Campground theCampground = mapRowToCampground(results);
				theCampgrounds.add(theCampground);
				if (theCampground.getOpenFromMonth() < arrivalMonth && theCampground.getOpenFromMonth() < departureMonth
						&& theCampground.getOpenToMonth() > arrivalMonth
						&& theCampground.getOpenToMonth() > departureMonth) {
					System.out.println(campgroundName.getName() + " # " + theReservation.getSiteId() + "               "
							+ theSite.getMaxOccupancy() + "     " + theSite.getIsAccessible() + "     "
							+ theSite.getMaxRVLength() + "     " + theSite.getHasUtilities() + "     " + "$"
							+ theCampground.getDailyFee().multiply(BDDaysBetween));
					outputCounter++;
				}
			}
		}

		if (outputCounter == 0) {
			System.out.println("No campsites available for search parameters. Please try again.");
			return;
		}
		System.out.println("Which site would you like to reserve? (Enter 0 to cancel)");
		Long desiredSite = input.nextLong();
		input.nextLine();
		if (desiredSite == 0) {
			return;
		}
		Reservations finalReservation = new Reservations();
		System.out.println("What name should the reservation be under?");
		String reserveName = input.nextLine();
		LocalDate today = LocalDate.now();
		String setNewReservation = ("INSERT INTO reservation(site_id, name, from_date, to_date, create_date)"
				+ "VALUES(   ?,     ?,      ?,        ?,       ?)");
		finalReservation.setSiteId(desiredSite);
		finalReservation.setName(reserveName);
		finalReservation.setFromDate(arrivalDate);
		finalReservation.setToDate(departureDate);
		finalReservation.setDateEntered(today);
		jdbcTemplate.update(setNewReservation, finalReservation.getSiteId(), finalReservation.getName(),
				finalReservation.getFromDate(), finalReservation.getToDate(), finalReservation.getDateEntered());
		String getNewResID = ("SELECT reservation_id FROM reservation " + "ORDER BY reservation_id DESC LIMIT 1");
		SqlRowSet reserveID = jdbcTemplate.queryForRowSet(getNewResID);
		Reservations justID = new Reservations();
		while (reserveID.next()) {
			justID = mapRowToResID(reserveID);
		}

		System.out
				.println("The Reservation has been made, your confirmation id is: " + justID.getReservationId() + '\n');
		return;

	}

	public void advancedSearchForReservations() {
		Scanner input = new Scanner(System.in);
		System.out.println("Which campground (enter 0 to cancel)? _");
		long campgroundNumber = input.nextLong();
		input.nextLine();
		if (campgroundNumber == 0) {
			return;
		}
		Date arrivalDate = null;
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String arrivalDateS = input.nextLine();
		int arrivalMonth = Integer.parseInt(arrivalDateS.substring(5, 7));
		try {
			arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(arrivalDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localArrivalDate = LocalDate.parse(arrivalDateS);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String departureDateS = input.nextLine();
		int departureMonth = Integer.parseInt(departureDateS.substring(5, 7));
		Date departureDate = null;
		try {
			departureDate = new SimpleDateFormat("yyyy-MM-dd").parse(departureDateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LocalDate localDepartureDate = LocalDate.parse(departureDateS);
		long daysBetween = ChronoUnit.DAYS.between(localArrivalDate, localDepartureDate);
		BigDecimal BDDaysBetween = new BigDecimal(daysBetween);
		System.out.println("How many people are you traveling with?");
		int numGuests = input.nextInt();
		input.nextLine();
		System.out.println("Do you require wheelchair access? (y/n)");
		String access = input.nextLine().toLowerCase();
		boolean wheelchairAccess = false;
		if (access.charAt(0) == 'y') {
			wheelchairAccess = true;
		}
		System.out.println("Are you bringing an RV? (y/n)");
		String rV = input.nextLine().toLowerCase();
		int rVLength = 0;
		if (rV.charAt(0) == 'y') {
			System.out.println("How long is your RV?");
			rVLength = input.nextInt();
			input.nextLine();
		}
		System.out.println("Do you require utility hookup? (y/n)");
		String hookup = input.nextLine().toLowerCase();
		boolean utilityHookup = false;
		if (hookup.charAt(0) == 'y') {
			utilityHookup = true;
		}
		System.out.println("\n" + "Results Matching Your Search Criteria");
		List<Reservations> theReservations = new ArrayList<>();
		List<Site> theSites = new ArrayList<>();
		List<Campground> theCampgrounds = new ArrayList<>();
		String getAvailableReservations = "SELECT DISTINCT site.site_id, site.max_occupancy, site.accessible, site.max_rv_length, site.utilities, campground.daily_fee, campground.open_from_mm, campground.open_to_mm\n"
				+ "FROM site\n" + "JOIN campground\n" + "ON campground.campground_id = site.campground_id\n"
				+ "WHERE campground.campground_id = ? \n" + "AND site.max_occupancy >= ?\n"
				+ "AND site.accessible = ?\n" + "AND site.max_rv_length >= ?\n" + "AND site.utilities = ?\n"
				+ "AND site.site_id NOT IN(SELECT site.site_id\n"
				+ "                                                     FROM site\n"
				+ "                                                     JOIN reservation\n"
				+ "                                                     ON site.site_id = reservation.site_id\n"
				+ "                                                     WHERE ? <= reservation.to_date\n"
				+ "                                                     AND ? >= reservation.from_date)\n"
				+ "LIMIT 5;\n" + "";
		SqlRowSet results = jdbcTemplate.queryForRowSet(getAvailableReservations, campgroundNumber, numGuests,
				wheelchairAccess, rVLength, utilityHookup, arrivalDate, departureDate);
		System.out.printf(String.format("%-20s", "Site No.") + String.format("%-20s", "Max Occupancy")
				+ String.format("%-20s", "Handicap Accessible") + String.format("%-20s", "Max RV Length")
				+ String.format("%-20s", "Utilities") + String.format("%-20s", "Total Cost"));
		System.out.println(" ");
		int outputCounter = 0;
		while (results.next()) {
			Site theSite = mapRowToSite(results);
			theSites.add(theSite);
			Reservations theReservation = mapRowToReservations(results);
			theReservations.add(theReservation);
			Campground theCampground = mapRowToCampground(results);
			theCampgrounds.add(theCampground);
			if (theCampground.getOpenFromMonth() < arrivalMonth && theCampground.getOpenFromMonth() < departureMonth
					&& theCampground.getOpenToMonth() > arrivalMonth
					&& theCampground.getOpenToMonth() > departureMonth) {
				System.out.printf("# " + String.format("%-20s", theSite.getSiteId())
						+ String.format("%-20s", theSite.getMaxOccupancy())
						+ String.format("%-20s", theSite.getIsAccessible())
						+ String.format("%-20s", theSite.getMaxRVLength())
						+ String.format("%-20s", theSite.getHasUtilities()) + "$"
						+ String.format("%-20s", theCampground.getDailyFee().multiply(BDDaysBetween)));
				System.out.println("");
				outputCounter++;
			}
		}
		if (outputCounter == 0) {
			System.out.println("No campsites available for search parameters. Please try again.");
			return;
		}
		System.out.println("Which site would you like to reserve? (Enter 0 to cancel)");
		Long desiredSite = input.nextLong();
		input.nextLine();
		if (desiredSite == 0) {
			return;
		}
		Reservations finalReservation = new Reservations();
		System.out.println("What name should the reservation be under?");
		String reserveName = input.nextLine();
		LocalDate today = LocalDate.now();
		String setNewReservation = ("INSERT INTO reservation(site_id, name, from_date, to_date, create_date)"
				+ "VALUES(   ?,     ?,      ?,        ?,       ?)");
		finalReservation.setSiteId(desiredSite);
		finalReservation.setName(reserveName);
		finalReservation.setFromDate(arrivalDate);
		finalReservation.setToDate(departureDate);
		finalReservation.setDateEntered(today);
		jdbcTemplate.update(setNewReservation, finalReservation.getSiteId(), finalReservation.getName(),
				finalReservation.getFromDate(), finalReservation.getToDate(), finalReservation.getDateEntered());
		String getNewResID = ("SELECT reservation_id FROM reservation " + "ORDER BY reservation_id DESC LIMIT 1");
		SqlRowSet reserveID = jdbcTemplate.queryForRowSet(getNewResID);
		Reservations justID = new Reservations();
		while (reserveID.next()) {
			justID = mapRowToResID(reserveID);
		}

		System.out
				.println("The Reservation has been made, your confirmation id is: " + justID.getReservationId() + '\n');
		return;
	}

	public void nextThirtyDays(int parkNum) {
		List<Reservations> theReservations = new ArrayList<>();
		String nextThirtyReserves = ("SELECT r.reservation_id, r.from_date, r.to_date\n" + 
				"FROM reservation r\n" + 
				"JOIN site s\n" + 
				"ON s.site_id = r.site_id\n" + 
				"JOIN campground cg\n" + 
				"ON cg.campground_id = s.campground_id\n" + 
				"JOIN park p\n" + 
				"ON cg.park_id = p.park_id\n" + 
				"WHERE to_date <= current_date + 30 AND p.park_id = ?;");
		SqlRowSet results = jdbcTemplate.queryForRowSet(nextThirtyReserves, parkNum);
		System.out.printf("Reservation ID" + "From Date" + "To Date" + '\n' +
				          "--------------" + "---------" + "-------");
		
		while(results.next()) {
			Reservations theReservation = new Reservations();
			theReservation = mapRowToNextThirty(results);
			theReservations.add(theReservation);
			System.out.println("# " + theReservation.getReservationId() + theReservation.getFromDate() + theReservation.getToDate());
		}

	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));
		theCampground.setOpenFromMonth(Integer.parseInt(results.getString("open_from_mm")));
		theCampground.setOpenToMonth(Integer.parseInt(results.getString("open_to_mm")));
		return theCampground;
	}

	private Campground mapRowToCampgroundName(SqlRowSet results) {
		Campground theCampground = new Campground();
		theCampground.setName(results.getString("name"));
		return theCampground;
	}

	private Reservations mapRowToReservations(SqlRowSet results) {
		Reservations theReservations = new Reservations();
		theReservations.setSiteId(results.getLong("site_id"));

		return theReservations;
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site theSite = new Site();
		theSite.setSiteId(results.getLong("site_id"));
		theSite.setMaxOccupancy(results.getLong("max_occupancy"));
		theSite.setIsAccessible(results.getBoolean("accessible"));
		theSite.setMaxRVLength(results.getInt("max_rv_length"));
		theSite.setHasUtilities(results.getBoolean("utilities"));
		return theSite;
	}

	private Reservations mapRowToResID(SqlRowSet results) {
		Reservations resID = new Reservations();
		resID.setReservationId(results.getLong("reservation_id"));
		return resID;

	}
	private Reservations mapRowToNextThirty(SqlRowSet results) {
		Reservations nextThirty = new Reservations();
		nextThirty.setReservationId(results.getLong("reservation_id"));
		nextThirty.setFromDate(results.getDate("from_date"));
		nextThirty.setToDate(results.getDate("to_date"));
		return nextThirty;

	}
}
