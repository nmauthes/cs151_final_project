import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Brogrammers
 * 
 * Main class for the hotel system. Instantiates the model and view classes. Also handles the initial login.
 *
 */
public class HotelTester {	
	public static void main(String[] args) throws Exception {
		ReservationSystem rs = new ReservationSystem();
		GuestView g = new GuestView(rs);
		ManagerView m = new ManagerView(rs);
		
		buildLoginFrame(rs, g, m);
	}
	
	private static void buildLoginFrame(ReservationSystem rs, GuestView g, ManagerView m) {
		final int FIELD_WIDTH = 5;
		
		JTextField signupField;
		JLabel signupLabel;
		
		JFrame loginFrame = new JFrame(); // creates login prompt for guest
		
		loginFrame.setLayout(new FlowLayout());
		loginFrame.setResizable(false);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton managerButton = new JButton("Manager");
		JButton guestButton = new JButton("Guest");
		
		managerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.setVisible(true);
				g.dispose();
			}
		});
		
		signupField = new JTextField(FIELD_WIDTH);
		signupLabel = new JLabel("Enter guest name or ID:");
		
		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] fields = { signupLabel, signupField };
				String[] buttons = { "Sign up", "Sign in" };
				int choice = JOptionPane.showOptionDialog(loginFrame, fields, "Guest login", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);
				
				if(choice == 0) { // add new user
					String name = signupField.getText();
					Account newAccount = new Account(name);
					rs.addAccount(newAccount);
					g.setActiveAccount(newAccount);
					g.setVisible(true);
				}
				else {
					try {
						int id = Integer.parseInt(signupField.getText());
						g.setActiveAccount(id);
						g.setVisible(true);
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(loginFrame, "Please enter a valid ID", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}	
				
				signupField.setText("");
				m.dispose();
			}
		});
		
		loginFrame.add(managerButton);
		loginFrame.add(guestButton);
		
		loginFrame.pack();
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setVisible(true);
	}
}
