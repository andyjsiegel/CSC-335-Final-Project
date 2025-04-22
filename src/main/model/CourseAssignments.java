package main.model;


import java.util.*;

public class CourseAssignments implements Iterable<Assignment>{

	private ArrayList<Assignment> courseAssignments;
	
	
	public CourseAssignments() {
		this.courseAssignments = new ArrayList<>();
	}

	public int size() {
		return courseAssignments.size();
	}

	// need to pass a copy first 
	public void add(Assignment assignment) {
		courseAssignments.add(assignment);
	}
	

	public void remove(Assignment assignment) {
		courseAssignments.remove(assignment);
	}
	

	@Override
	public Iterator<Assignment> iterator() {
		return this.courseAssignments.iterator();
	}
	
	
	
	
}
