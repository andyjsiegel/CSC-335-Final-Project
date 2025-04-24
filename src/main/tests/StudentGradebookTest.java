package main.tests;

import org.junit.jupiter.api.*;

import main.model.*;
import java.util.*;

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
}
