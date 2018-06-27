package com.techelevator;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCReservationsDAO implements ReservationsDAO {
	
	public void searchForReservations() {
		
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
