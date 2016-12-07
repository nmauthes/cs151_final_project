
public class ReceiptContext 
{
	private Receipt receipt;
	
	public ReceiptContext(Receipt receipt)
	{
		this.receipt = receipt;
	}
	
	public void executeStrategy(int id, String name, Account account)
	{
		receipt.showReceipt(id, name, account);
	}
	
}
