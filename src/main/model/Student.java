package main.model;

import java.util.ArrayList;
import java.util.Comparator;




public class Student extends User {
	
    private ArrayList<Course> coursesTaken;
    
    public Student(String username, String password, String name, boolean isHashed) {
        super(username, password, name, isHashed);
    }
    
    
    
	public static Comparator<Student> sortByFirstName() {
		return new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				int nameCompare = s1.name.compareToIgnoreCase(s2.name);
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
	
	
}