
public class ReceiptContext 
{
	private Receipt receipt;
	
	public ReceiptContext(Receipt receipt)
	{
		this.receipt = receipt;
	}
	/**
	 * Executes the strategy of either simple or comprehensive Receipt
	 * @param id
	 * @param name
	 * @param account
	 */
	public void executeStrategy(int id, String name, Account account)
	{
		receipt.showReceipt(id, name, account);
	}
	
}
