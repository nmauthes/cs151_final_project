import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReservationSystem { // model
	public final int NUMBER_OF_ROOMS = 20;
	
	private ArrayList<Account> accounts;
	private ArrayList<ChangeListener> listeners;
	
	public ArrayList<Account> getAccounts() { return accounts; }
	public Calendar getCalendar() { return calendar; }
	public void addListener(ChangeListener cl) { listeners.add(cl); }
	
	private Calendar calendar;
	private Date currentDate;
	private Date selectedDate;
	
	public ReservationSystem() throws Exception {
		//TODO
		
		accounts = new ArrayList<>();
		listeners = new ArrayList<>();
		initCalendar();
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
//	public int[] getAvailableRooms(String checkinDate, String checkoutDate) {
//		for(Account a : accounts) {
//			for(Reservation r : a.getReservations()) {
//				
//			}
//		}
//	}
	
	// calendar functions
	
	/**
	 * Sets the calendar to the current date.
	 */
	public void goToCurrent() {
		initCalendar();
	}
	
	/**
	 * Sets the calendar to the specified date.
	 * 
	 * @param date The date to go to
	 */
	public void goToDate(Date date) {
		calendar.setTime(date);
		
		selectedDate = calendar.getTime();
	}
	
	/**
	 * Sets the calendar to the specified date.
	 * 
	 * @param date The date to go to
	 */
	public void goToDate(String date) {
		int[] parsed = parseDate(date);
		calendar.set(parsed[2], parsed[0], parsed[1]);
		
		selectedDate = calendar.getTime();
	}
	
	
	/**
	 * Sets the calendar to the specified date.
	 * 
	 * @param month Month to go to
	 * @param day Day to go to
	 * @param year Year to go to
	 */
	public void goToDate(int month, int day, int year) {
		calendar.set(year, month, day);
		
		selectedDate = calendar.getTime();
	}
	
	/**
	 * Moves the calendar forward by one month.
	 */
	public void nextMonth() {
		calendar.add(Calendar.MONTH, 1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	
	/**
	 * Moves the calendar backward by one month.
	 */
	public void previousMonth() {
		calendar.add(Calendar.MONTH, -1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	
	public void nextYear() {
		calendar.add(Calendar.YEAR, 1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	
	public void previousYear() {
		calendar.add(Calendar.YEAR, -1);
		selectedDate = calendar.getTime();
		changeMade();
	}
	
//	public boolean checkConflict(String start1, String end1, String start2, String end2) {
//		Calendar temp = Calendar.getInstance();
//		
//		int[] start1Parsed = parseDate(start1);
//		int[] end1Parsed = parseDate(end1);
//		int[] start2Parsed = parseDate(start2);
//		int[] end2Parsed = parseDate(end2);
//	}
	
	public int getDaysBetween(String d1, String d2) {
		Calendar temp = Calendar.getInstance();
		
		int[] d1Parsed = parseDate(d1);
		int[] d2Parsed = parseDate(d2);
		
		temp.set(d1Parsed[2], d1Parsed[0], d1Parsed[1]);
		Date date1 = temp.getTime();
		temp.set(d2Parsed[2], d2Parsed[0], d2Parsed[1]);
		Date date2 = temp.getTime();
	
		return (int) TimeUnit.MILLISECONDS.toDays(Math.abs(date2.getTime() - date1.getTime()));
	}
	
	/**
	 * Parses a String of the form MM/DD/YYYY into a format that can be used by the calendar.
	 * 
	 * @param date Date as a String
	 * @return An int array representing the numeric date
	 */
	public static int[] parseDate(String date) {
		String[] temp = date.split("/");
		int[] parsed = new int[temp.length];
		
		parsed[0] = Integer.parseInt(temp[0]) - 1;
		parsed[1] = Integer.parseInt(temp[1]);
		parsed[2] = Integer.parseInt(temp[2]);
			
		return parsed;
	}
	
	private void changeMade() {
		ChangeEvent e = new ChangeEvent(this);
		
		for(ChangeListener cl : listeners) {
			cl.stateChanged(e);
		}
	}
	
	/**
	 * Initializes the calendar to the current day.
	 */
	private void initCalendar() {
		calendar = Calendar.getInstance();
	
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		currentDate = calendar.getTime();
		selectedDate = currentDate;
	}
	
	//TODO load function
	//TODO save function
	
}