import java.util.ArrayList;

public class Account implements java.io.Serializable {
	private static int nextID = 0;								//used to generate ID
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
	
	//ID's will be assigned 0,1,2,3 and so on. This will be convenient bc an account's id will
	//also correspond to the index used to access it in the accounts arrayList
	public void generateID() {
		id = nextID;
		nextID++;
	}
	
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
	
	public void removeReservation(Reservation r) {
		reservations.remove(r);
	}
	
	public void removeReservations(int[] removeIndices) {
		int j = 0;
		
		for(int i = 0; i < reservations.size(); i++) {
			if( i == j) {
				reservations.remove(i);
				j++;
			}
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
	
	//for testing purposes
	public void printReservations() {
		for(Reservation r : reservations) {
			System.out.println(r);
		}
	}
	
}