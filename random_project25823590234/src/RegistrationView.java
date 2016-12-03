import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrationView extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = WIDTH;

	private ReservationSystem model;
	private GuestView g;
	private ManagerView m;

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
			}
		});
		guestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.setVisible(true);
			}
		});
		
		add(managerButton);
		add(guestButton);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}