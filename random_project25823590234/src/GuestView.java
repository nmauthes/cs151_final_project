import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuestView extends JFrame {
	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	private final int TEXT_AREA_WIDTH = 20;
	private final int TEXT_AREA_HEIGHT = 30;
	private final int FIELD_WIDTH = 5;
	
	private ReservationSystem model;
	private Account activeAccount;
	private boolean[] currentlyOccupiedRooms;
	private String currentCheckInDate, currentCheckOutDate;
	
	private JButton makeReservationButton, confirmButton;
	private JTabbedPane guestTabs;
	private JPanel reservationPanel, viewCancelPanel, reservationButtonPanel, roomNumberPanel;
	private JTextArea availableRoomsArea;
	private JTextField roomNumberField, checkInField, checkOutField, roomTypeField;
	private JLabel availableRoomsLabel, roomNumberLabel, usernameLabel;

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
		
		makeReservationButton = new JButton("Make new reservation");
		confirmButton = new JButton("Confirm?");
		
		checkInField = new JTextField(FIELD_WIDTH);
		checkOutField = new JTextField(FIELD_WIDTH);
		roomTypeField = new JTextField(FIELD_WIDTH); //TODO combo box?
		
		makeReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] message = {"Enter check-in date:", checkInField, "Enter check-out date:", checkOutField, "Enter room type:", roomTypeField};
				
				int choice = JOptionPane.showConfirmDialog(GuestView.this, message, "Enter dates", JOptionPane.OK_CANCEL_OPTION);
				
				if(choice == JOptionPane.OK_OPTION) {
					boolean[] rooms;
					String checkIn = checkInField.getText();
					String checkOut = checkOutField.getText(); // TODO add checks
					
					try {
						rooms = model.getOccupiedRooms(checkIn, checkOut, "test"); //TODO
						availableRoomsArea.setText(printAvailableRooms(rooms, "L"));
						currentlyOccupiedRooms = rooms;
						currentCheckInDate = checkIn;
						currentCheckOutDate = checkOut;
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(GuestView.this, "Please enter valid date(s)", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					checkInField.setText("");
					checkOutField.setText("");
				}
			}
		});
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newRoomNumber = Integer.parseInt(roomNumberField.getText());
				if(!currentlyOccupiedRooms[newRoomNumber]) {
					String roomType = (newRoomNumber > ReservationSystem.NUMBER_OF_ROOMS / 2) ? "L" : "E";
					try {
						Reservation r = new Reservation(currentCheckInDate, currentCheckOutDate, roomType, newRoomNumber);
						activeAccount.addReservation(r);
						JOptionPane.showMessageDialog(GuestView.this, "Receipt goes here", "Reservation successful", JOptionPane.PLAIN_MESSAGE); // TODO add receipt here
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(GuestView.this, "Reservation error", "An error occurred", JOptionPane.ERROR_MESSAGE);
					}
				}
				availableRoomsArea.setText("");
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
	
	public void setActiveAccount(Account account) {
		activeAccount = account;
		availableRoomsArea.setText("Current user: " + account.getName());
	}
	
	public String printAvailableRooms(boolean[] rooms, String roomType) {
		String roomsList = "";
		int startingIndex, endingIndex;
		
		if(roomType.equalsIgnoreCase("L")) {
			startingIndex = 0;
			endingIndex = rooms.length / 2;
		}
		else {
			startingIndex = rooms.length / 2;
			endingIndex = rooms.length;
		}
		
		for(int i = startingIndex; i < endingIndex; i++) {
			if(!rooms[i]) {
				roomsList += "Room " + i + "\n";
			}
		}
		
		if(roomsList.equals(""))
			return "There are no such rooms available";
		
		return roomsList;
	}
}
