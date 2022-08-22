package TodoList;

import Users.Student;

public class Task {
	private String taskText;
	private String deadline;
	private boolean isComplete;
	private int TaskID;
	private Student student;

	public Task(String taskText, String deadline, Student s) {
		setTaskText(taskText);
		setDeadline(deadline);
		setStudent(s);
		setComplete(false);
	}

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public int getTaskID() {
		return TaskID;
	}
	public void setTaskID(int taskID) {
		TaskID = taskID;
	}

	public String getTaskText() {
		return taskText;
	}
	public void setTaskText(String taskText) {
		this.taskText = taskText;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public boolean isComplete() {
		return isComplete;
	}
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	@Override
	public String toString() {
		return "\n"+this.taskText+"\nDeadline: "+this.deadline;
	}
}
