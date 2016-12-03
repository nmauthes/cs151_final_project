import java.util.Collections;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.swing.event.*;

/**
 * @author Nick Mauthes
 * 
 * Handles all calendar functions including loading, saving, adding and removing events,
 * and getting events and dates.
 *
 */
public class MyCalendar {
	Calendar model;
	Date currentDate;
	Date selectedDate;

	public MyCalendar() {
		initCalendar();
	}
	
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
		model.set(parsed[2], parsed[0], parsed[1]);
		
		selectedDate = model.getTime();
	}
	
	
	/**
	 * Sets the calendar to the specified date.
	 * 
	 * @param month Month to go to
	 * @param day Day to go to
	 * @param year Year to go to
	 */
	public void goToDate(int month, int day, int year) {
		model.set(year, month, day);
		
		selectedDate = model.getTime();
	}
	
	/**
	 * Moves the calendar forward by one day.
	 */
	public void nextDay() {
		model.add(Calendar.DATE, 1);
		selectedDate = model.getTime();
	}
	
	/**
	 * Moves the calendar backward by one day.
	 */
	public void previousDay() {
		model.add(Calendar.DATE, -1);
		selectedDate = model.getTime();
	}
	
	/**
	 * Moves the calendar forward by one month.
	 */
	public void nextMonth() {
		model.add(Calendar.MONTH, 1);
		selectedDate = model.getTime();
	}
	
	/**
	 * Moves the calendar backward by one month.
	 */
	public void previousMonth() {
		model.add(Calendar.MONTH, -1);
		selectedDate = model.getTime();
	}
	
	/**
	 * Initializes the calendar to the current day.
	 */
	void initCalendar() {
		model = Calendar.getInstance();
	
		model.set(Calendar.HOUR_OF_DAY, 0);
		model.set(Calendar.MINUTE, 0);
		model.set(Calendar.SECOND, 0);
		model.set(Calendar.MILLISECOND, 0);
		
		currentDate = model.getTime();
		selectedDate = currentDate;
	}
	
	/**
	 * Parses a String of the form MM/DD/YYYY into a format that can be used by the calendar.
	 * 
	 * @param date Date as a String
	 * @return An int array representing the numeric date
	 */
	int[] parseDate(String date) {
		String[] temp = date.split("/");
		int[] parsed = new int[temp.length];
		
		parsed[0] = Integer.parseInt(temp[0]) - 1;
		parsed[1] = Integer.parseInt(temp[1]);
		parsed[2] = Integer.parseInt(temp[2]);
			
		return parsed;
	}
}
