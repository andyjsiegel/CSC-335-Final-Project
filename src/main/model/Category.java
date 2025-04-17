package main.model;

import java.util.ArrayList;

public class Category {
    private int points;
    private String name;
    private ArrayList<Assignment> assignments;

    public Category(String name, int points) {
        this.name = name;
        this.points = points;
        this.assignments = new ArrayList<Assignment>();
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
