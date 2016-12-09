import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GuestView extends JFrame {
	private final int WIDTH = 800;
	private final int HEIGHT = 550;
	private final int TEXT_AREA_WIDTH = 20;
	private final int TEXT_AREA_HEIGHT = 30;
	private final int ROOMS_NUMBER_OF_ROWS = 5;
	private final int FIELD_WIDTH = 5;
	private final int ROOMS_CELL_HEIGHT = 10;
	
	private ReservationSystem model;
	private Account activeAccount;
	private boolean[] currentlyOccupiedRooms;
	private String currentCheckInDate, currentCheckOutDate;
	
	private JButton makeReservationButton, confirmButton, cancelReservationButton;
	private JTabbedPane guestTabs;
	private JPanel reservationPanel, viewCancelPanel, reservationButtonPanel, roomNumberPanel, roomsInfoPanel;
	private JTextArea availableRoomsArea, roomsInfoArea;
	private JTextField roomNumberField, checkInField, checkOutField, roomTypeField;
	private JLabel availableRoomsLabel, roomNumberLabel, usernameLabel, allReservationsLabel;
	private JScrollPane roomsScrollPane;
	private JTable roomsTable;
	private DefaultTableModel roomsModel;
	
	int selectedRoomsRow, selectedCalendarRow, selectedCalendarColumn;

	public GuestView(ReservationSystem model) throws Exception {
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
		
		viewCancelPanel = new JPanel(new BorderLayout()); // adds components of rooms panel
		
		buildRoomsTableModel();	//flo
		buildRoomsTablePanel();	//flo
		
		roomsInfoPanel = new JPanel(new BorderLayout());
		
		roomsInfoArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		cancelReservationButton = new JButton("Cancel selected reservation");
		roomsInfoPanel.add(roomsInfoArea, BorderLayout.EAST);
		roomsInfoPanel.add(cancelReservationButton, BorderLayout.SOUTH);
		viewCancelPanel.add(roomsInfoPanel);
		
		makeReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] message = {"Enter check-in date:", checkInField, "Enter check-out date:", checkOutField, "Enter room type:", roomTypeField};
				
				int choice = JOptionPane.showConfirmDialog(GuestView.this, message, "Enter dates", JOptionPane.OK_CANCEL_OPTION);
				
				if(choice == JOptionPane.OK_OPTION) {
					boolean[] rooms;
					
					String checkIn = checkInField.getText();
					String checkOut = checkOutField.getText();
					
					while(!checkStayValidity(checkIn, checkOut)) {
						//do something (ex: popup window) to tell user
						// "No account with this ID. Please enter a valid ID."
						
						checkIn = checkInField.getText();
						checkOut = checkOutField.getText();
					}
							
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
		
//		viewCancelPanel = new JPanel(new BorderLayout());
		
		guestTabs.addTab("New reservation", reservationPanel);
		guestTabs.addTab("View/Cancel", viewCancelPanel);
		
		add(guestTabs);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	//flo
	private void buildRoomsTablePanel() {
		allReservationsLabel = new JLabel("All reservations");
		
		roomsScrollPane = new JScrollPane(roomsTable);
		
		viewCancelPanel.add(allReservationsLabel, BorderLayout.NORTH);
		viewCancelPanel.add(roomsScrollPane, BorderLayout.WEST);
	}
	
	//flo
	private void buildRoomsTableModel() { //TODO
		MouseAdapter m = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				selectedRoomsRow = roomsTable.getSelectedRow();
				
				Reservation toBeCancelled = (Reservation) roomsTable.getValueAt(selectedCalendarRow, selectedCalendarColumn);
			}
		};
		
		roomsModel = new DefaultTableModel(buildRoomsArray(), null);
		roomsTable = new JTable(roomsModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		roomsTable.setRowHeight(ROOMS_CELL_HEIGHT);
		roomsTable.setCellSelectionEnabled(true);
		roomsTable.addMouseListener(m);
	}
	
	//flo
	private Reservation[][] buildRoomsArray() {
		ArrayList<Reservation> allReservations = model.getAllReservations();
		Reservation[][] temp = new Reservation[ROOMS_NUMBER_OF_ROWS][2];
		
		for(int i = 0; i < allReservations.size(); i++) {
			temp[i][1] = allReservations.get(i);
		}
		
		return temp;
	}
	
	// input validation for checkIn and checkOut dates
	public boolean checkStayValidity(String checkIn, String checkOut) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date checkInDate = null;
		Date checkOutDate = null;
		
		try {
			checkInDate = sdf.parse(checkIn);
			checkOutDate = sdf.parse(checkOut);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date today = new Date();
		
		//checks not before current date
		if(today.before(checkInDate)||today.before(checkOutDate))
			return false;
		
		//checks less than 60 days
		int diffInDays = (int)( (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24) );
		
		if(diffInDays >= 60)
			return false;
		
		return true;
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
