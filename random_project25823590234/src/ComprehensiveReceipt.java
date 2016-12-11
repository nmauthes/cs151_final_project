import java.util.ArrayList;
/**
 * @author Brogrammers
 * 
 * Concrete strategy which creates a receipt that includes name, id, all reservations and total cost
 *
 */
public class ComprehensiveReceipt implements Receipt 
{
	public String showReceipt(Account account)
	{
			ArrayList<Reservation> reservations = account.getReservations();
			String list = "";
			
			for(Reservation r : reservations) {
				list += r + "\n";
			}
			
			return "Comprehensive Receipt: \n\nUser: " + account.getName() + "\nID: " + account.getID() + "\nReservations:\n" + list + "\nTotal Amount Due: $" + account.getTotalBalance();
	}
}
