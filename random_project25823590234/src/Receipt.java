
/**
 * @author Brogrammers
 * 
 * Interface representing a receipt for a transaction made using the hotel system. (Strategy)
 * Context is GUI pop up.
 *
 */
public interface Receipt 
{
	
	/**
	 * Method for displaying the receipt.
	 * 
	 * @param account Specified account
	 * @return Receipt as string
	 */
	public String showReceipt(Account account);
	
}
