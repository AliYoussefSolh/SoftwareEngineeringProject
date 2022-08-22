package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.text.ParseException;
import javax.swing.*;

import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import Users.Administrator;

@SuppressWarnings("serial")
public class AdminHomePage extends JFrame  implements ActionListener{
	private Administrator admin;
	private JButton ReviewApproval;
	
	public AdminHomePage(Administrator id) {
		admin = id;
		this.setTitle("Admin home page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(450, 200);
		this.setLayout(null);
		ImageIcon image = new ImageIcon("lau-logo.jpg");
		this.setIconImage(image.getImage());
		
		ReviewApproval = new JButton();
		ReviewApproval.setBounds(150, 60, 150, 35);
		ReviewApproval.addActionListener(this);
		this.add(ReviewApproval);
		ReviewApproval.setText("Approve Reviews");
		this.setVisible(true);
	}
	
	public Administrator getAdmin() {
		return admin;
	}
	public void setAdmin(Administrator i) {
		this.admin = i;
	}
	
	public void actionPerformed(ActionEvent click) {
		if(click.getSource() == ReviewApproval) {
			try {
				try {
					new ValidateReviewPage(admin, this);
				} catch (InvalidEmailException e) {
					e.printStackTrace();
				}
			} catch (NumberFormatException | FileNotFoundException | ParseException | InvalidRatingValueException e) {
				e.printStackTrace();
			}
		}
	}
}
