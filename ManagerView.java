import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;

/**
 * @author Brogrammers
 * 
 * View class for the manager portion of the project. Creates and provides a clickable calendar for viewing reserved rooms and 
 * a rooms view that allows manager to view reservations by room number.
 *
 */
public class ManagerView extends JFrame {
	private final int WIDTH = 925;
	private final int HEIGHT = 575;
	private final int TEXT_AREA_WIDTH = 25;
	private final int TEXT_AREA_HEIGHT = 40;
	private final int CALENDAR_CELL_HEIGHT = 50;
	private final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private final int DAYS_PER_WEEK = 7;
	private final int WEEKS_PER_MONTH = 6;

	private ReservationSystem model;
	
	private JTabbedPane managerTabs;
	private JLabel monthAndYearLabel, roomNumbersLabel;
	private JButton prevMonthButton, nextMonthButton, prevYearButton, nextYearButton, saveButton, loadButton;
	private JScrollPane calendarScrollPane, roomsScrollPane;
	private JPanel calendarPanel, calendarButtonPanel, calendarInfoPanel, roomsPanel, roomsInfoPanel;
	private DefaultTableModel calendarModel;
	private JTable calendarTable;
	private JList<Integer> roomsList;
	private JTextArea calendarInfoArea, roomsInfoArea;
	
	int selectedCalendarRow, selectedCalendarColumn;

