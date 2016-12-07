import java.util.ArrayList;

public class Account {
	private int id;
	private String name;
	private ArrayList<Reservation> reservations;
	
	public void setName(String name) { this.name = name; }
	public int getID() { return id; }
	public String getName() { return name; }
	public ArrayList<Reservation> getReservations() { return reservations; }
	
	public Account(String name) {
		setName(name);
		reservations = new ArrayList<>();
	}
	/*
	 * Fetches the real room numbers of all rooms reserved and returns a String representation
	 */
	public String getRoomsReserved()
	{
		String roomsList = "";
		for (int i = 0; i < this.reservations.size();i++)
		{
			roomsList = roomsList + reservations.get(i).getRealRoomNumber() + ", ";
		}
		
		return roomsList;
		
	}
	public void generateID() {
		// TODO
	}
	
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
	
	public String toString() {
		return "Name: " + name; // TODO add more info
	}
	
}