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
																			 MAIN_MENU_OPTIONS_EXIT};
	private static final String PARK_INFO_OPTIONS_VIEW = "View Campgrounds";
	private static final String PARK_INFO_OPTIONS_SEARCH = "Search for Reservations";
	private static final String PARK_INFO_OPTIONS_RETURN = "Return to Previous Screen";
	private static final String[] INFO_ALL_MENU_OPTIONS = new String[] {PARK_INFO_OPTIONS_VIEW,
																			PARK_INFO_OPTIONS_SEARCH,
																			PARK_INFO_OPTIONS_RETURN};
	private static final String CAMPGROUND_OPTIONS_SEARCH = "Search for Available Reservation";
	private static final String CAMPGROUND_OPTIONS_RETURN = "Return to Previous Screen";
	private static final String[] CAMPGROUND_ALL_MENU_OPTIONS = new String[] {CAMPGROUND_OPTIONS_SEARCH,
																				CAMPGROUND_OPTIONS_RETURN};
	
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
		
		parkDAO = new JDBCParkDAO(dataSource);
		
	}
	
	public void run() {
		while(true) {
			System.out.println("Main Menu" + '\n' + 
					           "---------" + '\n' +'\n' +
					           "Select a Park for further details:");
			String choice = (String)menu.getChoiceFromOptions(PARKS_ALL_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTIONS_ACADIA)) {
				//INSERT DISPLAY METHOD WITH ACADIA PARAMETER
					parkDAO.getParkInfo(1);
			}
			if(choice.equals(MAIN_MENU_OPTIONS_ARCHES)) {
				//INSERT DISPLAY METHOD WITH ARCHES PARAMETER
				parkDAO.getParkInfo(2);
			}
			if(choice.equals(MAIN_MENU_OPTIONS_CNVP)) {
				//INSERT DISPLAY METHOD WITH CNVP PARAMETER
				parkDAO.getParkInfo(3);
			}
			if(choice.equals(MAIN_MENU_OPTIONS_EXIT)) {
				break;	//ENDS PROGRAM
			}
			while (true) {
				System.out.println("\n" + "Select a Command");
				choice = (String)menu.getChoiceFromOptions(INFO_ALL_MENU_OPTIONS);
				if (choice.equals(PARK_INFO_OPTIONS_VIEW)) {
					while (true) {
						//INSERT DISPLAY FOR CAMPGROUNDS
						System.out.println("\n" + "Select a Command");
						choice = (String)menu.getChoiceFromOptions(CAMPGROUND_ALL_MENU_OPTIONS);
						if (choice.equals(CAMPGROUND_OPTIONS_SEARCH)) {
							//move into search for campground reservation
						}
						if (choice.equals(CAMPGROUND_OPTIONS_RETURN)) {
							break;
						}
					}
					continue;
				}
				if (choice.equals(PARK_INFO_OPTIONS_SEARCH)) {
					//move into search for campground reservation
				}
				if (choice.equals(PARK_INFO_OPTIONS_RETURN)) {
					break;
				}
			}
		}
	}
}
