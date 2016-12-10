import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays; // for testing

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
	private JPanel reservationPanel, viewCancelPanel, reservationButtonPanel, roomNumberPanel, cancelPanel;
	private JTextArea availableRoomsArea, cancelArea;
	private JTextField roomNumberField, checkInField, checkOutField;
	private JLabel availableRoomsLabel, roomNumberLabel, usernameLabel, allReservationsLabel, displayReservationsLabel;
	private JScrollPane roomsScrollPane;
	private JTable roomsTable;
	private JComboBox roomTypeComboBox;
	private DefaultTableModel roomsModel;
	
	int selectedRoomsRow, selectedCalendarRow, selectedCalendarColumn;

	public GuestView(ReservationSystem model) throws Exception {
		this.model = model;
		
		setTitle("Guest View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		// TODO add changelistener to update text areas
		
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
		String[] comboOptions = { "Luxurious", "Economic" };
		roomTypeComboBox = new JComboBox(comboOptions);
		
		viewCancelPanel = new JPanel(new BorderLayout()); // adds components of rooms panel
		
//		buildRoomsTableModel();	//flo
		buildRoomsTablePanel();	//flo
		
		cancelPanel = new JPanel(new BorderLayout());
		
		cancelArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		cancelReservationButton = new JButton("Cancel selected reservation");
		cancelPanel.add(cancelArea, BorderLayout.EAST);
//		cancelPanel.add(cancelReservationButton, BorderLayout.SOUTH);
		
		makeReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] message = {"Enter check-in date:", checkInField, "Enter check-out date:", checkOutField, "Enter room type:", roomTypeComboBox};
				
				int choice = JOptionPane.showConfirmDialog(GuestView.this, message, "Enter dates", JOptionPane.OK_CANCEL_OPTION);
				
				if(choice == JOptionPane.OK_OPTION) {
					boolean[] rooms;
					
					String checkIn = checkInField.getText();
					String checkOut = checkOutField.getText();
					String roomType = (roomTypeComboBox.getSelectedIndex() == 0) ? "L" : "E";
						
					try {
						if(model.checkStayValidity(checkIn, checkOut))
								throw new Exception();
								
						rooms = model.getOccupiedRooms(checkIn, checkOut);
						availableRoomsArea.setText(printAvailableRooms(rooms, roomType));
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
		
		//try { // this is unnecessary
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
		
//		} catch (Exception e) {
//			
//		}
		
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
	
	//TODO
	//flo
	// this method gets all reservations of currentAccount and prints them in viewCancelPanel
	private void buildRoomsTablePanel() {
		allReservationsLabel = new JLabel("All reservations");
		viewCancelPanel.add(allReservationsLabel, BorderLayout.NORTH);
		
		DefaultListModel<Reservation> viewReservationsModel = new DefaultListModel<Reservation>();
		
		//need to get Reservations to print 
		
		ArrayList<Reservation> viewReservationsAL = new ArrayList<Reservation>();
//		List<Reservation> cancelReservations = new ArrayList<Reservation>();
		//add all activeAccount's Reservations to dfl
		try {
			viewReservationsAL.add(new Reservation("11/20/2018", "11/25/2018", "L", 5));
			viewReservationsAL.add(new Reservation("10/20/2018", "10/25/2018", "L", 4));
			viewReservationsAL.add(new Reservation("12/20/2018", "12/25/2018", "E", 6));
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		for(int i = 0; i < viewReservationsAL.size(); i++) {
			viewReservationsModel.addElement(viewReservationsAL.get(i));
		}

		//TODO set list to allow multiple selections // get it working first
		
		JList<Reservation> viewReservationsList = new JList<Reservation>(viewReservationsModel);

		JScrollPane viewReservationsScrollPane = new JScrollPane(viewReservationsList);
		
		viewCancelPanel.add(viewReservationsScrollPane, BorderLayout.WEST);
		
//		cancelReservationButton.addActionListener( new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				 when clicked must delete selected reservations from viewReservationsList from activeAccount
//				ArrayList<Reservation> cancelReservations = (ArrayList<Reservation>) viewReservationsList.getSelectedValuesList();
//				
//				for(int i = 0; i < viewReservationsAL.size(); i++) {
//					if(cancelReservations.contains(viewReservationsAL.get(i))) {
//						viewReservationsAL.remove(i);
//					}
//				}
//				//TODO
//			}
//		});
		
//		viewCancelPanel.add(cancelReservationButton, BorderLayout.SOUTH);
//		viewCancelPanel.add(cancelPanel); // TODO fix throwing null pointer exception

	}
	
	//flo
//	private void buildRoomsTableModel() { //TODO
//		MouseAdapter m = new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				selectedRoomsRow = roomsTable.getSelectedRow();
//				
//				Reservation toBeCancelled = (Reservation) roomsTable.getValueAt(selectedCalendarRow, selectedCalendarColumn);
//			}
//		};
//		
//		roomsModel = new DefaultTableModel(buildRoomsArray(), null);
//		roomsTable = new JTable(roomsModel) {
//			public boolean isCellEditable(int row, int col) {
//				return false;
//			}
//		};
//		roomsTable.setRowHeight(ROOMS_CELL_HEIGHT);
//		roomsTable.setCellSelectionEnabled(true);
//		roomsTable.addMouseListener(m);
//	}
	
	//flo
	private Reservation[][] buildRoomsArray() {
		ArrayList<Reservation> allReservations = model.getAllReservations();
		Reservation[][] temp = new Reservation[ROOMS_NUMBER_OF_ROWS][2];
		
		for(int i = 0; i < allReservations.size(); i++) {
			temp[i][1] = allReservations.get(i);
		}
		
		return temp;
	}
	
	public void setActiveAccount(Account account) {
		activeAccount = account;
		availableRoomsArea.setText("Current user: " + activeAccount.getName());
	}
	
	public void setActiveAccount(int id) {
		activeAccount = model.getAccounts().get(id);
		availableRoomsArea.setText("Current user: " + activeAccount.getName());
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
