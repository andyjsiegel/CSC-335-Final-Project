package main.model;

import java.util.Date;

public class AssignmentGrade {
    private Assignment assignment;
    private double pointsEarned;
    private double pointsPossible;
    private String category;

    public AssignmentGrade(Assignment assignment, double pointsEarned, double pointsPossible, String category) {
        this.assignment = assignment;
        this.pointsEarned = pointsEarned;
        this.pointsPossible = pointsPossible;
        this.category = category;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public double getPointsEarned() {
        return pointsEarned;
    }

    public double getPointsPossible() {
        return pointsPossible;
    }

    public String getCategory() {
        return category;
    }

    // Returns the percentage score for this assignment grade.
    public double getPercentage() {
        if (pointsPossible == 0) return 0;
        return pointsEarned / pointsPossible;
    }
    
}