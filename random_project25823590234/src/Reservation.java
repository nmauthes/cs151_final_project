import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * @author Brogrammers
 * 
 * Represents a reservation made at the hotel. Provides fields for the check in and check out dates,
 * as well as room number and room type.
 *
 */
public class Reservation implements java.io.Serializable {
	private Date checkInDate, checkOutDate;
	private SimpleDateFormat sdf;  // for parsing Dates from Strings
	private String roomType; // L denotes Luxury room, E denotes economic room
	private int roomNumber; // goes from 0 to 19 because CS
	private Account reservingAccount;
	
	public Date getCheckInDate() { return checkInDate; }
	public Date getCheckOutDate() { return checkOutDate; }
	public int getRoomNumber() { return roomNumber; }
	public String getRoomType(){return roomType;}
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, int roomNumber) throws Exception {
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		this.checkInDate = sdf.parse(checkInDate);
		this.checkOutDate = sdf.parse(checkOutDate);
		this.roomType = roomType;
		this.roomNumber = --roomNumber; // for proper indexing
	}
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, int roomNumber, Account reservingAccount) throws Exception {
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		this.checkInDate = sdf.parse(checkInDate);
		this.checkOutDate = sdf.parse(checkOutDate);
		this.roomType = roomType;
		this.roomNumber = --roomNumber; // for proper indexing
		this.reservingAccount = reservingAccount;
	}
	
	/**
	 * Returns the "actual" number of the room as a String.
	 * 
	 * @return "Actual" room number
	 */
	public String getRealRoomNumber()
	{
		int realRoom = this.getRoomNumber() + 1;
		return Integer.toString(realRoom);
	}
	
	/**
	 * Checks to see if a conflict exists between the reservation and the specified
	 * check in and check out date.
	 * 
	 * @param otherCheckIn Specified check in date
	 * @param otherCheckOut Specified check out date
	 * @return True if conflict, false otherwise
	 */
	public boolean checkConflict(Date otherCheckIn, Date otherCheckOut) {
		//check to see if there is a date conflict
		if((checkInDate.compareTo(otherCheckOut) < 0 && checkOutDate.compareTo(otherCheckOut) > 0)
				|| (checkOutDate.compareTo(otherCheckIn) > 0  && checkInDate.compareTo(otherCheckIn) < 0)) {
			return true; // true if conflict
		}
		else
			return false;
	}
	
	public String toString() {
		String checkIn = sdf.format(checkInDate);
		String checkOut = sdf.format(checkOutDate);
		
		return "| Check In Date: " + checkIn + " | Check Out Date: " + checkOut + " | Room Number: " + getRealRoomNumber() + " |";
	}
}