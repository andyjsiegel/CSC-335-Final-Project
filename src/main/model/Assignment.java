package main.model;

import java.util.Date;

public class Assignment {
    private String title;
    private Date dueDate;

    private String name;
    private String description;
    private String assignmentTotalPoints;
    private String points;
    
    private String category;
    private String title;
    private double maxPoints;
    
    // An assignment would look something like this:
    // Final Project (name) due on April 29 (due date) 
    // Final class project incorporating everything learned (Description)
    // Points: 		0 (gained default is 0) / 150 (totalPoints)
    
    public Assignment() {}
    
    public Assignment(String name, String description, String totalPoints) {
    	this.name = name;
    	this.description = description;
    	this.assignmentTotalPoints = totalPoints;
    	this.points = "0";
    }
    
    public Assignment(String title, String category, double maxPoints) {
        this.category = category;
        this.title = title;
        this.maxPoints = maxPoints;
    }
    
    public String getTitle() {
    	return name;
    }
    
    public String getName() {
    	return name;
    }
    public void setAssignmentName(String name) {
    	this.name = name;
    }
    
    
    public void setAssignmentDescription(String description) {
    	this.description = description;
    }
    

    public void setAssignmentTotalPoints(String totalPoints) {
    	this.assignmentTotalPoints = totalPoints;
    }
    
    public Double getPointsEarned() {
        return Double.parseDouble(assignmentTotalPoints);
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
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment other = (Assignment) obj;
        return name.equals(other.name) && dueDate.equals(other.dueDate) && assignmentTotalPoints.equals(other.assignmentTotalPoints);
    }



}
