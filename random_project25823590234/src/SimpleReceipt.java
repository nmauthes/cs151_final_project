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
			ArrayList<String> reservedRoomList = new ArrayList<String>();
			
			return "Simple Receipt: \n User: " + account.getName() + ", ID: " + account.getID() + ", List of reserved rooms: " + account.getRoomsReserved() + ", Total Due for this Transaction: $" + account.getTotalBalance();
	
	}
}
