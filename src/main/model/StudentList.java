package main.model;

import java.util.*;


public class StudentList implements Iterable<Student>{
	// Technically a course has a student list, which has students innit 
	// kinda like the same idea of Card/CardStack/Game, but in this case 
	// we have 					Student/StudentList/Course  :)
	
	private ArrayList<Student> studentList;
	
	
	public StudentList() {
		this.studentList = new ArrayList<Student>();
	}
	
	public void addStudent(Student student) {
		studentList.add(student); 
	}

	
	public void removeStudent(Student student) {
		studentList.remove(student);
	}
	
	
    public void sortByFirstName() {
        Collections.sort(studentList, Student.sortByFirstName());
    }

    
	@Override
	public Iterator<Student> iterator() {
		return this.studentList.iterator();
	}
	
	
	
	
}
