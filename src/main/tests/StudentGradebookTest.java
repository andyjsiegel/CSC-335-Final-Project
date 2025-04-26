package main.tests;

import main.model.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentGradebookTest {
    private StudentGradebook gb;
    private Course course;

    @BeforeEach
    void setup() {
        // Create a dummy course for the gradebook
        course = new Course(
            "TestCourse", "TC101", "A test course",
            new Instructor("i","p","I","N","e@mail", false),
            new ArrayList<>()
        );
        gb = new StudentGradebook(course);
    }

    @Test
    void testCalculateAverageAndFinalGrade() {
        // Add two graded assignments: 8/10 and 16/20
        gb.addAssignment(new Assignment("A1","D1", 10), 8.0);
        gb.addAssignment(new Assignment("A2","D2", 20), 16.0);
        // (8 + 16) / (10 + 20) * 100 = 80.0
        double avg = gb.calculateAverage();
        assertEquals(80.0, avg, 1e-9);

        // calculateFinalGrade() currently delegates to calculateAverage()
        double finalPct = gb.calculateFinalGrade();
        assertEquals(avg, finalPct, 1e-9);
    }

    @Test
    void testCalculateWeightedAverageSingleCategory() {
        // Set up exactly one category so weighted==simple
        course.addCategory("Default", 100, 0);
        Assignment a = new Assignment("A","D", 10);
        a.setCategory("Default");
        gb.addAssignment(a, 5.0);  // 5/10 → 0.5

        // Weighted average = 0.5 * 100 = 50.0
        double wavg = gb.calculateWeightedAverage();
        assertEquals(50.0, wavg, 1e-9);
    }

    @Test
    void testCalculateMedianOddAndEven() {
        // Odd count: [2,5,8] → median=5
        gb.addAssignment(new Assignment("X1","d",10), 2.0);
        gb.addAssignment(new Assignment("X2","d",10), 5.0);
        gb.addAssignment(new Assignment("X3","d",10), 8.0);
        assertEquals(5.0, gb.calculateMedian(), 1e-9);

        // Even count: [2,5,8,10] → median=(5+8)/2=6.5
        gb = new StudentGradebook(course);
        gb.addAssignment(new Assignment("Y1","d",10), 2.0);
        gb.addAssignment(new Assignment("Y2","d",10), 5.0);
        gb.addAssignment(new Assignment("Y3","d",10), 8.0);
        gb.addAssignment(new Assignment("Y4","d",10),10.0);
        assertEquals(6.5, gb.calculateMedian(), 1e-9);
    }

    @Test
    void testGetAssignmentsReturnsCopy() {
        Assignment a1 = new Assignment("A1","D1",10);
        Assignment a2 = new Assignment("A2","D2",20);
        gb.addAssignment(a1, 5.0);
        gb.addAssignment(a2,10.0);

        List<Assignment> first = gb.getAssignments();
        assertEquals(2, first.size());
        // Mutate the returned list
        first.remove(0);
        // Underlying list should be unaffected
        List<Assignment> second = gb.getAssignments();
        assertEquals(2, second.size());
    }

    @Test
    void testGetAndSetFinalGrade() {
        // Initially null
        assertNull(gb.getFinalGrade());
        gb.setFinalGrade(FinalGrades.C);
        assertEquals(FinalGrades.C, gb.getFinalGrade());
    }

    @Test
    void testEqualsAndHashCode() {
        // Two fresh gradebooks on the same course with zero points should be equal
        StudentGradebook other = new StudentGradebook(course);
        assertEquals(gb, other);
        assertEquals(gb.hashCode(), other.hashCode());

        // After adding points to one, they differ
        gb.addAssignment(new Assignment("Z","d",10), 3.0);
        assertNotEquals(gb, other);
    }
}
