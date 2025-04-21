package main.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import main.model.*;
import java.util.*;
import java.util.Date;

public class GradingSchemeTest {
/*
    @Test
    public void testCalculateFinalGradeOption1() {
        // Create a dummy assignment using the existing Assignment constructor.
        Date now = new Date();
        Assignment dummyAssignment = new Assignment(now, "Dummy Assignment");

        List<AssignmentGrade> grades = new ArrayList<>();
        // Two exam scores: 80/100 and 90/100.
        grades.add(new AssignmentGrade(dummyAssignment, 80, 100, "Exam"));
        grades.add(new AssignmentGrade(dummyAssignment, 90, 100, "Exam"));

        // Option 1: (80+90) / (100+100) = 170 / 200 = 0.85.
        GradingScheme scheme = new GradingScheme(new HashMap<>(), new HashMap<>());
        double result = scheme.calculateFinalGradeOption1(grades);

        assertEquals(0.85, result, 0.001, "Final grade option 1 should be 0.85");
    }

    @Test
    public void testCalculateFinalGradeOption2() {
        Date now = new Date();
        Assignment dummyAssignment = new Assignment(now, "Dummy Assignment");

        // Define category weights and drop counts.
        Map<String, Double> weights = new HashMap<>();
        weights.put("Exam", 0.7);
        weights.put("Homework", 0.3);

        Map<String, Integer> drops = new HashMap<>();
        drops.put("Homework", 1);  // Drop the lowest homework grade.

        GradingScheme scheme = new GradingScheme(weights, drops);
        List<AssignmentGrade> grades = new ArrayList<>();

        // Exams: two scores: 80/100 and 90/100 → average = 0.85.
        grades.add(new AssignmentGrade(dummyAssignment, 80, 100, "Exam"));
        grades.add(new AssignmentGrade(dummyAssignment, 90, 100, "Exam"));

        // Homework: three scores: 70/100 (0.70), 100/100 (1.0), and 60/100 (0.60).
        grades.add(new AssignmentGrade(dummyAssignment, 70, 100, "Homework"));
        grades.add(new AssignmentGrade(dummyAssignment, 100, 100, "Homework"));
        grades.add(new AssignmentGrade(dummyAssignment, 60, 100, "Homework"));

        // Calculations for Option 2:
        // Exams: average = (0.8 + 0.9) / 2 = 0.85 → weighted contribution = 0.85 * 0.7 = 0.595.
        // Homework: drop lowest (0.60); remaining average = (0.70 + 1.0) / 2 = 0.85 → weighted contribution = 0.85 * 0.3 = 0.255.
        // Total final grade = 0.595 + 0.255 = 0.85.
        double result = scheme.calculateFinalGradeOption2(grades);
        assertEquals(0.85, result, 0.001, "Final grade option 2 should be 0.85");
        
    }
*/
}
