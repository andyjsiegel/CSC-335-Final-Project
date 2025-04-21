package main.model;

import java.util.*;
import java.util.stream.Collectors;

public class GradingScheme {
    private Map<String, Double> categoryWeights;
    private Map<String, Integer> dropCounts;

    public GradingScheme(Map<String, Double> categoryWeights, Map<String, Integer> dropCounts) {
        this.categoryWeights = categoryWeights;
        this.dropCounts = dropCounts;
    }

    // Option 1: Final Grade = Total Points Earned / Total Points Possible.
    public double calculateFinalGradeOption1(List<AssignmentGrade> assignmentGrades) {
        double totalEarned = 0;
        double totalPossible = 0;
        for (AssignmentGrade ag : assignmentGrades) {
            totalEarned += ag.getPointsEarned();
            totalPossible += ag.getPointsPossible();
        }
        if (totalPossible == 0) return 0;
        return totalEarned / totalPossible;
    }

    // Option 2: Weighted average calculation based on category weights with dropped low scores.
    public double calculateFinalGradeOption2(List<AssignmentGrade> assignmentGrades) {
        double finalGrade = 0.0;

        // For each category defined in the grading scheme.
        for (String category : categoryWeights.keySet()) {
            // Filter assignment grades by category.
            List<AssignmentGrade> categoryGrades = assignmentGrades.stream()
                    .filter(ag -> ag.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());

            // Sort by percentage score (lowest first).
            Collections.sort(categoryGrades, Comparator.comparingDouble(AssignmentGrade::getPercentage));

            // Drop the lowest scores as defined by the dropCounts.
            int dropCount = dropCounts.getOrDefault(category, 0);
            if (categoryGrades.size() > dropCount) {
                categoryGrades = categoryGrades.subList(dropCount, categoryGrades.size());
            }

            // Calculate the average percentage for the remaining assignments.
            double catTotal = 0.0;
            for (AssignmentGrade ag : categoryGrades) {
                catTotal += ag.getPercentage();
            }
            double catAverage = (categoryGrades.size() > 0 ? catTotal / categoryGrades.size() : 0);

            // Multiply by the weight for this category and add to the final grade.
            finalGrade += catAverage * categoryWeights.get(category);
        }

        return finalGrade;
    }

    // Getters (setters may be added if needed)
    public Map<String, Double> getCategoryWeights() {
        return categoryWeights;
    }

    public Map<String, Integer> getDropCounts() {
        return dropCounts;
    }
}
