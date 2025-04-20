package main.model;


import java.util.*;

public class CourseAssignments implements Iterable<Assignment>{

	private ArrayList<Assignment> courseAssignments;
	
	
	public CourseAssignments() {
		this.courseAssignments = new ArrayList<>();
	}


	// need to pass a copy first 
	public void addAssignment(Assignment assignment) {
		courseAssignments.add(assignment);
	}
	

	public void removeAssignment(Assignment assignment) {
		courseAssignments.remove(assignment);
	}
	

	@Override
	public Iterator<Assignment> iterator() {
		return this.courseAssignments.iterator();
	}
	
	
	
	
}
