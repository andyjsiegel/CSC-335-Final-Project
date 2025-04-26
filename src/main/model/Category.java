package main.model;

import java.util.ArrayList;

public class Category {
    private int points;
    private String name;
    private int dropLowest;
    private ArrayList<Assignment> assignments;
    
    // the expPointsPer represents the expected points for an assignment in the category, like a default. 
    // for CSC 335 SP25, a quiz would have this field be set to 20.
    private int expPointsPer; 

    public Category(String name, int points, int expPointsPer) {
        this.name = name;
        this.points = points;
        this.expPointsPer = expPointsPer;
        this.assignments = new ArrayList<Assignment>();
    }

    public void addDefaultAssignments() {
        /** First gets a name of the assignment from the category and trims off the "s" if it exists
         *  Homeworks -> Homework, Final Exam -> Final Exam
         *  Calculates the number of assignments based on the points/expPointsPer; one final exam worth the entire category = 1 assignment; projects worth 50 pts each in a 250 pt category = 5 projects
         *  Then adds that many assignments to the category with the iterator number if there's more than one
         */
        String defaultName = this.name;
        if(this.name.endsWith("s")) {
            defaultName = this.name.substring(0, this.name.length() - 1);
        }
        for(int i = 0; i < points/expPointsPer; i++) {
            if(points/expPointsPer == 1) {
                this.addAssignment(new Assignment(defaultName, "Default Description", expPointsPer));
            } else {
                this.addAssignment(new Assignment(defaultName + " " + (i+1), "Default Description " + i, expPointsPer));
            }
        }
    }

    public int getExpectedPoints() {
        return expPointsPer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(Assignment assignment) {
        this.assignments.add(assignment);
    }
    
    public int getDropLowest() { 
    	return dropLowest; 
   	}
    
    public void setDropLowest(int d) { 
    	this.dropLowest = d; 
    }
    
}
