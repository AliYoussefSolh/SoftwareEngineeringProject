package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.swing.*;

import Exceptions.InvalidEmailException;
import Exceptions.InvalidFileFormatException;
import Exceptions.InvalidRatingValueException;
import Users.*;
import LMS_data.LMS_API;

@SuppressWarnings("serial")
public class LoginPage extends JFrame implements ActionListener{
	private JButton loginButton;
	private JLabel ID;
	private JLabel unsuccessfullLogin, userTypeLabel;
	private JTextField IDInput;
	private JRadioButton student, instructor, admin;
	private ButtonGroup bg;
	
	public LoginPage() {
		
		this.setTitle("Log in page");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(380, 230);
		this.setLayout(null);
		
		//Labels
		ID = new JLabel("ID:");
		ID.setBounds(50 ,30, 100, 25);
		IDInput = new JTextField();
		IDInput.setBounds(90, 30, 183, 25);
		this.add(ID);
		this.add(IDInput);
		
		userTypeLabel = new JLabel("User type:");
		this.add(userTypeLabel);
		userTypeLabel.setBounds(35, 80, 60, 25);
	
		//Radio Buttons
		student = new JRadioButton("Student");    
		instructor = new JRadioButton("Instructor");  
		admin = new JRadioButton("Admin");
		student.setBounds(105, 80, 70, 25);   
		instructor.setBounds(190, 80, 80, 25);
		admin.setBounds(280, 80, 80, 25);
		bg = new ButtonGroup();
		bg.add(student);
		bg.add(instructor);
		bg.add(admin);
		student.setSelected(true);
		this.add(student);
		this.add(instructor);
		this.add(admin);
		
		
		unsuccessfullLogin = new JLabel();
		unsuccessfullLogin.setForeground(Color.red);
		unsuccessfullLogin.setBounds(100, 115, 160, 35);
		this.add(unsuccessfullLogin);
		
		
		loginButton = new JButton("Log in");
		loginButton.setBounds(125, 150, 100, 35);
		loginButton.addActionListener(this);
		this.add(loginButton);
		

		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent click) {
		if (click.getSource() == loginButton) {
			String sID = IDInput.getText().trim();
			Integer id = null;
			try {
				id = Integer.parseInt(sID);
			}catch(NumberFormatException e){
				unsuccessfullLogin.setText("Please enter in a number");
				return;
			}
			
			User user = null;
			if (student.isSelected()) {
				try {
					try {
						user = LMS_API.getStudent(Integer.toString(id));
					} catch (NumberFormatException | InvalidEmailException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {}
				if (user == null)
					unsuccessfullLogin.setText("Invalid ID");
				else {
					try {
						try {
							new StudentHomePage((Student) user);
						} catch (InvalidEmailException e) {
							e.printStackTrace();
						}
					} catch (NumberFormatException | FileNotFoundException | InvalidFileFormatException | ParseException
							| InvalidRatingValueException e) {
						e.printStackTrace();
					}
					unsuccessfullLogin.setText("");
				}
			}
			
			else if (instructor.isSelected()) {
				try {
					try {
						user = LMS_API.getInstructor(Integer.toString(id));
					} catch (NumberFormatException | InvalidEmailException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {}
				if (user == null)
					unsuccessfullLogin.setText("Invalid ID");
				else {
					try {
						try {
							new InstructorHomePage((Instructor) user);
						} catch (NumberFormatException | InvalidEmailException e) {
							e.printStackTrace();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					unsuccessfullLogin.setText("");
				}
			}
			
			else {
				try {
					try {
						user = LMS_API.getAdmin(Integer.toString(id));
					} catch (NumberFormatException | InvalidEmailException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {}
				if (user == null)
					unsuccessfullLogin.setText("Invalid ID");
				else {
					new AdminHomePage((Administrator) user);
					unsuccessfullLogin.setText("");
				}
			}
		}
	}

}

