package GUI;

import Users.Administrator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import javax.swing.*;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;

@SuppressWarnings("serial")
public class ValidateReviewPage extends JFrame {
	private Administrator admin; 
	private JLabel head, nothingHere;
	private ImageIcon checked, unchecked;
	JCheckBox[] isValidNote = null;
	
	public ValidateReviewPage(Administrator a, AdminHomePage adminHomePage) throws NumberFormatException, 
	FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException{
		this.admin = a;

		this.setTitle("Validate Review Page");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		adminHomePage.setVisible(false);
		admin.setReviewsToEvaluate(Database.getAllReviews()); 
		
		if (admin.getReviewsToEvaluate().size() == 0) {
			JPanel noReviews = new JPanel(new GridLayout(1, 1));
			noReviews.setPreferredSize(new Dimension(400, 200));
			nothingHere = new JLabel("No reviews yet....", SwingConstants.CENTER);
			nothingHere.setFont(new Font(null, Font.PLAIN, 14));
			noReviews.add(nothingHere);
			this.add(noReviews, BorderLayout.SOUTH);
			this.pack();
		}
		else {
			JPanel header = new JPanel(new GridLayout(1, 1));
			header.setPreferredSize(new Dimension(400, 40));
			head = new JLabel("Reviews to validate:", SwingConstants.CENTER);
			head.setFont(new Font(null, Font.ROMAN_BASELINE, 14));
			head.setForeground(Color.blue);
			head.setBounds(0, 10, 500, 30);
			header.add(head);
			this.add(header, BorderLayout.NORTH);
			this.pack();
			checked = new ImageIcon("./src/GUI/true.png");
			unchecked = new ImageIcon("./src/GUI/false.png");
			isValidNote = new JCheckBox[admin.getReviewsToEvaluate().size()];
			JPanel panel = new JPanel(new GridLayout((admin.getReviewsToEvaluate().size() % 2 == 0 ? 
					admin.getReviewsToEvaluate().size() /2 : admin.getReviewsToEvaluate().size() /2 + 1), 2));
			for (int j = 0; j < admin.getReviewsToEvaluate().size(); j++) {
				String description = "<html>" + admin.getReviewsToEvaluate().get(j).toString().replace("\n", "<br>");
				isValidNote[j] = new JCheckBox(description);
				isValidNote[j].setIcon(unchecked);
				isValidNote[j].setSelectedIcon(checked);
				panel.add(isValidNote[j]);
				isValidNote[j].setSelected(admin.getReviewsToEvaluate().get(j).isValid());
			}
			this.add(panel, BorderLayout.SOUTH);
			this.pack();
		}
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				for (int i = 0; i< admin.getReviewsToEvaluate().size(); i++) {
					try {
						Database.setReviewValidation(admin.getReviewsToEvaluate().get(i), isValidNote[i].isSelected());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				adminHomePage.setVisible(true);
		    }  
		});
						
		this.setVisible(true);
	}

}
