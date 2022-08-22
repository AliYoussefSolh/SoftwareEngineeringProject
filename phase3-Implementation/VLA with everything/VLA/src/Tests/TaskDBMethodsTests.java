package Tests;

import static org.junit.Assert.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import Data.Database;
import Exceptions.InvalidEmailException;
import Exceptions.InvalidRatingValueException;
import LMS_data.LMS_API;
import TodoList.Task;
import Users.Student;

public class TaskDBMethodsTests {

   @Test
   void CheckInsertTask() throws IOException, InvalidEmailException  {
    	Student s = new Student("Ali Solh",202000888,"ali.solh@lau.edu");
		Task t = new Task("task txt is nothing","11-05-2022",s);
		t.setTaskID(202001);
		String expAddedLineOnFile = String.join(" | ",
				Integer.toString(t.getTaskID()),
				t.getTaskText(),
				t.getDeadline().toString(),
				Boolean.toString(t.isComplete()),
				Integer.toString(t.getStudent().getID()));
		Database.insertTask(t); //insert the task
		//check if task added to the file by reading last line
		Scanner scan = new Scanner(new File("./src/Data/Tasks.txt"));
		String lineWrittenToFile = "";
		while (scan.hasNextLine()) {
			lineWrittenToFile = scan.nextLine();
		}
		scan.close();
		assertEquals(expAddedLineOnFile, lineWrittenToFile);
    }

    @Test
    void CheckReadingGetAllTasksIDs() throws FileNotFoundException {
    	Scanner scan = new Scanner(new File("./src/Data/Tasks.txt"));
    	ArrayList<Integer> expectedList = new ArrayList<Integer>();
    	while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] args = line.split(" | ");
			expectedList.add(Integer.parseInt(args[0]));
		}
    	ArrayList<Integer> methodList = Database.getAllTaskIDs();
    	assertEquals(expectedList, methodList);
    }
    
    @Test
    void CheckReadingGetAllTasksIDsNotNull() throws FileNotFoundException {
    	ArrayList<Integer> methodList = Database.getAllTaskIDs();
    	assertNotNull(methodList);
    }    

    @Test
    void checkDelete() throws InvalidEmailException, IOException, NumberFormatException, ParseException, InvalidRatingValueException {
    	Student s = new Student("Ali Solh",202000888,"ali.solh@lau.edu");
		Task t = new Task("task txt is nothing","11-05-2022",s);
		t.setTaskID(202002);
		String expAddedLineOnFile = String.join(" | ",
				Integer.toString(t.getTaskID()),
				t.getTaskText(),
				t.getDeadline().toString(),
				Boolean.toString(t.isComplete()),
				Integer.toString(t.getStudent().getID()));
		Database.insertTask(t);  //insert the task ==> tested above
		Database.deleteTask(t); //delete it
		Scanner scan = new Scanner(new File("./src/Data/Tasks.txt"));
		String lineWrittenToFile = "";
		while (scan.hasNextLine()) {
			lineWrittenToFile =scan.nextLine();
		}
		assertNotEquals(expAddedLineOnFile, lineWrittenToFile);
    }

    //check getTaskStudent()
    @Test
    void CheckGetTaskByStudent() throws FileNotFoundException, NumberFormatException, ParseException, InvalidRatingValueException, InvalidEmailException {
    	Scanner scan = new Scanner(new File("./src/Data/Tasks.txt"));
    	ArrayList<Task> expectedList = new ArrayList<Task>();
    	Student s = LMS_API.getStudent("202004412");
		int studentID = s.getID();
		while (scan.hasNext()) {
			Task t = Database.parseTask(scan.nextLine());
			if (t.getStudent().getID() == studentID)
				expectedList.add(t);
		}
    	ArrayList<Task> methodList = Database.selectStudentTasks(s);
    	for (int i=0; i<methodList.size(); i++) {
    		assertEquals(expectedList.get(i).getDeadline(), methodList.get(i).getDeadline());
    		assertEquals(expectedList.get(i).getTaskID(), methodList.get(i).getTaskID());
    		assertEquals(expectedList.get(i).getTaskText(), methodList.get(i).getTaskText());
    	}
    }
    
    @Test
    void CheckModificationNoteCompleteness() throws IOException, InvalidEmailException, NumberFormatException, ParseException, InvalidRatingValueException  {
    	String idTest = "1040357616"; //taken from file and has state false
    	Scanner scan = new Scanner(new File("./src/Data/Tasks.txt")); 
 		String gettingTaskFromDB = ""; 
 		Task t = null;
 		//initially is complete==false; modify to true
 		while (scan.hasNextLine()) {
 			gettingTaskFromDB = scan.nextLine(); 
 			if (gettingTaskFromDB.split(" \\| ")[0].equals(idTest)) {
 			    t = Database.parseTask(gettingTaskFromDB);
 			    break;
 			}
 		}
 		scan.close(); 

 		Database.modifyTaskCompleteness(t, true);
 		
 		Scanner sc = new Scanner(new File("./src/Data/Tasks.txt"));
 		String modifiedTaskInDB = "";
 		while (sc.hasNextLine()) {
 			modifiedTaskInDB = sc.nextLine(); 
 			if (modifiedTaskInDB.split(" \\| ")[0].equals(idTest)) {
 			    t = Database.parseTask(modifiedTaskInDB);
 			    break;
 			}
 		}
 		sc.close();
 		assertEquals("true", modifiedTaskInDB.split(" \\| ")[3]);
     }
    
}
