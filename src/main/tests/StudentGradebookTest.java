package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;
import java.util.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;


// StudentGradebookTest
class StudentGradebookTest {
    StudentGradebook gb;
    Assignment a1, a2;

    @BeforeEach
    void setup() {
        gb = new StudentGradebook("Course");
        a1 = new Assignment("A1","D1",10);
        a2 = new Assignment("A2","D2",20);
        gb.addAssignment(a1, 8.0);
        gb.addAssignment(a2, 16.0);
    }

    @Test
    void testCalculateAverageAndFinal() {
        gb.calculateAverage();
        // just ensure no exception
        gb.setFinalGrade();
    }

    @Test
    void testCalculateMedian() {
        gb.calculateMedian();
        // no exception
    }
    
    @Test
    void testCalculateAverageAndFinalGrade() throws Exception {
        StudentGradebook sg = new StudentGradebook("TestCourse");
        Assignment a1 = new Assignment("A1", "desc", 10);
        Assignment a2 = new Assignment("A2", "desc", 20);

        sg.addAssignment(a1, 8);
        sg.addAssignment(a2, 16);
        sg.calculateAverage();

        // Reflectively read the private field classAverage
        Field avgField = StudentGradebook.class.getDeclaredField("classAverage");
        avgField.setAccessible(true);
        double avg = (double) avgField.get(sg);

        assertEquals(125.0, avg, 1e-6,
            "Expected classAverage = (30 / 24) * 100 ≈ 125.0");

        // Now set and verify the finalGrade
        sg.setFinalGrade();
        Field fgField = StudentGradebook.class.getDeclaredField("finalGrade");
        fgField.setAccessible(true);
        FinalGrades fg = (FinalGrades) fgField.get(sg);

        assertEquals(FinalGrades.A, fg,
            "With average ≥ 90, finalGrade should be A");
    }

    /**
     * Test calculateMedian() on an odd number of grades.
     * Grades: [8, 14, 24] → median = 14
     */
    @Test
    void testCalculateMedianOdd() throws Exception {
        StudentGradebook sg = new StudentGradebook("Course");
        sg.addAssignment(new Assignment("A1","d", 10), 8);
        sg.addAssignment(new Assignment("A2","d", 20), 14);
        sg.addAssignment(new Assignment("A3","d", 30), 24);

        sg.calculateMedian();

        Field medianField = StudentGradebook.class.getDeclaredField("median");
        medianField.setAccessible(true);
        double median = (double) medianField.get(sg);

        assertEquals(14.0, median, 1e-6,
            "Median of [8,14,24] should be 14");
    }

    /**
     * Test calculateMedian() on an even number of grades.
     * Grades: [8, 10, 14, 24] → median = (10 + 14) / 2 = 12
     */
    @Test
    void testCalculateMedianEven() throws Exception {
        StudentGradebook sg = new StudentGradebook("Course");
        sg.addAssignment(new Assignment("A1","d", 10), 8);
        sg.addAssignment(new Assignment("A2","d", 20), 14);
        sg.addAssignment(new Assignment("A3","d", 30), 24);
        sg.addAssignment(new Assignment("A4","d", 40), 10);

        sg.calculateMedian();

        Field medianField = StudentGradebook.class.getDeclaredField("median");
        medianField.setAccessible(true);
        double median = (double) medianField.get(sg);

        assertEquals(12.0, median, 1e-6,
            "Median of [8,10,14,24] should be (10+14)/2 = 12");
    }
    
    /**
     * Ensures getAssignments() returns a new List each time,
     * so that mutating the returned list does not affect the
     * gradebook’s internal assignments.
     */
    @Test
    void testGetAssignmentsReturnsCopy() {
        StudentGradebook sg = new StudentGradebook("DemoCourse");
        Assignment a1 = new Assignment("HW1", "desc", 10);
        Assignment a2 = new Assignment("HW2", "desc", 20);

        // add two assignments
        sg.addAssignment(a1, 8);
        sg.addAssignment(a2, 16);

        // first snapshot
        java.util.List<Assignment> first = sg.getAssignments();
        assertEquals(2, first.size(), "Should have 2 assignments initially");

        // mutate the returned list
        first.remove(0);

        // second snapshot from fresh call
        java.util.List<Assignment> second = sg.getAssignments();
        assertEquals(2, second.size(),
            "Removing from the first snapshot must not affect the gradebook’s internal list");
    }

    @Test
    void testSetFinalGrade() throws Exception {
        StudentGradebook sg = new StudentGradebook("DummyCourse") {
            @Override
            public void calculateAverage() {
                // do nothing
            }
        };

        Field avgField = StudentGradebook.class.getDeclaredField("classAverage");
        avgField.setAccessible(true);
        Field fgField  = StudentGradebook.class.getDeclaredField("finalGrade");
        fgField .setAccessible(true);

        double[] grades = {95.0, 90.0, 89.9, 85.0, 80.0, 79.9, 72.0, 70.0, 
                           69.9, 61.0, 60.0, 59.9, 55.0, 50.0, 49.9, 30.0};
        FinalGrades[] expects = {
        	    //  95.0, 90.0
        	    FinalGrades.A, FinalGrades.A,
        	    //  89.9, 85.0, 80.0
        	    FinalGrades.B, FinalGrades.B, FinalGrades.B,
        	    //  79.9, 72.0, 70.0
        	    FinalGrades.C, FinalGrades.C, FinalGrades.C,
        	    //  69.9, 61.0, 60.0
        	    FinalGrades.D, FinalGrades.D, FinalGrades.D,
        	    //  59.9, 55.0, 50.0   <-- three E’s here
        	    FinalGrades.E, FinalGrades.E, FinalGrades.E,
        	    //  49.9, 30.0
        	    FinalGrades.F, FinalGrades.F
        	};


        for (int i = 0; i < grades.length; i++) {
            final double injectedAverage = grades[i];
            final FinalGrades expectedGrade = expects[i];

            avgField.setDouble(sg, injectedAverage);
            sg.setFinalGrade();

            FinalGrades actual = (FinalGrades) fgField.get(sg);
            assertEquals(expectedGrade, actual,
                () -> String.format("With classAverage=%.1f expected %s but got %s",
                                    injectedAverage, expectedGrade, actual));
        }
    }

    // helper to avoid magic literal
    private static double fiftyfive() {
        return 55.0;
    }
}
