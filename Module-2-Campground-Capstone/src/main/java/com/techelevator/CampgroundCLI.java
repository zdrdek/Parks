package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTIONS_ACADIA = "Acadia";
	private static final String MAIN_MENU_OPTIONS_ARCHES = "Arches";
	private static final String MAIN_MENU_OPTIONS_CNVP = "Cuyahoga National Valley Park";
	private static final String MAIN_MENU_OPTIONS_EXIT = "Exit";
	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";
	

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		// create your DAOs here
	}
	
	public void run() {
		
	}
}
