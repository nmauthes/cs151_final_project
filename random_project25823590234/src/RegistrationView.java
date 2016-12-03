import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationView extends JFrame {
	private final int FIELD_WIDTH = 5;

	private ReservationSystem model;
	private GuestView g;
	private ManagerView m;
	
	private JTextField signupField;
	private JLabel signupLabel;
	
	public RegistrationView(ReservationSystem model) {
		this.model = model;
		
		g = new GuestView(model);
		m = new ManagerView(model);
		
		setLayout(new FlowLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
				int choice = JOptionPane.showOptionDialog(RegistrationView.this, fields, "Guest login", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, null);
				
//				if(choice == JOptionPane.YES_OPTION)
//					// Sign in existing user
//				else
//					// Create new user
				
				g.setVisible(true);
				m.dispose();
			}
		});
		
		add(managerButton);
		add(guestButton);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}