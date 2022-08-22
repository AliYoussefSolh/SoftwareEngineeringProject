package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.*;

import Exceptions.InvalidEmailException;
import Users.Student;

@SuppressWarnings("serial")
public class CreateOrViewReview extends JFrame implements ActionListener {
	private JButton ViewReviewAction, CreateReviewAction;
	private JLabel welcomeMessage;
	private Student student;
	public CreateOrViewReview(Student user, StudentHomePage homePage) {
		student = user;
		this.setTitle("Review home page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(true);
		this.setSize(450, 180);
		this.setLayout(null);
		
		welcomeMessage = new JLabel();
		welcomeMessage.setBounds(120, 10, 300, 20);
		welcomeMessage.setText("Do you want to view or create review");
		this.add(welcomeMessage);
		
		ViewReviewAction = new JButton();
		ViewReviewAction.setText("View Review");
		ViewReviewAction.setBounds(15, 60, 150, 35);
		ViewReviewAction.addActionListener(this);
		this.add(ViewReviewAction);
		
		CreateReviewAction = new JButton();
		CreateReviewAction.setText("Create Review");
		CreateReviewAction.setBounds(220, 60, 150, 35);
		CreateReviewAction.addActionListener(this);
		this.add(CreateReviewAction);
		
		this.setVisible(true);
	}
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	@Override
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == ViewReviewAction) {
			try {
				try {
					new SelectViewReviewCourses(student, this);
				} catch (NumberFormatException | InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (click.getSource() == CreateReviewAction) {
			try {
				try {
					new SelectCourse (student, this);
				} catch (NumberFormatException | InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
}
