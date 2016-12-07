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