package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import Course.Course;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import Users.Student;

@SuppressWarnings("serial")
public class SelectViewReviewCourses extends JFrame  implements ActionListener{
	private JButton submit;
	JTextField reviewContent;
	private JComboBox<String> CourseInput;
	
	private Student student;
	private ArrayList<Course> courses = new ArrayList<Course>();
	static String SelectedCourse;

	public SelectViewReviewCourses (Student user, CreateOrViewReview PPage) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		student = user;
		courses = LMS_API.coursesRegisteredByStudent(student);
		this.setTitle("View Review page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(470, 330);
		this.setLayout(null);
		String[] courseNames = new String[courses.size()];
		for(int i=0; i<courses.size(); i++) {
			courseNames[i] = courses.get(i).getCourseTitle();
		}
		CourseInput = new JComboBox<String>(courseNames);
		CourseInput.setBounds(180, 40, 120, 30); 
		this.add(CourseInput);
		this.setVisible(true);
		SelectedCourse = CourseInput.getItemAt(CourseInput.getSelectedIndex()).trim();
		//System.out.println(SelectedCourse);
		submit=new JButton("View Review");
		submit.setBounds(80, 200, 130, 17);
		submit.addActionListener(this);
		this.add(submit);
		
		
	}
	      
	
	//text field, submit button, 4 * scales, choose course
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==submit) {
			SelectedCourse = CourseInput.getItemAt(CourseInput.getSelectedIndex()).trim();
			try {
				try {
					new ViewReviewPage(student,this, LMS_data.LMS_API.getCourseFromName(SelectedCourse));
				} catch (InvalidEmailException e1) {
					e1.printStackTrace();
				}
			} catch (NumberFormatException | FileNotFoundException | ParseException | InvalidRatingValueException e1) {
				e1.printStackTrace();
			}
		}
		
		
		
	}
	
}
