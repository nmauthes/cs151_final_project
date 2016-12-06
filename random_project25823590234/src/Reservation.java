import java.util.Date;
import java.text.SimpleDateFormat;

public class Reservation {
	private Date checkInDate, checkOutDate;
	private SimpleDateFormat sdf;  // for parsing Dates from Strings
	private String roomType;
	private int roomNumber;
	
	// TODO add get and set methods
	
	public Date getCheckInDate() { return checkInDate; }
	public Date getCheckOutDate() { return checkOutDate; }
	public int getRoomNumber() { return roomNumber; }
	
	public Reservation(String checkInDate, String checkOutDate, String roomType, int roomNumber) throws Exception {
		sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		this.checkInDate = sdf.parse(checkInDate);
		this.checkOutDate = sdf.parse(checkOutDate);
		this.roomType = roomType;
		this.roomNumber = roomNumber;
	}
	
	public boolean checkConflict(Reservation other) {
		//check to see if there is a date conflict
		if(roomNumber == other.getRoomNumber() && ((checkInDate.getTime() - getCheckOutDate().getTime() < 0 && checkOutDate.getTime() - other.getCheckOutDate().getTime() > 0)
				|| (checkOutDate.getTime() - other.getCheckInDate().getTime() > 0  && checkInDate.getTime() - other.getCheckInDate().getTime() < 0))) {
			return true;
		}
		else
			return false;
	}
	
	public String toString() {
		return null; // TODO
	}
}