package main.model;

import java.util.ArrayList;
import java.util.Collections;

public class StudentGradebook {

	/* 
	 * A student will have this class for each individual course they take
	 * This will added to a "Total grades" class and that class will belong to a student
	 */
	
	private double points;
	private double calculatedGrade;
	private double median;
	private String courseTitle;
	private ArrayList<Assignment> courseAssignments;
	private FinalGrades finalGrade;
	
	public StudentGradebook(String courseTitle) {
		this.courseAssignments = new ArrayList<>();
		this.courseTitle = courseTitle;
	}
	
	// must pass a copy of Assignments to this first btw 
	public void addAssignment(Assignment assignment, double grade) {
		assignment.setPointsEarned(grade);
		this.courseAssignments.add(assignment);
		this.points += grade;
	}
	
	public double calculateAverage() {
		int maxPoints = 0;
		
		for (Assignment assignment : courseAssignments) {
			maxPoints += assignment.getMaxPoints();
		}
		
		calculatedGrade = (points / maxPoints) * 100;
		return calculatedGrade;
	}
	
	
	public FinalGrades getFinalGrade() {
		
		calculateAverage();
		
	    if (calculatedGrade >= 90) {
	        this.finalGrade = FinalGrades.A;
	    } else if (calculatedGrade >= 80) {
	    	this.finalGrade = FinalGrades.B;
	    } else if (calculatedGrade >= 70) {
	    	this.finalGrade =  FinalGrades.C;
	    } else if (calculatedGrade >= 60) {
	    	this.finalGrade = FinalGrades.D;
	    } else {
			this.finalGrade = FinalGrades.E;
		}
	    return this.finalGrade;
	}

	// this is for the entire course of students, not just one stupid ahh ahh instructions ahhhhh
	public double calculateMedian() {

	    ArrayList<Double> grades = new ArrayList<>();
	    
	    for (Assignment assignment : courseAssignments) {
	        grades.add(assignment.getPointsEarned()); 
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
		return this.median;
	}

	public ArrayList<Assignment> getAssignments() {
		return new ArrayList<>(courseAssignments);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		StudentGradebook other = (StudentGradebook) obj;
		return courseTitle.equals(other.courseTitle) && points == other.points;
	}

}