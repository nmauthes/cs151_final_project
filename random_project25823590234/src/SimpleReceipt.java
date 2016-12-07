
public class SimpleReceipt implements Receipt 
{
	public void showReceipt(int id, String name, Account account)
	{
		if (account.getName() == name && account.getID() == id)
		{
			System.out.print("User ID: " + id + ", Name: " + name);
			// TO DO implement method to return all reservered rooms (NOT RESERVATIONS) by User and corresponding amount due
		}
	}
}
