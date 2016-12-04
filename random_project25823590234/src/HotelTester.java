import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelTester {	
	public static void main(String[] args) {
		final int FIELD_WIDTH = 5;

		ReservationSystem rs = new ReservationSystem();
		GuestView g = new GuestView(rs);
		ManagerView m = new ManagerView(rs);
		
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
				String[] buttons = { "Sign in", "Sign up" };
				int choice = JOptionPane.showOptionDialog(loginFrame, fields, "Guest login", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);
				
//					if(choice == 0)
//						System.out.println("Option 1");
//					else
//						System.out.println("Option 2");
				
				g.setVisible(true);
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
