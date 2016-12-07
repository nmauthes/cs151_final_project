import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
	private Date checkInDate, checkOutDate;
	private SimpleDateFormat sdf;  // for parsing Dates from Strings
	private String roomType; // room 0-9 luxurious and 10-19 economic?
	private int roomNumber; // goes from 0 to 19 because CS
	
	// TODO add get and set methods
	
	public Date getCheckInDate() { return checkInDate; }
	public Date getCheckOutDate() { return checkOutDate; }
	public int getRoomNumber() { return roomNumber; }
	/*
	 * Returns the actual representation of the room, Ex room 0 --> room 1 in real life
	 */
	public String getRealRoomNumber()
	{
		int realRoom = this.getRoomNumber() + 1;
		return Integer.toString(realRoom);
	}
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, int roomNumber) throws Exception {
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		this.checkInDate = sdf.parse(checkInDate);
		this.checkOutDate = sdf.parse(checkOutDate);
		this.roomType = roomType;
		this.roomNumber = roomNumber;
	}
	
	public boolean checkConflict(Reservation other) {
		//check to see if there is a date conflict
		if(roomNumber == other.getRoomNumber() && ((checkInDate.compareTo(other.getCheckOutDate()) < 0 && checkOutDate.compareTo(other.getCheckOutDate()) > 0)
				|| (checkOutDate.compareTo(other.getCheckInDate()) > 0  && checkInDate.compareTo(other.getCheckInDate()) < 0))) {
			return true;
		}
		else
			return false;
	}
	
	public String toString() {
		return null; // TODO
	}
}