
public class SimpleReceipt implements Receipt 
{
	public String showReceipt(Account account)
	{
			return "User: " + account.getName() + ", ID: " + account.getID() + account.getReservations();
			// TO DO implement method to return all reservered rooms (NOT RESERVATIONS) by User and corresponding amount due
	
	}
}
