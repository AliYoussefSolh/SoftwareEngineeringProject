package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;

import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import TodoList.Task;
import Users.Student;


@SuppressWarnings("serial")
public class ToDOLIST extends JFrame implements ActionListener{
	private StudentHomePage userHomePage;
	private Student student;
	private JLabel head, nothingHere;
	private ImageIcon checked, unchecked;
	private JButton add_task,save_changes;
	static ArrayList<Task> tasks ;
	static int numberOfTasks;
	static JCheckBox[] TasksButtons;
	
	public ToDOLIST(Student user, StudentHomePage homePage) throws NumberFormatException, FileNotFoundException, 
	ParseException, InvalidRatingValueException, InvalidEmailException {
		add_task=new JButton("Add Task");
		add_task.setBounds(20,5,100, 17);
		add_task.addActionListener(this);
		this.add(add_task);
		
		
		save_changes=new JButton("Save Changes");
		save_changes.setBounds(20, 25, 130, 17);
		save_changes.addActionListener(this);
		this.add(save_changes);
		student = user;
		tasks = Database.selectStudentTasks(student);
		numberOfTasks = tasks.size();
		userHomePage = homePage;
		userHomePage.setVisible(false);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		ImageIcon image = new ImageIcon("lau-logo.jpg");
		this.setIconImage(image.getImage());
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				userHomePage.setVisible(true);
		    }  
		});
		
		JPanel header = new JPanel(new GridLayout(1, 1));
		header.setPreferredSize(new Dimension(400, 40));
		head = new JLabel("To Do List:", SwingConstants.CENTER);
		head.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
		head.setForeground(Color.blue);
		head.setBounds(0, 10, 500, 30);
		header.add(head);
		this.add(header);
		

		if (numberOfTasks != 0) {
			checked = new ImageIcon("./src/GUI/true.png");
			unchecked = new ImageIcon("./src/GUI/false.png");
			TasksButtons = new JCheckBox[numberOfTasks];
			JPanel panel = new JPanel(new GridLayout((numberOfTasks %2 == 0 ? numberOfTasks/2  : numberOfTasks/2 + 1), 2));
			for (int i = 0; i < numberOfTasks; i++) {
				String description = "<html>" + tasks.get(i).toString().replace("\n", "<br>");
				TasksButtons[i] = new JCheckBox(description);
				if (tasks.get(i).isComplete()) {
					TasksButtons[i].setIcon(checked);
				}else {
					TasksButtons[i].setIcon(unchecked);
				}
				TasksButtons[i].setIcon(unchecked);
				TasksButtons[i].setSelectedIcon(checked);
				panel.add(TasksButtons[i]);
				TasksButtons[i].setSelected(tasks.get(i).isComplete());;
			}
			this.add(panel, BorderLayout.SOUTH);
			this.pack();
		}
		else {
			JPanel noAssignments = new JPanel(new GridLayout(1, 1));
			noAssignments.setPreferredSize(new Dimension(400, 200));
			nothingHere = new JLabel("No Tasks Yet....", SwingConstants.CENTER);
			nothingHere.setFont(new Font(null, Font.PLAIN, 14));
			noAssignments.add(nothingHere);
			this.add(noAssignments, BorderLayout.SOUTH);
			this.pack();
		}
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==add_task) {
			new CreateTaskPage(student, userHomePage);
			this.dispose();
		}
		if (e.getSource()==save_changes) {
			for (int i=0;i<numberOfTasks;i++) {
				try {
					Database.modifyTaskCompleteness(tasks.get(i), TasksButtons[i].isSelected());
					student.setTasks(Database.selectStudentTasks(student));
					int nbOfIncompleteTasks = 0;
					for (Task t : student.getTasks())
						if (!t.isComplete())
							++nbOfIncompleteTasks;
					userHomePage.notification.setText("Check out your tasks in to do list .You have "+nbOfIncompleteTasks+" Incomplete tasks");
				} catch (NumberFormatException | IOException | ParseException | InvalidRatingValueException | InvalidEmailException e1) {
					e1.printStackTrace();
				}
			}
		}	
	}

}
