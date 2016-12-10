import java.util.ArrayList;

/**
 * @author Brogrammers
 *
 * Represents a user account for the hotel system. Provides fields for name and generates an ID number.
 * Also contains a list of all the reservations made by the account.
 *
 */
public class Account implements java.io.Serializable {
	private static int nextID = 0;							
	private int id;
	private String name;
	private ArrayList<Reservation> reservations;
	
	public void setName(String name) 				{ this.name = name; 	}
	public int getID() 								{ return id; 			}
	public String getName() 						{ return name; 			}
	public ArrayList<Reservation> getReservations() { return reservations;  }
	
	public Account(String name) {
		setName(name);
		reservations = new ArrayList<>();
		generateID();
	}
	
	/**
	 * Fetches the real room numbers of all rooms reserved and returns a String representation
	 * @return the list of rooms reserved
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
	
	/**
	 * Generates an ID for the account
	 */
	public void generateID() {
		id = nextID;
		nextID++;
	}
	
	/**
	 * Adds a new reservation the list of reservations.
	 * 
	 * @param r The new reservation
	 */
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
	
	/**
	 * Removes a reservation from the list of reservations.
	 * 
	 * @param r The reservation to remove.
	 */
	public void removeReservation(Reservation r) {
		reservations.remove(r);
	}
	
	/**
	 * Removes reservations from list of reservations at indexes in removeIndices
	 * 
	 * @param int[] removeIndices Holds indices to remove from
	 */
	public void removeReservations(int[] removeIndices) {
		
		ArrayList<Reservation> cancelThese = new ArrayList<Reservation>();
		
		for(int i = 0; i < removeIndices.length; i++) {
			cancelThese.add(reservations.get(removeIndices[i]));
		}
		
		for(Reservation r : cancelThese) {
			reservations.remove(r);
		}
	}
	
	/**
	 * Calculates the total amount owed by iterating thru the reservations arrayList to check for room type and charging by type
	 * @return the total balance
	 */
	public int getTotalBalance()
	{
		int totalBalance = 0;
		for (Reservation reservation : this.reservations) // iterates through the entire reservations arraylist
		{
			if (reservation.getRoomType() == "L") // Checks if the room type is of type Luxury
			{
				totalBalance =+ 200;
			}
			if (reservation.getRoomType() == "E") // Checks if the room type is of type Economic
			{
				totalBalance =+ 80;
			}
			
		}
		return totalBalance;
	}
	
	public String toString() {
		return "| Name: " + name + " | ID: " + id + " |";
	}	
}