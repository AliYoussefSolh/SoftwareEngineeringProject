package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.*;

import TodoList.Task;
import TodoList.TodoListHandler;
import Users.Student;


@SuppressWarnings("serial")
public class CreateTaskPage extends JFrame implements ActionListener {
	private Student student;
	private StudentHomePage userHomePage;
	private JLabel instructions, dueDateLabel;
	private JTextField taskInstructionsInput;
	private JButton pickDateButton, publishButton; 
	private String dueDate = "";
	
	public CreateTaskPage (Student st, StudentHomePage homePage) {
		this.student = st;
		userHomePage = homePage;
		userHomePage.setVisible(false);
		
		this.setTitle("Add Task page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(555, 500);
		this.setLayout(null);
		ImageIcon image = new ImageIcon("lau-logo.jpg");
		this.setIconImage(image.getImage());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				userHomePage.setVisible(true);
		    }  
		});
		
		instructions = new JLabel("Task Instructions", SwingConstants.CENTER);
		instructions.setBounds(0, 40, 540, 30);
		taskInstructionsInput = new JTextField();
		taskInstructionsInput.setBounds(10, 120, 520, 100);
		this.add(instructions);
		this.add(taskInstructionsInput);
		
		pickDateButton = new JButton("pick due date");
		pickDateButton.setBounds(202, 310, 130, 30);
		pickDateButton.addActionListener(this);
		this.add(pickDateButton);
		
		dueDateLabel = new JLabel("Due Date: ", SwingConstants.CENTER);
		dueDateLabel.setBounds(0, 345, 540, 30);
		this.add(dueDateLabel);
		
		publishButton = new JButton("Add Task");
		publishButton.setBounds(182, 380, 170, 60);
		publishButton.setForeground(Color.blue);
		publishButton.addActionListener(this);
		this.add(publishButton);
		
		this.setVisible(true);
	}
		
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == pickDateButton) {
			dueDate = new DatePicker(this).getPickedDate();
			dueDateLabel.setText("Due Date: " + dueDate);
		}
		if (click.getSource() == publishButton){
			String instructions = taskInstructionsInput.getText().trim();
			Task t = new Task(instructions,dueDate,student);
			TodoListHandler tdh = new TodoListHandler(t,student);
			try {
				tdh.uploadToDB();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.dispose();
			userHomePage.setVisible(true);
		}
	}
}
