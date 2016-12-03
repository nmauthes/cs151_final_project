import javax.swing.*;
import java.awt.*;

public class GuestView extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = WIDTH;
	private final int ROOM_VIEW_WIDTH = 20;
	private final int ROOM_VIEW_HEIGHT = 25;
	

	private ReservationSystem model;
	private Account activeAccount;
	
	private JTabbedPane guestTabs;
	private JPanel reservationPanel, viewCancelPanel;

	public GuestView(ReservationSystem model) {
		this.model = model;
		
		setTitle("Guest View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		guestTabs = new JTabbedPane();
		
		reservationPanel = new JPanel(new BorderLayout());
		viewCancelPanel = new JPanel(new BorderLayout());
		
		guestTabs.addTab("New reservation", reservationPanel);
		guestTabs.addTab("View/Cancel", viewCancelPanel);
		
		add(guestTabs);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	public void setActiveAccount(String id) {
		//TODO
	}
}
