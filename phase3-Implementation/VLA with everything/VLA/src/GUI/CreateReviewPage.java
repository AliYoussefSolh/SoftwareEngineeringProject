package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import Course.Course;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import Reviewing.Rating;
import Reviewing.Review;
import Reviewing.ReviewHandler;
import Users.Instructor;
import Users.Student;

@SuppressWarnings("serial")
public class CreateReviewPage extends JFrame implements ActionListener {
	private Student student;
	private SelectCourse userHomePage;
	private JLabel text,R1,R2,R3,R4;
	JComboBox<String> Rating1;
	private JButton submit;
	private JComboBox<String> Rating2, Rating3, Rating4;
	private JTextField ReviewInstructionsInput;
	static String[] ratings = {"1","2","3","4","5"};
	static Course course;
	static Instructor instructor;
	
	
	public CreateReviewPage (Student st, SelectCourse homePage) throws FileNotFoundException, NumberFormatException, InvalidEmailException {
		this.student = st;
		userHomePage = homePage;
		userHomePage.setVisible(false);
		
		this.setTitle("Add Review page");
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
		R1=new JLabel("Explanation");
		R4=new JLabel("Flexibilty");
		R2=new JLabel("Grading");
		R3=new JLabel("Communication");
		R1.setBounds(55,220,100,25);
		R2.setBounds(175,220,100,25);
		R3.setBounds(295,220,100,25);
		R4.setBounds(415,220,100,25);
		Rating1=new JComboBox<String>(ratings);
		Rating2=new JComboBox<String>(ratings);
		Rating3=new JComboBox<String>(ratings);
		Rating4=new JComboBox<String>(ratings);
		Rating1.setBounds(55, 250, 100, 25);
		Rating2.setBounds(175, 250, 100, 25);
		Rating3.setBounds(295, 250, 100, 25);
		Rating4.setBounds(415, 250, 100, 25);
		
		submit=new JButton("Submit Review");
		submit.setBounds(200, 400, 130, 17);
		submit.addActionListener(this);
		this.add(submit);
	
		this.add(R1);
		this.add(R2);
		this.add(R3);
		this.add(R4);
		this.add(Rating1);
		this.add(Rating2);
		this.add(Rating3);
		this.add(Rating4);
		String Dr_name="";
		for (Course c:LMS_API.coursesRegisteredByStudent(st)) {
			if (c.getCourseTitle().equals(homePage.SelectedCourse)) {
				Dr_name=c.getProfessor().getName();
				course=c;
				instructor=c.getProfessor();
				break;
			}
		}
			
		text = new JLabel("Writing Review about Dr "+Dr_name, SwingConstants.CENTER);
		text.setBounds(0, 40, 540, 30);
		ReviewInstructionsInput = new JTextField();
		ReviewInstructionsInput.setBounds(10, 120, 520, 100);
		this.add(text);
		this.add(ReviewInstructionsInput);
		
		this.setVisible(true);
	}	
	
	public void actionPerformed(ActionEvent click) {
		if (click.getSource()==submit) {
			String txt = ReviewInstructionsInput.getText().trim();
			int rr1=Integer.parseInt(Rating1.getItemAt(Rating1.getSelectedIndex()).trim());
			int rr2=Integer.parseInt(Rating2.getItemAt(Rating2.getSelectedIndex()).trim());
			int rr3=Integer.parseInt(Rating3.getItemAt(Rating3.getSelectedIndex()).trim());
			int rr4=Integer.parseInt(Rating4.getItemAt(Rating4.getSelectedIndex()).trim());
			Rating r1;
			try {
				r1 = new Rating("Explanation",rr1);
				Rating r2=new Rating("Grading",rr2);
				Rating r3=new Rating("Flexibilty",rr3);
				Rating r4=new Rating("Communication",rr4);
				Rating[] array= {r1,r2,r3,r4};
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
				String date = sdf.format(new Date());
				Review RR = new Review(txt,date,array,instructor,student,course,false);
				ReviewHandler rh = new ReviewHandler(RR);
				rh.uploadToDB();
			} catch (InvalidRatingValueException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			this.dispose();
		}
		
	}
}
