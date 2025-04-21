package main.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Student extends User {
	
    private ArrayList<Course> coursesTaken;
    private StudentGradebook gradebook;
    
    public Student(String username, String password, String name, boolean isHashed) {
        super(username, password, name, isHashed);
        this.gradebook = new StudentGradebook();
    }
    
    public String getName() {
    	return name;
    }

	//copy constructor
    public Student(Student other) {
        super(other.username, other.password, other.name, true);
        this.gradebook = new StudentGradebook();
    }

	public static Comparator<Student> sortByFirstName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.username.compareToIgnoreCase(s2.username);
				return nameCompare;
			}
		};
	}
	
	public static Comparator<Student> sortByUsername() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.username.compareToIgnoreCase(s2.name);
				return nameCompare;
				}
		};
	}
	
	@Override
	public String toString() {
		return "Student " + name + " with username " + username;
	}
	public ArrayList<Course> getCoursesForDay(Days day) {
		return coursesTaken;
	}
}