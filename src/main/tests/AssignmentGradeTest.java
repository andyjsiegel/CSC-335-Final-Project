package main.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.model.*;


public class AssignmentGradeTest {
	AssignmentGrade ag1, ag2;

    @BeforeEach
    void setup() {
        ag1 = new AssignmentGrade(null,50,100,"HW");
        ag2 = new AssignmentGrade(null, 0, 0, "E");
    }

    @Test
    void testGetters() {
        assertEquals(50, ag1.getPointsEarned());
        assertEquals(100, ag1.getPointsPossible());
        assertEquals("HW", ag1.getCategory());
    }

    @Test
    void testPercentage() {
        assertEquals(0.5, ag1.getPercentage());
        assertEquals(0.0, ag2.getPercentage());
    }
}

