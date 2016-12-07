
public class ComprehensiveReceipt implements Receipt 
{
	public void showReceipt(int id, String name, Account account)
	{
		if (account.getName() == name && account.getID() == id)
		{
			System.out.print("User ID: " + id + ", Name: " + name);
			System.out.print(account.getReservations());
		}
	}
}
