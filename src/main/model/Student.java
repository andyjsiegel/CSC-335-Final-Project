package main.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Student extends User {
	
    private ArrayList<Course> coursesTaken;
    private StudentGradebook gradebook;
    
    public Student(String username, String password, String firstname, String lastname, String email, boolean isHashed) {
        super(username, password, firstname, lastname, email, isHashed);
    }

	//copy constructor
    public Student(Student other) {
        super(other.username, other.password, other.firstname, other.lastname, other.email,true);
        this.gradebook = new StudentGradebook();
    }

	public static Comparator<Student> sortByFirstName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.firstname.compareToIgnoreCase(s2.firstname);
				return nameCompare;
			}
		};
	}
	
	public static Comparator<Student> sortByUsername() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.username.compareToIgnoreCase(s2.username);
				return nameCompare;
				}
		};
	}

	public String getFullName() {
		return this.firstname + " " + this.lastname;
	}

	@Override
	public String toString() {
		return "Student " + this.firstname + " " + this.lastname + " with username " + username;
	}
    
	public ArrayList<Course> getCoursesForDay(Days day) {
		return coursesTaken;
	}
}