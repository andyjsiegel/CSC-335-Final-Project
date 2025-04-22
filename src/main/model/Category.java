package main.model;

import java.util.ArrayList;

public class Category {
    private int points;
    private String name;
    private ArrayList<Assignment> assignments;
    
    // the expPoints represents the expected points for an assignment in the category, like a default. 
    // for CSC 335 SP25, a quiz would have this field be set to 20.
    private int expPoints; 

    public Category(String name, int points, int expPoints) {
        this.name = name;
        this.points = points;
        this.expPoints = expPoints;
        this.assignments = new ArrayList<Assignment>();
    }

    public int getExpectedPoints() {
        return expPoints;
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
}
