package com.techelevator;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.JDBCParkDAO;


public class CampgroundCLI {
	
	private static final String MAIN_MENU_OPTIONS_ACADIA = "Acadia";
	private static final String MAIN_MENU_OPTIONS_ARCHES = "Arches";
	private static final String MAIN_MENU_OPTIONS_CNVP = "Cuyahoga National Valley Park";
	private static final String MAIN_MENU_OPTIONS_EXIT = "Exit";
	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";
	private static final String[] PARKS_ALL_MENU_OPTIONS = new String[] {MAIN_MENU_OPTIONS_ACADIA, 
																			 MAIN_MENU_OPTIONS_ARCHES,
																			 MAIN_MENU_OPTIONS_CNVP,
																			 MENU_OPTION_RETURN_TO_MAIN};
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private ReservationsDAO reservationsDAO;
	private SiteDAO siteDAO;
	

	public static void main(String[] args) {
		
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
//		parkDAO = new JDBCParkDAO(dataSource);
		
	}
	
	public void run() {
		while(true) {
			System.out.println("Main Menu" + '\n' + 
					           "---------" + '\n' +'\n' +
					           "Select a Park for further details:");
			String choice = (String)menu.getChoiceFromOptions(PARKS_ALL_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTIONS_ACADIA)) {
				
			}
			if(choice.equals(MAIN_MENU_OPTIONS_ARCHES)) {
				
			}
			if(choice.equals(MAIN_MENU_OPTIONS_CNVP)) {
				
			}
			if(choice.equals(MAIN_MENU_OPTIONS_EXIT)) {
				
			}
		}
	}
}
