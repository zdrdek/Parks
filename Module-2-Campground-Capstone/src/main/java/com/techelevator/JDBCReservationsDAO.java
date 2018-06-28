package com.techelevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationsDAO implements ReservationsDAO {
	
	public void searchForReservations() {
		Scanner input = new Scanner(System.in);
		System.out.println("Which campground (enter 0 to cancel)? _");
		String campgroundNumber = input.nextLine();
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String arrivalDate = input.nextLine();
		System.out.println("What is the departure date? yyyy-mm-dd");
		String departureDate = input.nextLine();
		System.out.println("\n" + "Results Matching Your Search Criteria");
		List<Reservations> theReservations = new ArrayList<>();
		String getAvailableReservations = ""
		
		
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
		theReservations.setDateEntered(results.getDate("create_date"));
		return theReservations;
	}

}
