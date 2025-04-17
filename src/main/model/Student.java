package main.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Student extends User {
	
    private ArrayList<Course> coursesTaken;
    
    public Student(String username, String password, String email, String name, boolean isHashed) {
        super(username, password, email, name, isHashed);
		coursesTaken = new ArrayList<Course>();
    }
    

	public static Comparator<Student> sortByFirstName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.username.compareToIgnoreCase(s2.username);
				return nameCompare;
			}
		};
	}

	public ArrayList<Course> getCoursesForDay(Days day) {
		return coursesTaken;
	}
}