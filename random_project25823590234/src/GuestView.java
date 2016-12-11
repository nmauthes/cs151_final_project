import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brogrammers
 *
 * View class for the guest view portion of the program. Creates the make reservation and view cancel panels
 * and provides controllers for making, viewing and cancelling guest reservations.
 */
public class GuestView extends JFrame {
	private final int WIDTH = 600;
	private final int HEIGHT = 450;
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
	private JPanel reservationPanel, viewCancelPanel, reservationButtonPanel, roomNumberPanel;
	private JTextArea availableRoomsArea;
	private JTextField roomNumberField, checkInField, checkOutField;
	private JLabel availableRoomsLabel, roomNumberLabel, allReservationsLabel;
	private JComboBox<String> roomTypeComboBox;
	private DefaultListModel<Reservation> viewReservationsModel;
	private JList<Reservation> viewReservationsList;
	
	int selectedRoomsRow, selectedCalendarRow, selectedCalendarColumn;

	public GuestView(ReservationSystem model) throws Exception {
		this.model = model;
		
		setTitle("Guest View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		viewReservationsModel = new DefaultListModel<Reservation>();
		viewReservationsList = new JList<Reservation>(viewReservationsModel);
		activeAccount = new Account("default");							// temporary for testing purposes, remove after sign in is completed
		updateViewCancelModel();
		
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
		roomTypeComboBox = new JComboBox<>(comboOptions);
		
		makeViewCancelTab();
		
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
		
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int newRoomNumber;
				try {
					newRoomNumber = Integer.parseInt(roomNumberField.getText());
					if(!currentlyOccupiedRooms[newRoomNumber]) {
						String roomType = (newRoomNumber > ReservationSystem.NUMBER_OF_ROOMS / 2) ? "E" : "L";
						try {
							Reservation r = new Reservation(currentCheckInDate, currentCheckOutDate, roomType, newRoomNumber);
							activeAccount.addReservation(r);
							model.changeMade();
							
							updateViewCancelModel();
							String[] choices = { "Simple Receipt", "Comprehensive Receipt" };
							String input = (String) JOptionPane.showInputDialog(null, "Choose a Receipt Type",
							        "Receipt Selection", JOptionPane.QUESTION_MESSAGE, null, // Use
							                                                                        // default
							                                                                        // icon
							        choices, // Array of choices
							        choices[1]); // Initial choice
							Receipt receipt;
							// User decides which Receipt they would like and it will print the corresponding choice
							if (input.equals("Simple Receipt"))
							{
								receipt = new SimpleReceipt();
								JOptionPane.showMessageDialog(GuestView.this, receipt.showReceipt(activeAccount) + "\n", "Reservation successful", JOptionPane.PLAIN_MESSAGE); // FIX BALANCE
							}
							else
							//if (input.equals("Comprehensive Receipt"))
							{
								receipt = new ComprehensiveReceipt();
								JOptionPane.showMessageDialog(GuestView.this, receipt.showReceipt(activeAccount) + "\n", "Reservation successful", JOptionPane.PLAIN_MESSAGE); // FIX BALANCE
							}
							
						}
						catch(Exception ex) {
							JOptionPane.showMessageDialog(GuestView.this, "An error occurred", "Reservation error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				catch(NullPointerException n) {
					JOptionPane.showMessageDialog(GuestView.this, "Please make a reservation first", "You have not made a reservation", JOptionPane.WARNING_MESSAGE);
				}
				catch(NumberFormatException nf) {
					JOptionPane.showMessageDialog(GuestView.this, "Please enter a valid number", "Invalid room number", JOptionPane.WARNING_MESSAGE);
				}
				

				availableRoomsArea.setText("");
				roomNumberField.setText("");
				currentlyOccupiedRooms = null;
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
				
		guestTabs.addTab("New reservation", reservationPanel);
		guestTabs.addTab("View/Cancel", viewCancelPanel);
		
		add(guestTabs);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	/**
	 * Creates components of View/Cancel tab
	 */
	private void makeViewCancelTab() {
		viewCancelPanel = 										new JPanel(new BorderLayout());
		cancelReservationButton = 								new JButton("Cancel selected reservation");
		allReservationsLabel = 									new JLabel("All reservations");
		viewReservationsList = 									new JList<Reservation>(viewReservationsModel);
		JScrollPane viewReservationsScrollPane = 				new JScrollPane(viewReservationsList);
				
		// When clicked, removes selected Reservations from activeAccount's Reservations		
		cancelReservationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] cancelIndices = viewReservationsList.getSelectedIndices();				
				activeAccount.removeReservations(cancelIndices);
				updateViewCancelModel();
				model.changeMade();
			}
		});
		
		// Add components to viewCancelPanel
		viewCancelPanel.add(viewReservationsScrollPane, BorderLayout.WEST);
		viewCancelPanel.add(allReservationsLabel, BorderLayout.NORTH);
		viewCancelPanel.add(cancelReservationButton, BorderLayout.SOUTH);
	}
	
	/**
	 * Updates View/Cancel Tab's Reservation List when changes made to Model
	 */
	public void updateViewCancelModel() {
		
		viewReservationsModel.clear();
		// Get activeAccount's Reservations
		ArrayList<Reservation> viewReservationsAL = activeAccount.getReservations();
		
		// Add activeAccount's Reservations to viewReservationsModel
		for(int i = 0; i < viewReservationsAL.size(); i++) {
			viewReservationsModel.addElement(viewReservationsAL.get(i));
		}	
	}
	
	/**
	 * Sets the active account once a user signs up.
	 * 
	 * @param account The currently focused account
	 */
	public void setActiveAccount(Account account) {
		activeAccount = account;
		availableRoomsArea.setText("Current user: " + activeAccount.getName());
		updateViewCancelModel();
	}
	
	/**
	 * Sets the active account by ID once a user signs in.
	 * 
	 * @param account The currently focused account
	 */
	public void setActiveAccount(int id) {
		activeAccount = model.getAccounts().get(id);
		availableRoomsArea.setText("Current user: " + activeAccount.getName());
		updateViewCancelModel();
	}
	
	private String printAvailableRooms(boolean[] rooms, String roomType) {
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
				roomsList += "Room " + (i + 1) + "\n";
			}
		}
		
		if(roomsList.equals(""))
			return "There are no such rooms available";
		
		return roomsList;
	}
	
}
