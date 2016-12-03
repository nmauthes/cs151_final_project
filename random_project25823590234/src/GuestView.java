import javax.swing.*;
import java.awt.*;

public class GuestView extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = WIDTH;
	private final int ROOM_VIEW_WIDTH = 20;
	private final int ROOM_VIEW_HEIGHT = 25;
	

	private ReservationSystem model;
	private Account activeAccount;
	
	//private JTextArea roomViewArea;

	public GuestView(ReservationSystem model) {
		this.model = model;
		
		setTitle("Guest View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	public void setActiveAccount(String id) {
		//TODO
	}
}
