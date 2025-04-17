package main.model;

import java.util.Date;

public class Assignment {
    private String title;
    private Date dueDate;
    private String category;
    private double maxPoints;
    private Double score = null;

    public Assignment(String title, String category, double maxPoints) {
        this.category = category;
        this.title = title;
        this.maxPoints = maxPoints;
    }

    //TODO: remove this placeholder method
    public void setGradeTo100() {
        grade(maxPoints);
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public void grade(double score) {
        this.score = score;
    }

    public Double getPointsEarned() {
        return score;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public String getGrade() {
        if(score == null) {
            return "";
        }
        return score / maxPoints * 100 + "%";
    }
}
