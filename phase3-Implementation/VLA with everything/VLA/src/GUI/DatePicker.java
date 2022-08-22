package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePicker {
	private JDialog datePicker;
	private String pickedDate = "";
	private int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	private int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	private JLabel monthLabel = new JLabel("", JLabel.CENTER);
	private JButton[] buttons = new JButton[49];
	
	public DatePicker(CreateTaskPage assignmentPage) {
		datePicker = new JDialog();
		datePicker.setModal(true);
		datePicker.setTitle("Date Picker");
		
		JPanel calender = new JPanel(new GridLayout(7, 7));
		calender.setPreferredSize(new Dimension(430, 120));
		String[] daysOfTheWeek = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };
        
        for (int x = 0; x < buttons.length; x++) {		
        	final int selection = x;
        	buttons[x] = new JButton();
            if (x > 6)
            	buttons[x].addActionListener(new ActionListener() {
            		public void actionPerformed(ActionEvent click) {
            			pickedDate = buttons[selection].getActionCommand();
            			datePicker.dispose();
            		}
                });
           else
        	   buttons[x].setText(daysOfTheWeek[x]);
           calender.add(buttons[x]);//add buttons
        }
        
		JPanel footer = new JPanel(new GridLayout(1, 3));
		JButton previous = new JButton("<< Previous");
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent click) {
				month--;
				displayDate();
			}
        });
		
		JButton next = new JButton("Next >>");
        //add action command
        next.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		month++;
        		displayDate();
        	}
        });
        footer.add(previous);
		footer.add(monthLabel);
		footer.add(next);
		datePicker.add(calender, BorderLayout.CENTER);
		datePicker.add(footer, BorderLayout.SOUTH);
		datePicker.pack();
		
		displayDate();
		datePicker.setVisible(true);
	}
	
	public void displayDate(){
    	for (int x = 7; x < buttons.length; x++)
    		buttons[x].setText("");//
  	    SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");	
  	    Calendar cal = java.util.Calendar.getInstance();			
    	cal.set(year, month, 1);
    	int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
    	int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    	for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
    		buttons[x].setText("" + day);
    	monthLabel.setText(sdf.format(cal.getTime()));
    }

	public String getPickedDate() {
    	//if condition
    	if (pickedDate.equals(""))
    		return pickedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(pickedDate));
        return sdf.format(cal.getTime());
    }
	
}
