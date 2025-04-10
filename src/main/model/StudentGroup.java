package main.model;

import java.util.ArrayList;
import java.util.Iterator;

public class StudentGroup implements Iterable<Student>{

	private ArrayList<Student> studentGroup;

	
	public StudentGroup() {
		this.studentGroup = new ArrayList<Student>();
	}
	
	@Override
	public Iterator<Student> iterator() {
		return this.studentGroup.iterator();
	}

	
	
}
