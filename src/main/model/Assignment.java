package main.model;

import java.util.Date;

public class Assignment {
    private Date dueDate;
    private String name;
    private String description;
    private String assignmentTotalPoints;
    private String points;
    
    // An assignment would look something like this:
    // Final Project (name) due on April 29 (due date) 
    // Final class project incorporating everything learned (Description)
    // Points: 		0 (gained default is 0) / 150 (totalPoints)
    
    public Assignment() {}
    
    public void setAssignmentName(String name) {
    	this.name = name;
    }
    
    
    public void setAssignmentDescription(String description) {
    	this.description = description;
    }
    
    
    public void setAssignmentTotalPoints(String totalPoints) {
    	this.assignmentTotalPoints = totalPoints;
    }
    
   
    public String getTotalPoints() {
    	return this.assignmentTotalPoints;
    }
 
    public void setGrade(String grade) {
    	this.points = grade;
    }
    
    public String getGrade() {
    	return this.points;
    }
}
