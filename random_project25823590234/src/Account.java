import java.util.ArrayList;

public class Account {
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
	
	public String toString() {
		return "Name: " + name + " ID: " + id; // TODO add more info
	}
	
}