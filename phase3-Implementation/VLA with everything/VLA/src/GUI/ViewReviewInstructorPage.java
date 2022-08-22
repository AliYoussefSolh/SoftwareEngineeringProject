package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import Reviewing.Review;
import Users.Instructor;

@SuppressWarnings("serial")
public class ViewReviewInstructorPage extends JFrame{
	private JLabel nothingHere;
	static JLabel[] RatingButtons;
	
	public ViewReviewInstructorPage(Instructor i, InstructorHomePage p) throws NumberFormatException, 
	FileNotFoundException, ParseException, InvalidRatingValueException, InvalidEmailException {
		ArrayList<Review> Allreviews = Database.selectInstructorReviews(i);
		ArrayList<Review> reviews = new ArrayList<Review>();
		for (Review r : Allreviews) 
			if (r.isValid())
				reviews.add(r);
		
		int nbOfReviews = reviews.size();
		if ( nbOfReviews!= 0) {
			JPanel panel = new JPanel(new GridLayout((nbOfReviews %2 == 0 ? nbOfReviews/2  : nbOfReviews/2 + 1), 2));
			RatingButtons = new JLabel[nbOfReviews];
			for (int j = 0; j < nbOfReviews; j++) {
				String description = "<html>" + reviews.get(j).toString().replace("\n", "<br>");
				RatingButtons[j] = new JLabel(description);
				panel.add(RatingButtons[j]);
			}
			this.add(panel, BorderLayout.SOUTH);
			this.pack();
		}
		else {
			JPanel noAssignments = new JPanel(new GridLayout(1, 1));
			noAssignments.setPreferredSize(new Dimension(400, 200));
			nothingHere = new JLabel("No Reviews Yet....", SwingConstants.CENTER);
			nothingHere.setFont(new Font(null, Font.PLAIN, 14));
			noAssignments.add(nothingHere);
			this.add(noAssignments, BorderLayout.SOUTH);
			this.pack();
		}
		this.setVisible(true);
	}

}
