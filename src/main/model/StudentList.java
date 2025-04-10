package main.model;

import java.util.Collections;
import java.util.*;


public class StudentList {
	// Technically a course has a student list, which has students innit 
	// kinda like the same idea of Card/CardStack/Game, but in this case 
	// we have 					Student/StudentList/Course  :)
	
	private ArrayList<Student> studentList;
	
	
	public StudentList() {
		this.studentList = new ArrayList<Student>();
	}
	
	public void addStudent(Student student) {
		studentList.add(student); // no order in particular i guess?? idk
	}

    public void sortByFirstName() {
        Collections.sort(studentList, Student.sortByFirstName());
    }
	
	
	
	
}
