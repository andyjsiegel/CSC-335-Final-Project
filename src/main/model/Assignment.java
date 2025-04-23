package main.model;

import java.util.Date;

public class Assignment {
    private Date dueDate;
    private String title;
    private String description;

    private double points;
    private double maxPoints;
    
    // An assignment would look something like this:
    // Final Project (name) due on April 29 (due date) 
    // Final class project incorporating everything learned (Description)
    // Points: 		0 (gained default is 0) / 150 (totalPoints)
    
    
    public Assignment(String title, String description, double maxPoints) {
        this.description = description;
        this.title = title;
        this.maxPoints = maxPoints;
        this.points = -1; // <0 score means not graded. 
    }
    
    public String getTitle() {
    	return title;
    }
    
    public void setAssignmentDescription(String description) {
    	this.description = description;
    }
    
    public void setGradeTo100() {
        this.points = this.maxPoints;
    }
 
    public void setGrade(Double grade) {
    	this.points = grade;
    }
    
    public double getGrade() {
    	return this.points;
    }

    public double getMaxPoints() {
        return maxPoints;
    } 
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Assignment other = (Assignment) obj;
        return title.equals(other.title) && dueDate.equals(other.dueDate) && points == other.points; 
    }
}