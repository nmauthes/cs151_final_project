import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class ManagerView extends JFrame {
	private final int WIDTH = 500;
	private final int HEIGHT = 550;
	private final int CELL_HEIGHT = 50;
	private final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	private final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	private final int DAYS_PER_WEEK = 7;
	private final int WEEKS_PER_MONTH = 6;

	private ReservationSystem model;
	
	private JLabel monthAndYearLabel;
	private JButton prevMonthButton, nextMonthButton, prevYearButton, nextYearButton;
	private JScrollPane calendarScrollPane;
	private JPanel calendarPanel, calendarButtonPanel;
	private DefaultTableModel dtm;
	private JTable calendarTable;
	
	int selectedRow, selectedColumn;

	public ManagerView(ReservationSystem model) {
		this.model = model;
		
		ChangeListener cl = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateTableModel();
				highlightSelectedCell();
				updateLabels();
			}
		};
		model.addListener(cl);
		
		setTitle("Manager View");
		setLayout(new FlowLayout());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		
		calendarPanel = new JPanel(new BorderLayout());
		add(calendarPanel);
		
		monthAndYearLabel = new JLabel();
		updateLabels();
		
		buildTableModel();
		buildCalendarPanel();
		
		setLocationRelativeTo(null);
		//setVisible(true);
	}
	
	private void buildCalendarPanel() {
		prevMonthButton = new JButton("<<");
		nextMonthButton = new JButton(">>");
		prevMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getCalendar().previousMonth();
			}
		});
		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getCalendar().nextMonth();
			}
		});
		
		calendarPanel.add(monthAndYearLabel, BorderLayout.NORTH);
		
		calendarScrollPane = new JScrollPane(calendarTable);
		calendarPanel.add(calendarScrollPane, BorderLayout.CENTER);
		
		calendarButtonPanel = new JPanel();
		calendarButtonPanel.add(prevMonthButton);
		calendarButtonPanel.add(nextMonthButton);
		calendarPanel.add(calendarButtonPanel, BorderLayout.SOUTH);
	}
	
	private void buildTableModel() {
		MouseAdapter m = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				selectedRow = calendarTable.getSelectedRow();
				selectedColumn = calendarTable.getSelectedColumn();
				
				int day = (int) calendarTable.getValueAt(selectedRow, selectedColumn);
				model.getCalendar().goToDate(model.getCalendar().model.get(Calendar.MONTH), day, model.getCalendar().model.get(Calendar.YEAR));
			}
		};
		
		dtm = new DefaultTableModel(buildMonthArray(), DAYS);
		calendarTable = new JTable(dtm) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		calendarTable.setRowHeight(CELL_HEIGHT);
		calendarTable.setCellSelectionEnabled(true);
		calendarTable.addMouseListener(m);
		highlightSelectedCell();
	}
	
	private Integer[][] buildMonthArray() {
		Integer[][] temp = new Integer[WEEKS_PER_MONTH][DAYS_PER_WEEK];
		
		int month = model.getCalendar().model.get(Calendar.MONTH);
		int year = model.getCalendar().model.get(Calendar.YEAR);
		Calendar tempCal = Calendar.getInstance();
		tempCal.set(year, month, 1);
		
		int firstDay = tempCal.get(Calendar.DAY_OF_WEEK) - 1;
		int day = 1;
		
		for(int i = 0; i < WEEKS_PER_MONTH; i++) {
			for(int j = 0; j < DAYS_PER_WEEK; j++) {
				if((i != 0 || j >= firstDay) && (day <= tempCal.getActualMaximum(Calendar.DAY_OF_MONTH)))
					temp[i][j] = day++;
					
				if(day == model.getCalendar().model.get(Calendar.DAY_OF_MONTH) + 1) {
					selectedRow = i;
					selectedColumn = j;
				}
			}
		}
		return temp;
	}
	
	private void updateLabels() {
		monthAndYearLabel.setText(MONTHS[model.getCalendar().model.get(Calendar.MONTH)] + " " + model.getCalendar().model.get(Calendar.YEAR));
	}
	
	private void updateTableModel() {
		Integer[][] temp = buildMonthArray();
	
		dtm = new DefaultTableModel(temp, DAYS);
		calendarTable.setModel(dtm);
	}
	
	private void highlightSelectedCell() {
		for(int i = 0; i < dtm.getRowCount(); i++) {
			for(int j = 0; j < dtm.getColumnCount(); j++) {
				Integer val = (Integer) dtm.getValueAt(i, j);
				
				if(val != null && val == model.getCalendar().model.get(Calendar.DAY_OF_MONTH))
					calendarTable.changeSelection(i, j, false, false);
			}
		}
	}
}
