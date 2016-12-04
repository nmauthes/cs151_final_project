import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuestView extends JFrame {
	private final int WIDTH = 600;
	private final int HEIGHT = 550;
	private final int TEXT_AREA_WIDTH = 20;
	private final int TEXT_AREA_HEIGHT = 30;
	private final int FIELD_WIDTH = 5;
	
	private ReservationSystem model;
	private Account activeAccount;
	
	private JButton makeReservationButton, confirmButton;
	private JTabbedPane guestTabs;
	private JPanel reservationPanel, viewCancelPanel, reservationButtonPanel, roomNumberPanel;
	private JTextArea availableRoomsArea;
	private JTextField roomNumberField, checkinField, checkoutField;
	private JLabel availableRoomsLabel, roomNumberLabel;

	public GuestView(ReservationSystem model) {
		this.model = model;
		
		setTitle("Guest View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		guestTabs = new JTabbedPane();
		
		reservationPanel = new JPanel(new BorderLayout());
		
		availableRoomsArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		reservationPanel.add(availableRoomsArea, BorderLayout.CENTER);
		
		reservationButtonPanel = new JPanel();
		
		availableRoomsLabel = new JLabel("Available rooms");
		reservationPanel.add(availableRoomsLabel, BorderLayout.NORTH);
		
		makeReservationButton = new JButton("Make reservation");
		confirmButton = new JButton("Confirm");
		
		checkinField = new JTextField(FIELD_WIDTH);
		checkoutField = new JTextField(FIELD_WIDTH);
		
		makeReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] message = {"Enter check-in date:", checkinField, "Enter check-out date:", checkoutField};
				
				int choice = JOptionPane.showConfirmDialog(GuestView.this, message, "Enter dates", JOptionPane.OK_CANCEL_OPTION);
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		reservationButtonPanel.add(makeReservationButton);
		reservationButtonPanel.add(confirmButton);
		reservationPanel.add(reservationButtonPanel, BorderLayout.SOUTH);
		
		roomNumberPanel = new JPanel();
		
		roomNumberLabel = new JLabel("Enter room number to reserve:");
		roomNumberField = new JTextField(FIELD_WIDTH);
		
		roomNumberPanel.add(roomNumberLabel);
		roomNumberPanel.add(roomNumberField);
		reservationPanel.add(roomNumberPanel, BorderLayout.EAST);
		
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
