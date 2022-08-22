package TodoList;

import Users.Student;
import java.io.IOException;
import Data.Database;
import Interfaces.*;

public class TodoListHandler implements Notifier, Uploadable{
	private Task task;
	private Student student;
	
	public TodoListHandler(Task t, Student st) {
		this.student=st;
		this.task=t;
		st.getTasks().add(t);
	}
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	public void uploadToDB() throws IOException {
		Database.insertTask(task);
	}
	
	@Override
	public void notifyUser() {
		//handled in GUI
	}
}
