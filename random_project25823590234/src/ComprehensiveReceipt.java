
public class ComprehensiveReceipt implements Receipt 
{
	public String showReceipt(Account account)
	{
			
			return "User: " + account.getName() + ", ID: " + account.getID() + ", Reservations: " + account.getReservations();
			//TO DO implement amount due
	}
}
