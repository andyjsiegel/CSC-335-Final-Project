package main.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;


public class StudentList implements Iterable<Student> {
	// Technically a course has a student list, which has students innit 
	// kinda like the same idea of Card/CardStack/Game, but in this case 
	// we have 					Student/StudentList/Course  :)
	
	private ArrayList<Student> internalStudentList;
	
	
	public StudentList() {
		this.internalStudentList = new ArrayList<Student>();
	}

	public StudentList(StudentList other) {
		this.internalStudentList = new ArrayList<Student>(other.internalStudentList);
	}
	
	public void add(Student student) {
		internalStudentList.add(student); 
	}

	
	public void remove(Student student) {
		internalStudentList.remove(student);
	}
	
	
    public void sortByFirstName() {
        Collections.sort(internalStudentList, Student.sortByFirstName());
    }

    
	@Override
	public Iterator<Student> iterator() {
		return this.internalStudentList.iterator();
	}
	
	public boolean isEmpty() {
		return this.internalStudentList.isEmpty();
	}

	public int size() {
		return this.internalStudentList.size();
	}

	public void merge(StudentList other) {
		this.internalStudentList.addAll(other.internalStudentList);
		HashSet<Student> uniqueStudents = new HashSet<>();
		uniqueStudents.addAll(this.internalStudentList);
		this.internalStudentList.clear();
		this.internalStudentList.addAll(uniqueStudents);
	}
	
	
}
