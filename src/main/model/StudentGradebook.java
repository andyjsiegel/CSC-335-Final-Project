package main.model;

import java.util.ArrayList;
import java.util.Collections;

public class StudentGradebook {

	/* 
	 * A student will have this class for each individual course they take
	 * This will added to a "Total grades" class and that class will belong to a student
	 */
	
	private double points;
	private int assignmentCount;
	private double classAverage;
	private double median;
	private ArrayList<Assignment> courseAssignments;
	private FinalGrades finalGrade;
	
	
	public StudentGradebook() {
		this.courseAssignments = new ArrayList<>();
	}
	

	// must pass a copy of Assignments to this first btw 
	public void addAssignment(Assignment assignment, String grade) {
		assignment.setGrade(grade);
		this.courseAssignments.add(assignment);
		this.points += Double.parseDouble(grade);
		this.assignmentCount++;
	}
	
	
	public void calculateAverage() {
		int totalPoints = 0;
		
		for (Assignment assignment : courseAssignments) {
			totalPoints += Double.parseDouble(assignment.getTotalPoints());
		}
		
		classAverage = (totalPoints / points) * 100;
	}
	
	
	// this should probably just calculate it automatically, and should just call it, we have the data either way so 
	public void setFinalGrade() {
		
		calculateAverage();
		double finalGrade = classAverage;
		
	    if (finalGrade >= 90) {
	        this.finalGrade = FinalGrades.A;
	    } else if (finalGrade >= 80) {
	    	this.finalGrade = FinalGrades.B;
	    } else if (finalGrade >= 70) {
	    	this.finalGrade =  FinalGrades.C;
	    } else if (finalGrade >= 60) {
	    	this.finalGrade = FinalGrades.D;
	    } else if (finalGrade >= 50) {
	    	this.finalGrade = FinalGrades.E;
	    } else {
	    	this.finalGrade = FinalGrades.F;
	    }
	    
	}

	// this is for the entire course of students, not just one stupid ahh ahh instructions ahhhhh
	public void calculateMedian() {

	    ArrayList<Double> grades = new ArrayList<>();
	    
	    for (Assignment assignment : courseAssignments) {
	        grades.add(Double.parseDouble(assignment.getGrade())); 
	    }

	    Collections.sort(grades);

	    int n = grades.size();
	    
	    double median;

	    if (n % 2 == 1) {
	        median = grades.get(n / 2);
	    } else {
	        median = (grades.get(n / 2 - 1) + grades.get(n / 2)) / 2.0;
	    }

	    this.median = median;
	}



}