	public ManagerView(ReservationSystem model) {
		this.model = model;
		
		ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateTableModel();
				highlightSelectedCell();
				updateLabels();
				updateCalendar();
				updateRoomsView();
			}
		};
		model.addListener(cl);
		
		setTitle("Manager View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		managerTabs = new JTabbedPane();
	
		calendarPanel = new JPanel(new BorderLayout()); // adds components of calendar panel
		
		monthAndYearLabel = new JLabel();
		updateLabels();
		
		buildCalendarTableModel();
		buildCalendarPanel();
		
		calendarInfoPanel = new JPanel(new BorderLayout());
		
		calendarInfoArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		saveButton = new JButton("Save all"); // SAVES MODEL'S DATA USING SERIALIZATION
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.save();
				JOptionPane.showMessageDialog(ManagerView.this, "Save successful", "Reservations saved", JOptionPane.PLAIN_MESSAGE);
			}
		});
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.load();
					JOptionPane.showMessageDialog(ManagerView.this, "Loading successful", "Reservations loaded", JOptionPane.PLAIN_MESSAGE);
				}
				catch(ClassNotFoundException i) {
					JOptionPane.showMessageDialog(ManagerView.this, "Loading failed", "File not found", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(ManagerView.this, "Loading failed", "There was an error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		calendarInfoPanel.add(loadButton, BorderLayout.NORTH);
		calendarInfoPanel.add(calendarInfoArea, BorderLayout.CENTER);
		calendarInfoPanel.add(saveButton, BorderLayout.SOUTH);
		calendarPanel.add(calendarInfoPanel, BorderLayout.EAST);
		
		roomsPanel = new JPanel(new BorderLayout()); // adds components of rooms panel

		buildRoomsListPanel();

		roomsInfoPanel = new JPanel(new BorderLayout());
		
		roomsInfoArea = new JTextArea(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT);
		roomsInfoPanel.add(roomsInfoArea, BorderLayout.CENTER);
		roomsPanel.add(roomsInfoPanel);
		
		managerTabs.addTab("Calendar", calendarPanel);
		managerTabs.addTab("Rooms", roomsPanel);
		add(managerTabs);
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	private void buildRoomsListPanel() {
		roomNumbersLabel = new JLabel("Room information:");
		
		ListSelectionListener l = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				updateRoomsView();
			}
		};
		
		Integer[] rooms = new Integer[ReservationSystem.NUMBER_OF_ROOMS];
		for(int i = 0; i < rooms.length; i++) {
			rooms[i] = i + 1;
		}
		
		roomsList = new JList(rooms);
		roomsList.addListSelectionListener(l);
		
		roomsScrollPane = new JScrollPane(roomsList);
		
		roomsPanel.add(roomNumbersLabel, BorderLayout.NORTH);
		roomsPanel.add(roomsScrollPane, BorderLayout.WEST);
	}
	
	private void updateRoomsView() {
		ArrayList<Reservation> reservations = model.getReservationsByRoomNumber(roomsList.getSelectedIndex());
		
		String list = "";
		for(Reservation r : reservations) {
			list += r.toString() + "\n";
		}
		
		roomsInfoArea.setText(list);
	}
	
	private void buildCalendarPanel() {
		prevMonthButton = new JButton("<<");
		nextMonthButton = new JButton(">>");
		prevYearButton = new JButton("<");
		nextYearButton = new JButton(">");
		
		prevMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.previousMonth();
			}
		});
		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.nextMonth();
			}
		});
		prevYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.previousYear();
			}
		});
		nextYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.nextYear();
			}
		});
		
		calendarPanel.add(monthAndYearLabel, BorderLayout.NORTH);
		
		calendarScrollPane = new JScrollPane(calendarTable);
		calendarPanel.add(calendarScrollPane, BorderLayout.CENTER);
		
		calendarButtonPanel = new JPanel();
		calendarButtonPanel.add(prevYearButton);
		calendarButtonPanel.add(prevMonthButton);
		calendarButtonPanel.add(nextMonthButton);
		calendarButtonPanel.add(nextYearButton);
		calendarPanel.add(calendarButtonPanel, BorderLayout.SOUTH);
	}
	
	private void buildCalendarTableModel() {
		MouseAdapter m = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				selectedCalendarRow = calendarTable.getSelectedRow();
				selectedCalendarColumn = calendarTable.getSelectedColumn();
				
				int day = (int) calendarTable.getValueAt(selectedCalendarRow, selectedCalendarColumn);
				model.goToDate(model.getCalendar().get(Calendar.MONTH), day, model.getCalendar().get(Calendar.YEAR));
				
				updateCalendar();
			}
		};
		
		calendarModel = new DefaultTableModel(buildMonthArray(), DAYS);
		calendarTable = new JTable(calendarModel) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		calendarTable.setRowHeight(CALENDAR_CELL_HEIGHT);
		calendarTable.setCellSelectionEnabled(true);
		calendarTable.addMouseListener(m);
		highlightSelectedCell();
	}
	
	private Integer[][] buildMonthArray() {
		Integer[][] temp = new Integer[WEEKS_PER_MONTH][DAYS_PER_WEEK];
		
		int month = model.getCalendar().get(Calendar.MONTH);
		int year = model.getCalendar().get(Calendar.YEAR);
		Calendar tempCal = Calendar.getInstance();
		tempCal.set(year, month, 1);
		
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
		int day = 1;
		
		for(int i = 0; i < WEEKS_PER_MONTH; i++) {
			for(int j = 0; j < DAYS_PER_WEEK; j++) {
				if((i != 0 || j >= firstDay) && (day <= tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)))
					temp[i][j] = day++;
					
				if(day == model.getCalendar().get(Calendar.DAY_OF_MONTH) + 1) {
					selectedCalendarRow = i;
					selectedCalendarColumn = j;
				}
			}
		}
		return temp;
	}
	
	private void updateCalendar() {
		Date currentDate = model.getCalendar().getTime();
		ArrayList<Reservation> reservations = model.getReservationsByDate(currentDate);
		
		String list = "";
		for(Reservation r : reservations) {
			list += r.toString() + "\n";
		}
		
		calendarInfoArea.setText(list);
	}
	
	private void updateLabels() {
		monthAndYearLabel.setText(MONTHS[model.getCalendar().get(Calendar.MONTH)] + " " + model.getCalendar().get(Calendar.YEAR));
	}
	
	private void updateTableModel() {
		Integer[][] temp = buildMonthArray();
	
		calendarModel = new DefaultTableModel(temp, DAYS);
		calendarTable.setModel(calendarModel);
	}
	
	private void highlightSelectedCell() {
		for(int i = 0; i < calendarModel.getRowCount(); i++) {
			for(int j = 0; j < calendarModel.getColumnCount(); j++) {
				Integer val = (Integer) calendarModel.getValueAt(i, j);
				
				if(val != null && val == model.getCalendar().get(Calendar.DAY_OF_MONTH))
					calendarTable.changeSelection(i, j, false, false);
			}
		}
	}
}
