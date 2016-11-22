import java.util.ArrayList;

public class Account {
	private int id;
	private String name;
	private ArrayList<Reservation> reservations;
	
	public void setID(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public int getID() { return id; }
	public String getName() { return name; }
	
	public Account(String name, int id) {
		setName(name);
		setID(id);
	}
	
	public void addReservation(Reservation r) {
		reservations.add(r);
	}
}
