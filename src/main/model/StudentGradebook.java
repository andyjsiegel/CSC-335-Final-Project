package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentGradebook {

	/* 
	 * A student will have this class for each individual course they take
	 * This will added to a "Total grades" class and that class will belong to a student
	 */
    private final Course course;       // ← new field
	private double points;
	private double calculatedGrade;
	private double median;
	private ArrayList<Assignment> courseAssignments;
	private FinalGrades finalGrade;

	
	// change constructor to take the Course
	public StudentGradebook(Course course) {
        this.course = course;
        this.courseAssignments = new ArrayList<>();
        this.points = 0.0;
    }

    /** Add a graded assignment (and accumulate its points). */
    public void addAssignment(Assignment assignment, double grade) {
        assignment.setPointsEarned(grade);
        this.courseAssignments.add(assignment);
        this.points += grade;
    }

    /** Add an ungraded assignment (e.g. when creating defaults). */
    public void addAssignment(Assignment assignment) {
        this.courseAssignments.add(assignment);
    }	

    /** 
     * Mode 1: simple points‐sum percentage (0–100). 
     */
    public double calculateAverage() {
        double totalPossible = courseAssignments.stream()
            .mapToDouble(Assignment::getMaxPoints)
            .sum();
        if (totalPossible == 0) return 0.0;
        return (points / totalPossible) * 100.0;
    }
    
    /** 
     * Mode 2: weighted average (0–100) using your GradingScheme's weights & drops. 
     */
    public double calculateWeightedAverage() {
        // wrap each graded Assignment into AssignmentGrade
        List<AssignmentGrade> grades = courseAssignments.stream()
            .filter(Assignment::isGraded)
            .map(a -> new AssignmentGrade(
                    a,
                    a.getPointsEarned(),
                    a.getMaxPoints(),
                    a.getCategory()
            ))
            .collect(Collectors.toList());

        GradingScheme scheme = course.getGradingScheme();
        // returns a fraction 0–1
        double fraction = scheme.calculateFinalGradeOption2(grades);
        return fraction * 100.0;
    }
    
    /**
     * Pick the right mode based on the course's CalculationMode.
     * @return final percentage 0–100
     */
    public double calculateFinalGrade() {
        if (course.getCalculationMode() == Course.CalculationMode.WEIGHTED) {
            return calculateWeightedAverage();
        } else {
            return calculateAverage();
        }
    }
	
    /**
     * Once the instructor clicks “Set Final Grades,” they call:
     *   gb.setFinalGrade(letter);
     * so this returns whatever letter was stored (or null if not yet set).
     */
    public FinalGrades getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(FinalGrades finalGrade) {
        this.finalGrade = finalGrade;
    }

    /** Raw median of points earned (not percentage). */
    public double calculateMedian() {
        List<Double> scores = courseAssignments.stream()
            .map(Assignment::getPointsEarned)
            .sorted()
            .collect(Collectors.toList());
        int n = scores.size();
        if (n == 0) return 0.0;
        if (n % 2 == 1) {
            return scores.get(n / 2);
        } else {
            return (scores.get(n/2 - 1) + scores.get(n/2)) / 2.0;
        }
    }

    /** Return a copy of all assignments for display/reporting. */
    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(courseAssignments);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StudentGradebook)) return false;
        StudentGradebook other = (StudentGradebook) obj;
        // compare by course identity and total points (you can adjust as needed)
        return course.equals(other.course)
            && Double.compare(points, other.points) == 0;
    }

    @Override
    public int hashCode() {
        // if you use equals, it’s good practice to override hashCode too
        int result = course.hashCode();
        long temp = Double.doubleToLongBits(points);
        result = 31 * result + (int)(temp ^ (temp >>> 32));
        return result;
    }
}