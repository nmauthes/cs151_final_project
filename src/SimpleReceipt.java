import java.util.ArrayList;
/**
 * @author Brogrammers
 * 
 * Concrete strategy which creates a receipt showing name, id, reserved rooms and total for the transaction.
 *
 */
public class SimpleReceipt implements Receipt 
{
	public String showReceipt(Account account)
	{	
		return "Simple Receipt:\nUser: " + account.getName() + "\nID: " + account.getID() + "\nList of reserved rooms:\n" + account.getRoomsReserved() + "\nTotal Due for this Transaction: $" + account.getTotalBalance();
	
	}
}
