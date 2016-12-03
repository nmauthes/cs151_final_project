import javax.swing.event.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ReservationSystem { // model
	private ArrayList<Account> accounts;
	private ArrayList<ChangeListener> listeners;
	private MyCalendar calendar;
	
	public ArrayList<Account> getAccounts() { return accounts; }
	public MyCalendar getCalendar() { return calendar; }
	public void addListener(ChangeListener cl) { listeners.add(cl); }
	
	public ReservationSystem() {
		//TODO
		
		listeners = new ArrayList<>();
		calendar = new MyCalendar();
	}
	
	public void addAccount(Account account) {
		// TODO
	}
	
	//TODO load function
	//TODO save function
	
}