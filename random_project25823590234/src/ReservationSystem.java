import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReservationSystem { // model
	private ArrayList<Account> accounts;
	private ArrayList<ChangeListener> listeners;
	
	public ArrayList<Account> getAccounts() { return accounts; }
	public Calendar getCalendar() { return calendar; }
	public void addListener(ChangeListener cl) { listeners.add(cl); }
	
	private Calendar calendar;
	private Date currentDate;
	private Date selectedDate;
	
	public ReservationSystem() {
		//TODO
		
		listeners = new ArrayList<>();
		initCalendar();
	}
	
	public void addAccount(Account account) {
		// TODO
	}
	
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
	
	/**
	 * Parses a String of the form MM/DD/YYYY into a format that can be used by the calendar.
	 * 
	 * @param date Date as a String
	 * @return An int array representing the numeric date
	 */
	private int[] parseDate(String date) {
		String[] temp = date.split("/");
		int[] parsed = new int[temp.length];
		
		parsed[0] = Integer.parseInt(temp[0]) - 1;
		parsed[1] = Integer.parseInt(temp[1]);
		parsed[2] = Integer.parseInt(temp[2]);
			
		return parsed;
	}
	
	//TODO load function
	//TODO save function
	
}